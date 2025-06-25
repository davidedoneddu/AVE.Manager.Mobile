package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import it.sal.disco.unimib.avemanager.R;


public class HelpFragment extends Fragment {


    private TextInputEditText etSubject, etBody;
    private MaterialButton btnSendEmail;

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

        etSubject = view.findViewById(R.id.etSubject);
        etBody = view.findViewById(R.id.etBody);
        btnSendEmail = view.findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(v -> {
            String subject = etSubject.getText() != null ? etSubject.getText().toString() : "";
            String body = etBody.getText() != null ? etBody.getText().toString() : "";

            if (subject.isEmpty() || body.isEmpty()) {
                Toast.makeText(getContext(), "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:davide.doneddu@vissoftware.it")); // <-- Sostituisci con tuo indirizzo
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);

            try {
                startActivity(Intent.createChooser(emailIntent, "Invia email con..."));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "Nessuna app email trovata", Toast.LENGTH_SHORT).show();
            }
        });
    }
}