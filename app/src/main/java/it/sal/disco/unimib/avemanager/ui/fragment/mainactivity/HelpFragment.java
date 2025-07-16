package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.viewmodel.EventDataViewModel;

@AndroidEntryPoint
public class HelpFragment extends Fragment {


    private TextInputEditText etSubject, etBody;
    private MaterialButton btnSendEmail;
    private EventDataViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel =  new ViewModelProvider(this).get(EventDataViewModel.class);
        etSubject = view.findViewById(R.id.etSubject);
        etBody = view.findViewById(R.id.etBody);
        btnSendEmail = view.findViewById(R.id.btnSendEmail);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            //loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        viewModel.getIsSended().observe(getViewLifecycleOwner(), isSended -> {
            if(Boolean.TRUE.equals(isSended)){
                new AlertDialog.Builder(requireContext())
                        .setTitle("Email Inviata")
                        .setMessage("Il nostro team ha preso in carico la sua richiesta, riceverai la risposta sulla tua mail")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if(!Objects.requireNonNull(error.getMessage()).isEmpty()){
                new AlertDialog.Builder(requireContext())
                        .setTitle("Errore")
                        .setMessage(error.getMessage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        btnSendEmail.setOnClickListener(v -> {
            String subject = etSubject.getText() != null ? etSubject.getText().toString() : "";
            String body = etBody.getText() != null ? etBody.getText().toString() : "";

            if (subject.isEmpty() || body.isEmpty()) {
                Toast.makeText(getContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }


            viewModel.sendEmailHelp(subject, body);

        });
    }
}