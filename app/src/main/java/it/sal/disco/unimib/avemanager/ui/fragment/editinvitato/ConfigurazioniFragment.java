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
import it.sal.disco.unimib.avemanager.databinding.FragmentConfigurazioniBinding;
import it.sal.disco.unimib.avemanager.ui.viewmodel.InvitatiViewModel;
import it.sal.disco.unimib.avemanager.util.SimpleTextWatcher;
@AndroidEntryPoint
public class ConfigurazioniFragment extends Fragment {

    private FragmentConfigurazioniBinding binding;
    private InvitatiViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentConfigurazioniBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InvitatiViewModel.class);

        viewModel.getCurrentInvitato().observe(getViewLifecycleOwner(), invitato -> {
            if (invitato != null) {
                binding.checkboxInvitato.setChecked(Boolean.TRUE.equals(invitato.getInvInvitato()));
                binding.checkboxConfermato.setChecked(Boolean.TRUE.equals(invitato.getInvConfermato()));
                binding.checkboxPresente.setChecked(Boolean.TRUE.equals(invitato.getInvPresente()));
                binding.checkboxNonPartecipa.setChecked(Boolean.TRUE.equals(invitato.getInvNonPartecipa()));
                binding.etNote.getEditText().setText(invitato.getInvNote());
                binding.etEmail.getEditText().setText(invitato.getInvEmail());
                binding.etSesso.getEditText().setText(invitato.getInvSesso());
                binding.checkboxVotante.setChecked(Boolean.TRUE.equals(invitato.getInvVotante()));
                binding.checkboxPuoEssereSostituito.setChecked(Boolean.TRUE.equals(invitato.getInvPuoEssereSostituito()));
                binding.checkboxPuoInvitare.setChecked(Boolean.TRUE.equals(invitato.getInvPuoInvitare()));
                binding.etCarica.getEditText().setText(invitato.getInvCarica());
                binding.checkboxScrutinatore.setChecked(Boolean.TRUE.equals(invitato.getInvScrutinatore()));
                binding.etExPostoOccupato.getEditText().setText(invitato.getInvExPostoOccupato());
            }
        });

        binding.checkboxInvitato.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvInvitato(isChecked));

        binding.checkboxConfermato.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvConfermato(isChecked));

        binding.checkboxPresente.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvPresente(isChecked));

        binding.checkboxNonPartecipa.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvNonPartecipa(isChecked));

        binding.etNote.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvNote(s.toString());
            }
        });

        binding.etEmail.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvEmail(s.toString());
            }
        });

        binding.etSesso.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvSesso(s.toString());
            }
        });

        binding.checkboxVotante.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvVotante(isChecked));

        binding.checkboxPuoEssereSostituito.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvPuoEssereSostituito(isChecked));

        binding.checkboxPuoInvitare.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvPuoInvitare(isChecked));

        binding.etCarica.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvCarica(s.toString());
            }
        });

        binding.checkboxScrutinatore.setOnCheckedChangeListener((buttonView, isChecked) ->
                viewModel.getCurrentInvitato().getValue().setInvScrutinatore(isChecked));

        binding.etExPostoOccupato.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getCurrentInvitato().getValue().setInvExPostoOccupato(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}