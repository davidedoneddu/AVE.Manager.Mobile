package it.sal.disco.unimib.avemanager.data.repository;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.util.DataCallback;

public class AuthRepository {

    private final FirebaseDataSource firebaseDataSource;
    private final ApiDataSource apiDataSource;

    public AuthRepository(FirebaseDataSource firebaseDataSource, ApiDataSource apiDataSource){
        this.firebaseDataSource = firebaseDataSource;
        this.apiDataSource = apiDataSource;
    }
    /**
     * Login con user e password Firebase, poi chiama la API con il token Firebase
     */
    public void login(String email, String password, DataCallback<String> callback) {
        // 1. Login su Firebase
        firebaseDataSource.login(email, password, new DataCallback<String>() {
            @Override
            public void onSuccess(String firebaseToken) {
                // 2. Login su API con il token Firebase ricevuto
                apiDataSource.login(firebaseToken, new DataCallback<String>() {
                    @Override
                    public void onSuccess(String backendToken) {
                        // Successo completo: ritorna token backend
                        callback.onSuccess(backendToken);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // Errore login API
                        callback.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                // Errore login Firebase
                callback.onFailure(t);
            }
        });
    }

    private String loginApi (String token){
        return "";
    }
}
