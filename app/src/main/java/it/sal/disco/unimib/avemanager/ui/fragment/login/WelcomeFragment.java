package it.sal.disco.unimib.avemanager.ui.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import it.sal.disco.unimib.avemanager.R;


public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        super(R.layout.fragment_welcome);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Trova il pulsante
        Button buttonEnter = view.findViewById(R.id.button_enter);

        // Imposta il click listener per il pulsante
        buttonEnter.setOnClickListener(v -> {
            // Naviga alla schermata di login
            NavHostFragment.findNavController(this).navigate(R.id.action_welcome_to_signIn);
        });
    }
}