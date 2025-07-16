package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.utils.CardEventDataFragment;
import it.sal.disco.unimib.avemanager.ui.model.EventData;
import it.sal.disco.unimib.avemanager.ui.viewmodel.EventDataViewModel;

@AndroidEntryPoint
public class HomePageFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private EventDataViewModel viewModel;

    // TextView delle 3 card
    private TextView invitatiTitle, invitatiDesc;
    private TextView votantiTitle, votantiDesc;
    private TextView votiTitle, votiDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(EventDataViewModel.class);

        // Swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadEventData());

        // Collega i TextView delle card
        invitatiTitle = view.findViewById(R.id.invitatiTitle);
        invitatiDesc = view.findViewById(R.id.invitatiDesc);
        votantiTitle = view.findViewById(R.id.votantiTitle);
        votantiDesc = view.findViewById(R.id.votantiDesc);
        votiTitle = view.findViewById(R.id.votiTitle);
        votiDesc = view.findViewById(R.id.votiDesc);

        MaterialButton reloadButton = view.findViewById(R.id.reloadQuorumBtn);
        reloadButton.setOnClickListener(v -> viewModel.loadEventData());

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> swipeRefreshLayout.setRefreshing(isLoading));

        viewModel.getEventData().observe(getViewLifecycleOwner(), this::onChanged);

        // Errori
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Errore: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Prima fetch
        viewModel.loadEventData();
    }

    private void onChanged(EventData event) {
        if (event != null) {
            // Card 1 - Invitati
            invitatiTitle.setText(R.string.card_1_title);
            invitatiDesc.setText(String.format("%d presenti su %d", event.getEventInvPresenti(), event.getEventInvTotali()));

            // Card 2 - Votanti
            votantiTitle.setText(R.string.card_2_title);
            votantiDesc.setText(String.format("%d votanti presenti su %d", event.getEventVotiInvPresenti(), event.getEventVotiInvTotali()));

            // Card 3 - Voti
            votiTitle.setText(R.string.card_3_title);
            votiDesc.setText(String.format("%d voti presenti su %d", event.getEventVotiPresenti(), event.getEventVotiTotali()));

            // Fragment donut chart
            String dataQuorum = event.getEventDataQuorum() != null ? event.getEventDataQuorum().toString() : "";
            CardEventDataFragment cardFragment = CardEventDataFragment.newInstance(
                    event.getEventVotiPresenti(), event.getEventVotiTotali(),
                    event.isEventIsQuorumOk(), dataQuorum);

            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.chartCard, cardFragment)
                    .commit();
        }
    }
}
