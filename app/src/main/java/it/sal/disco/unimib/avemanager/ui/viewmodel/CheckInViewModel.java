package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.CheckInRepository;
import it.sal.disco.unimib.avemanager.ui.model.CheckInResult;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class CheckInViewModel extends ViewModel {



    public enum CheckInState { IDLE, LOADING, SUCCESS, ERROR }

    private final MutableLiveData<CheckInState> checkInState = new MutableLiveData<>(CheckInState.IDLE);
    private final MutableLiveData<CheckInResult> checkInResult = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final CheckInRepository repository;

    @Inject
    public CheckInViewModel(CheckInRepository repository) {
        this.repository = repository;
    }

    public LiveData<CheckInResult> getCheckInResult() { return checkInResult; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<CheckInState> getCheckInState() { return checkInState; }

    public void checkInWithQr(String qrCode) {
        checkInState.postValue(CheckInState.LOADING);

        if(qrCode.isEmpty()){
            errorMessage.postValue("Il codice utente per il check in non è valido");
            checkInState.postValue(CheckInState.ERROR);
            return;
        }
        repository.checkInWithQr(qrCode, new DataCallback<CheckInResult>() {
            @Override
            public void onSuccess(CheckInResult result) {
                checkInResult.postValue(result);
                checkInState.postValue(CheckInState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                errorMessage.postValue(t.getMessage());
                checkInState.postValue(CheckInState.ERROR);
            }
        });
    }

    public void resetCheckInResult() {
        checkInResult.postValue(null);
    }
}
