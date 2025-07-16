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
import it.sal.disco.unimib.avemanager.databinding.FragmentAziendaBinding;
import it.sal.disco.unimib.avemanager.ui.viewmodel.InvitatiViewModel;
import it.sal.disco.unimib.avemanager.util.SimpleTextWatcher;
@AndroidEntryPoint
public class AziendaFragment extends Fragment {

    private FragmentAziendaBinding binding;
    private InvitatiViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAziendaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InvitatiViewModel.class);

        viewModel.getCurrentInvitato().observe(getViewLifecycleOwner(), invitato -> {
            if (invitato != null) {
                Objects.requireNonNull(binding.etSocieta.getEditText()).setText(invitato.getInvSocieta());
                Objects.requireNonNull(binding.etRagSoc.getEditText()).setText(invitato.getInvRagSoc());
                Objects.requireNonNull(binding.etIndirizzo.getEditText()).setText(invitato.getInvIndirizzo());
                Objects.requireNonNull(binding.etCap.getEditText()).setText(invitato.getInvCap());
                Objects.requireNonNull(binding.etCitta.getEditText()).setText(invitato.getInvCitta());
                Objects.requireNonNull(binding.etProvincia.getEditText()).setText(invitato.getInvProvincia());
            }
        });

        Objects.requireNonNull(binding.etSocieta.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvSocieta(s.toString());
            }
        });

        Objects.requireNonNull(binding.etRagSoc.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvRagSoc(s.toString());
            }
        });

        Objects.requireNonNull(binding.etIndirizzo.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvIndirizzo(s.toString());
            }
        });

        Objects.requireNonNull(binding.etCap.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvCap(s.toString());
            }
        });

        Objects.requireNonNull(binding.etCitta.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvCitta(s.toString());
            }
        });

        Objects.requireNonNull(binding.etProvincia.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvProvincia(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
