package it.sal.disco.unimib.avemanager.data.model.request;

public class FirebaseTokenRequestDTO {
    private String IdToken;

    public FirebaseTokenRequestDTO(String firebaseToken) {
        this.IdToken = firebaseToken;
    }

    public String getFirebaseToken() {
        return IdToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.IdToken = firebaseToken;
    }
}
