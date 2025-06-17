package it.sal.disco.unimib.avemanager.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.activity.MainActivity;
import it.sal.disco.unimib.avemanager.ui.viewmodel.LoginViewModel;

public class SignInFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonLogin;

    private LoginViewModel loginViewModel;

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case SUCCESS:
                    // Login avvenuto con successo
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    // opzionale: mostra una progress bar
                    break;
            }
        });

        buttonLogin.setOnClickListener(v -> {
            String username = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();
            loginViewModel.login(username, password);
        });

        return view;
    }
}