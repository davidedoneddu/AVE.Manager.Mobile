package it.sal.disco.unimib.avemanager.data.datasource.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class ApiCookieJar implements CookieJar {

    // Mappa host -> lista cookie
    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        List<Cookie> existingCookies = cookieStore.getOrDefault(url.host(), new ArrayList<>());
        Map<String, Cookie> cookieMap = new HashMap<>();

        // Inserisci i vecchi
        for (Cookie cookie : existingCookies) {
            cookieMap.put(cookie.name(), cookie);
        }

        // Sovrascrivi/aggiungi i nuovi
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.name(), cookie);
        }

        // Salva l'unione
        cookieStore.put(url.host(), new ArrayList<>(cookieMap.values()));
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        // Ritorna i cookie da inviare per l'host della richiesta
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<>();
    }
}

