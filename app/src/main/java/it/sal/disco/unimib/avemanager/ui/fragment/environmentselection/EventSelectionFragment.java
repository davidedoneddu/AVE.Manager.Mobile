package it.sal.disco.unimib.avemanager.ui.fragment.environmentselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.EnvironmentSelectionActivity;
import it.sal.disco.unimib.avemanager.ui.adapter.EventAdapter;
import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.ui.viewmodel.EventViewModel;

@AndroidEntryPoint
public class EventSelectionFragment extends Fragment {

    private EventViewModel viewModel ; // Usa viewModels() se puoi
    private EventAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EnvironmentSelectionActivity activity = (EnvironmentSelectionActivity) requireActivity();

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEvents);
        adapter = new EventAdapter(new ArrayList<>(), org -> {
            viewModel.selectEvent(org);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getEventList();
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getEventListLiveData().observe(getViewLifecycleOwner(), events -> {
            adapter = new EventAdapter(events, event -> {
                viewModel.selectEvent(event);
            });
            recyclerView.setAdapter(adapter);
        });

        viewModel.getEventState().observe(getViewLifecycleOwner(), state -> {
            if (state == EventViewModel.EventState.LOADING) {
                activity.showLoader();
            } else {
                activity.hideLoader();
            }
        });

        viewModel.getEventList();

    }
}