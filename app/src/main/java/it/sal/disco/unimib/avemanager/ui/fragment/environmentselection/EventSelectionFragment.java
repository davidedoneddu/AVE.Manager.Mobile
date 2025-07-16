package it.sal.disco.unimib.avemanager.ui.fragment.environmentselection;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.EnvironmentSelectionActivity;
import it.sal.disco.unimib.avemanager.ui.activity.EventMainActivity;
import it.sal.disco.unimib.avemanager.ui.adapter.EventAdapter;
import it.sal.disco.unimib.avemanager.ui.viewmodel.EventViewModel;
import it.sal.disco.unimib.avemanager.ui.viewmodel.SharedSelectedEnvironmentViewModel;

@AndroidEntryPoint
public class EventSelectionFragment extends Fragment {

    private EventViewModel viewModel ; // Usa viewModels() se puoi
    private EventAdapter adapter;
    private SharedSelectedEnvironmentViewModel sharedViewModel ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedSelectedEnvironmentViewModel.class);
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
        adapter = new EventAdapter(new ArrayList<>(), clickedEvent -> {
            viewModel.selectEvent(clickedEvent);
            sharedViewModel.setSelectedEvent(clickedEvent);

            // Qui costruisci e lanci l'intent
            String orgName = sharedViewModel.getSelectedOrganization().getValue() != null
                    ? sharedViewModel.getSelectedOrganization().getValue().getName()
                    : "N/A";

            String eventName = clickedEvent.getName();

            Intent intent = new Intent(requireContext(), EventMainActivity.class);
            intent.putExtra("org_name", orgName);
            intent.putExtra("event_name", eventName);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getEventList();
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getEventListLiveData().observe(getViewLifecycleOwner(), events -> {
            adapter = new EventAdapter(events, clickedEvent -> {
                viewModel.selectEvent(clickedEvent);
                sharedViewModel.setSelectedEvent(clickedEvent);

                String orgName = sharedViewModel.getSelectedOrganization().getValue() != null
                        ? sharedViewModel.getSelectedOrganization().getValue().getName()
                        : "N/A";

                String eventName = clickedEvent.getName();

                Intent intent = new Intent(requireContext(), EventMainActivity.class);
                intent.putExtra("org_name", orgName);
                intent.putExtra("event_name", eventName);
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        });

        viewModel.getEventState().observe(getViewLifecycleOwner(), state -> {
            if (state == EventViewModel.EventState.LOADING) {
                activity.showLoader();
            } else if (state == EventViewModel.EventState.ERROR){
                activity.hideLoader();
                Toast.makeText(activity, "Errore durante il caricamento", Toast.LENGTH_SHORT).show();
            }else{
                activity.hideLoader();
            }
        });

        viewModel.getEventList();

    }
}