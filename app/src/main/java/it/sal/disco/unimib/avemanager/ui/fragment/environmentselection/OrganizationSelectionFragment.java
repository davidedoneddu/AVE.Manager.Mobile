package it.sal.disco.unimib.avemanager.ui.fragment.environmentselection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.EnvironmentSelectionActivity;
import it.sal.disco.unimib.avemanager.ui.adapter.OrganizationAdapter;
import it.sal.disco.unimib.avemanager.ui.viewmodel.OrganizationViewModel;
import it.sal.disco.unimib.avemanager.ui.viewmodel.SharedSelectedEnvironmentViewModel;

@AndroidEntryPoint
public class OrganizationSelectionFragment extends Fragment {

    private OrganizationViewModel viewModel ; // Usa viewModels() se puoi
    private OrganizationAdapter adapter;
    private SharedSelectedEnvironmentViewModel sharedViewModel ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedSelectedEnvironmentViewModel.class);
        viewModel = new ViewModelProvider(this).get(OrganizationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_organization_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EnvironmentSelectionActivity activity = (EnvironmentSelectionActivity) requireActivity();

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOrganizations);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new OrganizationAdapter(new ArrayList<>(), org -> {
            viewModel.selectOrganization(org);
            sharedViewModel.setSelectedOrganization(org);
            // Naviga al fragment successivo usando NavController
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_to_selectEvent);
        }, (orgId, callback) -> {
            viewModel.getOrganizationImage(orgId, callback);
        });

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getOrganizationList();
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getOrganizationListLiveData().observe(getViewLifecycleOwner(), organizations -> {
            adapter = new OrganizationAdapter(organizations, org -> {
                viewModel.selectOrganization(org);
                sharedViewModel.setSelectedOrganization(org);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_to_selectEvent);
            }, (orgId, callback) -> {
                viewModel.getOrganizationImage(orgId, callback);
            });
            recyclerView.setAdapter(adapter);

        });

        viewModel.getOrganizationState().observe(getViewLifecycleOwner(), state -> {
            if (state == OrganizationViewModel.OrganizationState.LOADING) {
                activity.showLoader();
            } else {
                activity.hideLoader();
            }
        });
        viewModel.getOrganizationState().observe(getViewLifecycleOwner(), state -> {
            if (state == OrganizationViewModel.OrganizationState.LOADING) {
                activity.showLoader();
            } else {
                activity.hideLoader();
            }
        });

        viewModel.getOrganizationList();
    }
}