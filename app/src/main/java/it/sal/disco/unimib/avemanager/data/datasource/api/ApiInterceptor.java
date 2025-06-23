package it.sal.disco.unimib.avemanager.data.datasource.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {

    private final TokenProvider tokenProvider;
    private final AuthFailureHandler authFailureHandler;



    public ApiInterceptor(TokenProvider tokenProvider, AuthFailureHandler authFailureHandler) {
        this.tokenProvider = tokenProvider;
        this.authFailureHandler = authFailureHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String url = originalRequest.url().toString();

        // Non aggiungere header Authorization sulla chiamata login
        if (url.contains("/login")) {
            return chain.proceed(originalRequest);
        }

        // Prendi il token attuale
        String token = tokenProvider.getToken();

        Request.Builder requestBuilder = originalRequest.newBuilder();

        if (token != null && !token.isEmpty()) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        Request requestWithAuth = requestBuilder.build();

        Response response = chain.proceed(requestWithAuth);

        // Se ricevi 401, gestisci la situazione (es. logout o refresh)
        if (response.code() == 401) {
            // qui puoi chiamare authFailureHandler.onAuthFailure()
            authFailureHandler.onAuthFailure();
        }

        return response;
    }

    // Interfaccia per fornire il token (può essere implementata in Repository o ViewModel)
    public interface TokenProvider {
        String getToken();
    }

    // Interfaccia per gestire errore auth (es. logout o refresh)
    public interface AuthFailureHandler {
        void onAuthFailure();
    }
}