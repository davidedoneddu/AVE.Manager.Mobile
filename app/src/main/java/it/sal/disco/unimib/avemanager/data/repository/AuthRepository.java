package it.sal.disco.unimib.avemanager.data.repository;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.data.model.response.BaseResponseDTO;
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
        firebaseDataSource.login(email, password, new DataCallback<String>() {
            @Override
            public void onSuccess(String firebaseToken) {
                apiDataSource.login(firebaseToken, new DataCallback<String>() {
                    @Override
                    public void onSuccess(String backendToken) {

                        callback.onSuccess(backendToken);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        callback.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public void logout(DataCallback<String> callback) {
        firebaseDataSource.logout(new DataCallback<String>() {

            @Override
            public void onSuccess(String result) {
                apiDataSource.logout(new DataCallback<BaseResponseDTO>() {
                    @Override
                    public void onSuccess(BaseResponseDTO result) {
                        if(!result.isOk()){
                            callback.onFailure(new Exception(result.getErrorMessage()));
                        }
                        callback.onSuccess(result.getResponseMessage());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        callback.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });


    }
}
