package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.utils.CardEventDataFragment;

public class HomePageFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private View loadingOverlay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        CardEventDataFragment cardFragment = CardEventDataFragment.newInstance(
                50, 100, 75, 120);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.eventDataFragmentContainer, cardFragment)
                .commit();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Qui metti la logica di refresh che vuoi fare
            refreshEventData();
        });
    }

    private void refreshEventData() {
        // Esempio: mostra overlay loading e poi nascondilo dopo aggiornamento
        loadingOverlay.setVisibility(View.VISIBLE);

        // Simula una chiamata di aggiornamento, poi rimuove loading e stop refresh
        swipeRefreshLayout.postDelayed(() -> {
            loadingOverlay.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }, 1500);
    }
}