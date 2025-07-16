package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.EditInvitatoActivity;
import it.sal.disco.unimib.avemanager.ui.adapter.InvitatiAdapter;
import it.sal.disco.unimib.avemanager.ui.fragment.utils.MaterialDialogFragment;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;
import it.sal.disco.unimib.avemanager.ui.viewmodel.CheckInViewModel;
import it.sal.disco.unimib.avemanager.ui.viewmodel.InvitatiViewModel;
import it.sal.disco.unimib.avemanager.util.OnInvitatoActionListener;

@AndroidEntryPoint
public class ManageInvitatiFragment extends Fragment {

    private InvitatiAdapter adapter;
    private InvitatiViewModel viewModel;
    private CheckInViewModel checkInViewModel;

    private final int visibleThreshold = 5; // numero di item prima della fine per triggerare caricamento
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_invitati, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewInvitati);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new InvitatiAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(InvitatiViewModel.class);
        checkInViewModel = new ViewModelProvider(this).get(CheckInViewModel.class);

        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel.getInvitatiLiveData().observe(getViewLifecycleOwner(), invitati -> adapter.submitList(new ArrayList<>(invitati), false));

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> swipeRefreshLayout.setRefreshing(loading));

        // Scroll listener per lazy load
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!viewModel.isLoading() && !viewModel.isLastPage() &&
                        totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    viewModel.loadMore();
                }
            }
        });
        // Gestione ricerca invitati con debounce semplice
        TextView searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            private Runnable searchRunnable;
            private final android.os.Handler handler = new android.os.Handler();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) handler.removeCallbacks(searchRunnable);

                searchRunnable = () -> viewModel.setSearchQuery(s.toString());
                handler.postDelayed(searchRunnable, 300); // 300 ms debounce
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Osserva eventi CheckIn
        checkInViewModel.getCheckInResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(
                        true,
                        "Check-in riuscito",
                        "Utente:\n" + result.getNomeUtente() + "\n\n" + result.getDescrizione()
                );

                dialog.show(getParentFragmentManager(), "CheckInSuccess");
                checkInViewModel.resetCheckInResult();
                viewModel.refresh();
            }
        });

        checkInViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(false, "Errore", message);
                dialog.show(getParentFragmentManager(), "CheckInError");
            }
        });

        checkInViewModel.getCheckInState().observe(getViewLifecycleOwner(), state -> {
            if (state != CheckInViewModel.CheckInState.LOADING) {
                viewModel.refresh();
            }
        });

        //Gestione refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        swipeRefreshLayout.setEnabled(true);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float dx = Math.abs(event.getX() - startX);
                        float dy = Math.abs(event.getY() - startY);

                        if (dy > 50 && dx < 10)  {

                            swipeRefreshLayout.setEnabled(true);
                        } else {
                            swipeRefreshLayout.setEnabled(false);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        v.performClick();  // ora senza warning
                        break;
                }
                return false;
            }
        });


        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());

        //gestione add
        FloatingActionButton buttonAdd = view.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), EditInvitatoActivity.class);
            startActivity(intent);
        });


        adapter.setOnInvitatoActionListener(new OnInvitatoActionListener() {
            @Override
            public void onEditClick(Invitato invitato) {
                Intent intent = new Intent(requireContext(), EditInvitatoActivity.class);
                intent.putExtra("EXTRA_INVITATO_ID", invitato.getInvId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Invitato invitato) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Elimina Invitato")
                        .setMessage("Sei sicuro di voler eliminare " + invitato.getInvNome() +" "+ invitato.getInvCognome()+"?")
                        .setPositiveButton("Sì", (dialog, which) -> viewModel.deleteInvitato(invitato))
                        .setNegativeButton("Annulla", null)
                        .show();
            }

            @Override
            public void onSendEmailClick(Invitato invitato) {
                viewModel.sendEmailToInvitato(invitato);
            }

            @Override
            public void onInfoClick(Invitato invitato) {

            }
        });

        return view;
    }

    private @NonNull ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            private final int backgroundColor = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
            private final Drawable icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_qr_code_scanner);

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    Invitato invitato = adapter.getItem(position);
                    if (invitato != null ) {
                        checkInViewModel.checkInWithQr(invitato.getInvWebCode() != null ? invitato.getInvWebCode() : "");
                    }else{
                        MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(false, "Errore", "L'invitato non è stato caricato correttamente, riprova");
                        dialog.show(getParentFragmentManager(), "CheckInError");
                    }
                    adapter.notifyItemChanged(position); // ripristina l'item perché non rimuoviamo
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX,
                                    float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    float cornerRadius = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            16,
                            itemView.getResources().getDisplayMetrics());

                    Paint paint = new Paint();
                    paint.setColor(backgroundColor);
                    paint.setAntiAlias(true);

                    RectF backgroundRect = new RectF(
                            itemView.getLeft(),
                            itemView.getTop(),
                            itemView.getRight(),
                            itemView.getBottom());

                    c.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, paint);

                    assert icon != null;
                    int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                    int iconMargin = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            32,
                            itemView.getResources().getDisplayMetrics());
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    int iconBottom = iconTop + icon.getIntrinsicHeight();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    icon.draw(c);

                    int originalLeft = itemView.getLeft();
                    int saveCount = c.save();
                    float radius = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            16,
                            itemView.getResources().getDisplayMetrics());

                    Path clipPath = new Path();
                    RectF clipRect = new RectF(
                            originalLeft,
                            itemView.getTop(),
                            itemView.getRight(),
                            itemView.getBottom()
                    );
                    clipPath.addRoundRect(clipRect, radius, radius, Path.Direction.CW);
                    c.clipPath(clipPath);
                    float alpha = 1.0f - Math.min(1.0f, Math.abs(dX) / itemView.getWidth() * 2);
                    itemView.setAlpha(alpha);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                    c.restoreToCount(saveCount);

                } else {
                    itemView.setAlpha(1.0f);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };
        return new ItemTouchHelper(simpleCallback);
    }
}