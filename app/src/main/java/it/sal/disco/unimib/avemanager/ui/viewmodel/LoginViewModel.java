package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.AuthRepository;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    public enum LoginState { IDLE, LOADING, SUCCESS, ERROR }
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>(LoginState.IDLE);
    private final AuthRepository authRepository;

    @Inject
    public LoginViewModel(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public void login(String email, String password) {
        loginState.setValue(LoginState.LOADING);

        authRepository.login(email, password, new DataCallback<String>() {
            @Override
            public void onSuccess(String token) {
                // Puoi salvare il token qui se vuoi (SharedPreferences o DataStore)
                loginState.postValue(LoginState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                loginState.postValue(LoginState.ERROR);
            }
        });
    }
}
