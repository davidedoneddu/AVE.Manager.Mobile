package it.sal.disco.unimib.avemanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import it.sal.disco.unimib.avemanager.R;

public class LoginPageActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //emailEditText = findViewById(R.id.emailEditText);
        //passwordEditText = findViewById(R.id.passwordEditText);
        //loginButton = findViewById(R.id.loginButton);

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString().trim();
//                String password = passwordEditText.getText().toString().trim();
//
//                if (email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(LoginPageActivity.this, "Inserisci email e password", Toast.LENGTH_SHORT).show();
//                } else {
//                    //loginUser(email, password);
//                }
//            }
//        });
    }
}