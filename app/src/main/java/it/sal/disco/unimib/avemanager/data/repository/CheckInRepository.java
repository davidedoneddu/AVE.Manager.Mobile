package it.sal.disco.unimib.avemanager.data.repository;

import javax.inject.Singleton;
import javax.inject.Inject;

import it.sal.disco.unimib.avemanager.ui.model.CheckInResult;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@Singleton
public class CheckInRepository {

    @Inject
    public CheckInRepository() {
        // eventuale setup
    }

    public void checkInWithQr(String qrCode, DataCallback<CheckInResult> callback) {
        // Simula chiamata a Firestore/API
        if (qrCode.equals("VALID_CODE")) {
            CheckInResult result = new CheckInResult("Mario Rossi", "Ingresso valido");
            callback.onSuccess(result);
        } else {
            callback.onFailure(new Exception("Codice non valido"));
        }
    }
}
