package it.sal.disco.unimib.avemanager.data.datasource.api;


import android.content.Context;
import android.content.SharedPreferences;

public class TokenProvider implements ApiInterceptor.TokenProvider {

    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_JWT_TOKEN = "jwt_token";

    private final SharedPreferences sharedPreferences;

    public TokenProvider(Context context)  {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        sharedPreferences.edit()
                .putString(KEY_JWT_TOKEN, token)
                .apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_JWT_TOKEN, null);
    }

    public void clearToken() {
        sharedPreferences.edit()
                .remove(KEY_JWT_TOKEN)
                .apply();
    }
}
