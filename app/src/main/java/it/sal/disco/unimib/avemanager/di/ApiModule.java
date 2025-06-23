package it.sal.disco.unimib.avemanager.di;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiCookieJar;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiInterceptor;
import it.sal.disco.unimib.avemanager.data.datasource.api.AuthFailureHandler;
import it.sal.disco.unimib.avemanager.data.datasource.api.TokenProvider;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class) // singleton per tutta l'app
public class ApiModule {

    private static final String BASE_URL = "https://tuo-dominio-api.com/api/";

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

        return new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new ApiInterceptor(tokenProvider, authFailureHandler))
                .build();
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
