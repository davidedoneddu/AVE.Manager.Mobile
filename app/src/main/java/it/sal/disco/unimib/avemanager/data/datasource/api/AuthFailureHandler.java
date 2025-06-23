package it.sal.disco.unimib.avemanager.data.datasource.api;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import it.sal.disco.unimib.avemanager.ui.activity.LoginPageActivity;


public class AuthFailureHandler implements ApiInterceptor.AuthFailureHandler {

    private final Context context;

    public AuthFailureHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void onAuthFailure() {
        Log.w("AuthFailureHandler", "Token non valido o scaduto, effettuo logout");

        // Esempio: pulire dati utente/token salvati (SharedPreferences, DB...)
        clearUserSession();

        // Lanciare activity di login per chiedere di rifare il login
        Intent intent = new Intent(context, LoginPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void clearUserSession() {
        // Qui svuota token, user info, ecc.
        // Esempio con SharedPreferences:
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
