package it.sal.disco.unimib.avemanager.data.model;

public class FirebaseTokenRequestDto {
    private String IdToken;

    public FirebaseTokenRequestDto(String firebaseToken) {
        this.IdToken = firebaseToken;
    }

    public String getFirebaseToken() {
        return IdToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.IdToken = firebaseToken;
    }
}
