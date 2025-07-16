package it.sal.disco.unimib.avemanager.data.datasource.api;

import it.sal.disco.unimib.avemanager.data.model.request.EmailHelpRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.request.FirebaseTokenRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.request.InvitatoCheckInRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.request.InvitatoDTO;
import it.sal.disco.unimib.avemanager.data.model.request.SelectEnvironmentRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.request.SelectEventRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.response.BaseResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.EventDataResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.EventListResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.FirebaseTokenResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.InvitatiListResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.OrganizationListResponseDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    // AUTHENTICATION
//    @Multipart
//    @POST("api/auth/router-login")
//    Call<Void> login(@Body LoginRequestDTO request);

    @POST("api/auth/logout")
    Call<BaseResponseDTO> logout();

    // ORGANIZATION
    @GET("api/manage/getEnvList")
    Call<OrganizationListResponseDTO> getEnvList();

    @POST("api/manage/selectEnv")
    Call<BaseResponseDTO> selectEnv(@Body SelectEnvironmentRequestDTO request);

    // EVENTI
    @GET("api/manage/getEnvEvents")
    Call<EventListResponseDTO> getEnvEvents();

    @POST("api/manage/selectEvent")
    Call<BaseResponseDTO> selectEvent(@Body SelectEventRequestDTO request);

    @GET("api/eventi/getDataEvento")
    Call<EventDataResponseDTO> getDataEvento();

    @POST("api/eventi/sendHelpEmail")
    Call<BaseResponseDTO> sendHelpEmail(@Body EmailHelpRequestDTO request);

    // FIREBASE AUTH
    @POST("api/firebase/login")
    Call<FirebaseTokenResponseDTO> firebaseLogin(@Body FirebaseTokenRequestDTO request);

    // INVITATI
    @GET("api/invitati/getlistinvitati")
    Call<InvitatiListResponseDTO> getListInvitati();

    @POST("api/invitati/updateInvitato")
    Call<BaseResponseDTO> updateInvitato(@Body InvitatoDTO request);
    @POST("api/invitati/createInvitato")
    Call<BaseResponseDTO> insertInvitato(@Body InvitatoDTO request);

    @POST("api/invitati/deleteInvitato")
    Call<BaseResponseDTO> deleteInvitato(@Body InvitatoDTO request);

    @POST("api/invitati/doCheckIn")
    Call<BaseResponseDTO> doCheckIn(@Body InvitatoCheckInRequestDTO request);

    @POST("api/invitati/sendEmailInvitato")
    Call<BaseResponseDTO> sendEmailInvitato(@Body InvitatoDTO request);

    @GET("Images/{filename}")
    Call<ResponseBody> downloadImage(@Path("filename") String filename);
}
