package it.sal.disco.unimib.avemanager.data.datasource.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Inject;

import it.sal.disco.unimib.avemanager.util.DataCallback;

public class FirebaseDataSource {

    private final FirebaseAuth auth;
    private final FirebaseStorage storage;

    @Inject
    public FirebaseDataSource() {
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    // Login con email e password, callback con successo o errore
    public void login(String email, String password, final DataCallback<String> callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (auth.getCurrentUser() != null) {
                            auth.getCurrentUser().getIdToken(true)
                                    .addOnCompleteListener(tokenTask -> {
                                        if (tokenTask.isSuccessful()) {
                                            String idToken = tokenTask.getResult().getToken();
                                            callback.onSuccess(idToken);
                                        } else {
                                            Exception e = tokenTask.getException();
                                            Log.e("FirebaseDataSource", "Errore nel getIdToken: " + (e != null ? e.getMessage() : "null"), e);
                                            callback.onFailure(e);

                                        }
                                    });
                        } else {
                            Exception e = new Exception("Utente autenticato ma getCurrentUser() è null");
                            Log.e("FirebaseDataSource", e.getMessage());
                            callback.onFailure(e);
                        }
                    } else {
                        Exception e = task.getException();
                        Log.e("FirebaseDataSource", "Errore login: " + (e != null ? e.getMessage() : "null"), e);
                        callback.onFailure(e);
                    }
                });
    }


    public void logout(DataCallback<String> dataCallback) {
        try {
            auth.signOut();

            if (auth.getCurrentUser() == null) {
                dataCallback.onSuccess("Logout effettuato con successo");
            } else {
                dataCallback.onFailure(new Exception("L'utente è ancora autenticato"));
            }
        } catch (Exception e) {
            Log.e("FirebaseDataSource", "Errore durante il logout: " + e.getMessage(), e);
            dataCallback.onFailure(e);
        }
    }


}

