//package it.sal.disco.unimib.avemanager.data.datasource.api;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClient {
//    private static final String BASE_URL = "https://tuo-dominio-api.com/api/";
//    private static Retrofit retrofit;
//
//    public static ApiService getApiService(TokenProvider tokenProvider, AuthFailureHandler authFailureHandler, ApiCookieJar cookieJar) {
//        if (retrofit == null) {
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .cookieJar(cookieJar)  // gestisce i cookie
//                    .addInterceptor(new ApiInterceptor(tokenProvider, authFailureHandler)) // aggiunge token e gestisce 401
//                    .build();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(okHttpClient) // qui metti l'okhttp client personalizzato
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit.create(ApiService.class);
//    }
//}
