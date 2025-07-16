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
                binding.etSocieta.getEditText().setText(invitato.getInvSocieta());
                binding.etRagSoc.getEditText().setText(invitato.getInvRagSoc());
                binding.etIndirizzo.getEditText().setText(invitato.getInvIndirizzo());
                binding.etCap.getEditText().setText(invitato.getInvCap());
                binding.etCitta.getEditText().setText(invitato.getInvCitta());
                binding.etProvincia.getEditText().setText(invitato.getInvProvincia());
            }
        });

        binding.etSocieta.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvSocieta(s.toString());
            }
        });

        binding.etRagSoc.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvRagSoc(s.toString());
            }
        });

        binding.etIndirizzo.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvIndirizzo(s.toString());
            }
        });

        binding.etCap.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvCap(s.toString());
            }
        });

        binding.etCitta.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvCitta(s.toString());
            }
        });

        binding.etProvincia.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvProvincia(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
