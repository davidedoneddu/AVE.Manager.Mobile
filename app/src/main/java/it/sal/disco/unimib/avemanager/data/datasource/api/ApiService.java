package it.sal.disco.unimib.avemanager.data.datasource.api;

import it.sal.disco.unimib.avemanager.data.model.FirebaseTokenRequestDto;
import it.sal.disco.unimib.avemanager.data.model.OrganizationDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface ApiService {

    @POST("auth/login")
    Call<String> login(@Body FirebaseTokenRequestDto request); // ritorna un token

    @GET("organization")
    Call<List<OrganizationDto>> getOrganizations(@Header("Authorization") String bearerToken);
}
