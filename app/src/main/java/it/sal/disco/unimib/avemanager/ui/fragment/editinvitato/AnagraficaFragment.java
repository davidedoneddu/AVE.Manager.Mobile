package it.sal.disco.unimib.avemanager.ui.fragment.editinvitato;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

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
                Objects.requireNonNull(binding.etCognome.getEditText()).setText(invitato.getInvCognome());
                Objects.requireNonNull(binding.etNome.getEditText()).setText(invitato.getInvNome());
                Objects.requireNonNull(binding.etCortesia.getEditText()).setText(invitato.getInvCortesia());
                Objects.requireNonNull(binding.etTitolo.getEditText()).setText(invitato.getInvTitolo());
            }
        });

        // Aggiorna ViewModel quando cambia testo
        Objects.requireNonNull(binding.etCognome.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvCognome(s.toString());
            }
        });

        Objects.requireNonNull(binding.etNome.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvNome(s.toString());
            }
        });

        Objects.requireNonNull(binding.etCortesia.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvCortesia(s.toString());
            }
        });

        Objects.requireNonNull(binding.etTitolo.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvTitolo(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
