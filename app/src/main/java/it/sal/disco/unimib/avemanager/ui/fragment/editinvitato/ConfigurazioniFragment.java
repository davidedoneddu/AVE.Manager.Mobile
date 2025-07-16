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
                Objects.requireNonNull(binding.etNote.getEditText()).setText(invitato.getInvNote());
                Objects.requireNonNull(binding.etEmail.getEditText()).setText(invitato.getInvEmail());
                Objects.requireNonNull(binding.etSesso.getEditText()).setText(invitato.getInvSesso());
                binding.checkboxVotante.setChecked(Boolean.TRUE.equals(invitato.getInvVotante()));
                binding.checkboxPuoEssereSostituito.setChecked(Boolean.TRUE.equals(invitato.getInvPuoEssereSostituito()));
                binding.checkboxPuoInvitare.setChecked(Boolean.TRUE.equals(invitato.getInvPuoInvitare()));
                Objects.requireNonNull(binding.etCarica.getEditText()).setText(invitato.getInvCarica());
                binding.checkboxScrutinatore.setChecked(Boolean.TRUE.equals(invitato.getInvScrutinatore()));
                Objects.requireNonNull(binding.etExPostoOccupato.getEditText()).setText(invitato.getInvExPostoOccupato());
            }
        });

        binding.checkboxInvitato.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvInvitato(isChecked));

        binding.checkboxConfermato.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvConfermato(isChecked));

        binding.checkboxPresente.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvPresente(isChecked));

        binding.checkboxNonPartecipa.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvNonPartecipa(isChecked));

        Objects.requireNonNull(binding.etNote.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvNote(s.toString());
            }
        });

        Objects.requireNonNull(binding.etEmail.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvEmail(s.toString());
            }
        });

        Objects.requireNonNull(binding.etSesso.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvSesso(s.toString());
            }
        });

        binding.checkboxVotante.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvVotante(isChecked));

        binding.checkboxPuoEssereSostituito.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvPuoEssereSostituito(isChecked));

        binding.checkboxPuoInvitare.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvPuoInvitare(isChecked));

        Objects.requireNonNull(binding.etCarica.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvCarica(s.toString());
            }
        });

        binding.checkboxScrutinatore.setOnCheckedChangeListener((buttonView, isChecked) ->
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvScrutinatore(isChecked));

        Objects.requireNonNull(binding.etExPostoOccupato.getEditText()).addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Objects.requireNonNull(viewModel.getCurrentInvitato().getValue()).setInvExPostoOccupato(s.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}