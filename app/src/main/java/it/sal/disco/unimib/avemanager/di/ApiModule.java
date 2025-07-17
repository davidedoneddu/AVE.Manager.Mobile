package it.sal.disco.unimib.avemanager.di;

import android.content.Context;
import android.util.Log;


import java.security.cert.CertificateException;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiCookieJar;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiInterceptor;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiService;
import it.sal.disco.unimib.avemanager.data.datasource.api.AuthFailureHandler;
import it.sal.disco.unimib.avemanager.data.datasource.api.TokenProvider;
import it.sal.disco.unimib.avemanager.util.Costants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class) // singleton per tutta l'app
public class ApiModule {

    private static final String BASE_URL = Costants.BASE_PATH;

    @Provides
    @Singleton
    public static ApiCookieJar provideApiCookieJar() {
        return new ApiCookieJar();
    }

    @Provides
    @Singleton
    public static TokenProvider provideTokenProvider(@ApplicationContext Context context) {
        // Implementa questa interfaccia secondo la tua logica, magari leggendo token da SharedPreferences o DB
        return new TokenProvider(context);
    }

    @Provides
    @Singleton
    public static AuthFailureHandler provideAuthFailureHandler(@ApplicationContext Context context) {
        // Implementa questa interfaccia, es: logout o refresh token
        return new AuthFailureHandler(context) ;
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(
            ApiCookieJar cookieJar,
            TokenProvider tokenProvider,
            AuthFailureHandler authFailureHandler) {

//        return new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                .addInterceptor(new ApiInterceptor(tokenProvider, authFailureHandler))
//                .build();

        // Logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // <-- Mostra body

        try {
//            // Trust manager che accetta tutti i certificati
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override public java.security.cert.X509Certificate[] getAcceptedIssuers() { return new java.security.cert.X509Certificate[]{}; }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .addInterceptor(new ApiInterceptor(tokenProvider, authFailureHandler))
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
                        Log.d("MANUAL_LOG", responseBody.string());

                        return response;
                    })
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true);

            return builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
