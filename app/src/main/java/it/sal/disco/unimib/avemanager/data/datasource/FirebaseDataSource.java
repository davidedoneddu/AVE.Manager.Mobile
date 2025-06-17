package it.sal.disco.unimib.avemanager.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FirebaseDataSource {

    private final FirebaseAuth auth;
    private final FirebaseStorage storage;

    public FirebaseDataSource() {
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    // Login con email e password, callback con successo o errore
    public void login(String email, String password, final FirebaseAuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login ok
                            callback.onSuccess();
                        } else {
                            // Login fallito, passa eccezione o messaggio
                            callback.onFailure(task.getException());
                        }
                    }
                });
    }

    // Recupera URL immagine organizzazione da Firebase Storage
    public void getOrganizationImageUrl(String orgId, final FirebaseStorageCallback callback) {
        StorageReference imageRef = storage.getReference().child("organizations/" + orgId + ".png");

        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<android.net.Uri>() {
            @Override
            public void onComplete(@NonNull Task<android.net.Uri> task) {
                if (task.isSuccessful()) {
                    // URL ottenuto
                    callback.onSuccess(task.getResult().toString());
                } else {
                    // Errore nel recuperare URL
                    callback.onFailure(task.getException());
                }
            }
        });
    }

    // Interfaccia callback per login
    public interface FirebaseAuthCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    // Interfaccia callback per recupero URL immagine
    public interface FirebaseStorageCallback {
        void onSuccess(String imageUrl);
        void onFailure(Exception e);
    }
}

