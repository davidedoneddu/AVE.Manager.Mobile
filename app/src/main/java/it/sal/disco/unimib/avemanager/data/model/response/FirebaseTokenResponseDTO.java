package it.sal.disco.unimib.avemanager.data.model.response;

public class FirebaseTokenResponseDTO extends BaseResponseDTO{
    private String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
