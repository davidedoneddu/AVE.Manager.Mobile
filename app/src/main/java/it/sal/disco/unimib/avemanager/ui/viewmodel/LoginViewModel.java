package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.sal.disco.unimib.avemanager.data.repository.AuthRepository;

public class LoginViewModel extends ViewModel {

    public enum LoginState { IDLE, LOADING, SUCCESS, ERROR }

    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>(LoginState.IDLE);
    private final AuthRepository authRepository = new AuthRepository();

    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public void login(String username, String password) {
        loginState.setValue(LoginState.LOADING);

        new Thread(() -> {
            boolean result = authRepository.login(username, password);
            if (result) {
                loginState.postValue(LoginState.SUCCESS);
            } else {
                loginState.postValue(LoginState.ERROR);
            }
        }).start();
    }
}
