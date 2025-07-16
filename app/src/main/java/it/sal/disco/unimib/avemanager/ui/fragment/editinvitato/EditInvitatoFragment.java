package it.sal.disco.unimib.avemanager.ui.fragment.editinvitato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.databinding.FragmentEditInvitatoBinding;
import it.sal.disco.unimib.avemanager.ui.activity.EditInvitatoActivity;
import it.sal.disco.unimib.avemanager.ui.viewmodel.InvitatiViewModel;
@AndroidEntryPoint
public class EditInvitatoFragment extends Fragment {

    private FragmentEditInvitatoBinding binding;
    private InvitatiViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditInvitatoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(InvitatiViewModel.class);

        List<Fragment> fragments = Arrays.asList(
                new AnagraficaFragment(),
                new AziendaFragment(),
                new ConfigurazioniFragment()
        );

        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        };

        binding.viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("Anagrafica"); break;
                        case 1: tab.setText("Azienda"); break;
                        case 2: tab.setText("Partecipazione"); break;
                    }
                }
        ).attach();

        binding.btnSave.setOnClickListener(v -> {
            viewModel.saveInvitato();
            requireActivity().finish();  // Chiude activity edit e torna alla lista
        });

        // Carica invitato se presente
        Bundle args = getArguments();
        if (args != null && args.containsKey(EditInvitatoActivity.EXTRA_INVITATO_ID)) {
            int id = args.getInt(EditInvitatoActivity.EXTRA_INVITATO_ID);
            viewModel.loadInvitato(id);
        } else {
            viewModel.createNewInvitato();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}