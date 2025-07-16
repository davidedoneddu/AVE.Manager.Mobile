package it.sal.disco.unimib.avemanager.ui.fragment.editinvitato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.databinding.FragmentAnagraficaBinding;
import it.sal.disco.unimib.avemanager.ui.viewmodel.InvitatiViewModel;
import it.sal.disco.unimib.avemanager.util.SimpleTextWatcher;
@AndroidEntryPoint
public class AnagraficaFragment extends Fragment {

    private FragmentAnagraficaBinding binding;
    private InvitatiViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAnagraficaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InvitatiViewModel.class);

        viewModel.getCurrentInvitato().observe(getViewLifecycleOwner(), invitato -> {
            if (invitato != null) {
                binding.etCognome.getEditText().setText(invitato.getInvCognome());
                binding.etNome.getEditText().setText(invitato.getInvNome());
                binding.etCortesia.getEditText().setText(invitato.getInvCortesia());
                binding.etTitolo.getEditText().setText(invitato.getInvTitolo());
            }
        });

        // Aggiorna ViewModel quando cambia testo
        binding.etCognome.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvCognome(s.toString());
            }
        });

        binding.etNome.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvNome(s.toString());
            }
        });

        binding.etCortesia.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvCortesia(s.toString());
            }
        });

        binding.etTitolo.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvTitolo(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
