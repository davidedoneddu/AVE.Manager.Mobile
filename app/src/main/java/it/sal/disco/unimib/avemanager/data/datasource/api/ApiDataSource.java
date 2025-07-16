package it.sal.disco.unimib.avemanager.data.datasource.api;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.InputStream;

import javax.inject.Inject;

import it.sal.disco.unimib.avemanager.data.mapper.InvitatoMapper;
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
import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDataSource {

    private final ApiService apiService;
    private final TokenProvider tokenProvider;

    @Inject
    public ApiDataSource(ApiService apiService, TokenProvider tokenProvider) {
        this.apiService = apiService;
        this.tokenProvider = tokenProvider;
    }

    public void login(String token, DataCallback<String> callback) {
        FirebaseTokenRequestDTO request = new FirebaseTokenRequestDTO(token);

        apiService.firebaseLogin(request).enqueue(new Callback<FirebaseTokenResponseDTO>() {
            @Override
            public void onResponse (@NonNull Call<FirebaseTokenResponseDTO> call, @NonNull Response<FirebaseTokenResponseDTO> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    String authHeader = response.headers().get("Authorization");
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String jwtToken = authHeader.substring(7);
                        tokenProvider.saveToken(jwtToken); // lo usi in tutte le chiamate future
                    }

                    callback.onSuccess("Login Effettuato");
                } else {
                    //callback.onFailure(new Exception("Login fallito: " + response.code()));
                    callback.onFailure(new Exception("Login fallito"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<FirebaseTokenResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchOrganizationList(DataCallback<OrganizationListResponseDTO> callback) {
        apiService.getEnvList().enqueue(new Callback<OrganizationListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<OrganizationListResponseDTO> call,
                                   @NonNull Response<OrganizationListResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Lista organizzazioni vuota"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrganizationListResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void selectOrganization(Organization selectedOrganization, DataCallback<String> callback) {
        SelectEnvironmentRequestDTO dto = new SelectEnvironmentRequestDTO();
        dto.setOrgId(selectedOrganization.getId());
        apiService.selectEnv(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(!response.body().isOk()){
                        callback.onFailure(new Exception(response.body().getErrorMessage()));
                    }
                    callback.onSuccess(response.body().getResponseMessage());
                } else {
                    callback.onFailure(new Exception("Organizzazione non trovata"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchEventList(DataCallback<EventListResponseDTO> callback) {
        apiService.getEnvEvents().enqueue(new Callback<EventListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventListResponseDTO> call,
                                   @NonNull Response<EventListResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Lista eventi vuota"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventListResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void selectEvent(Evento selectedEvent, DataCallback<String> callback) {
        SelectEventRequestDTO dto = new SelectEventRequestDTO();
        dto.setEventId(selectedEvent.getId());
        apiService.selectEvent(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(!response.body().isOk()){
                        callback.onFailure(new Exception(response.body().getErrorMessage()));
                    }
                    callback.onSuccess(response.body().getResponseMessage());
                } else {
                    callback.onFailure(new Exception("Evento non trovato"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchImage(String fileName, DataCallback<Bitmap> callback) {
        apiService.downloadImage(fileName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNullCall<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    callback.onSuccess(bitmap);
                } else {
                    callback.onFailure(new Exception("Errore nel download dell'immagine"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchInvitatiList(DataCallback<InvitatiListResponseDTO> callback) {
        apiService.getListInvitati().enqueue(new Callback<InvitatiListResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<InvitatiListResponseDTO> call,
                                   @NonNull Response<InvitatiListResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Lista invitati vuota"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<InvitatiListResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void doCheckIn(String code, DataCallback<String> callback) {
        InvitatoCheckInRequestDTO checkInRequestDTO = new InvitatoCheckInRequestDTO();
        checkInRequestDTO.setCodice(code);
        apiService.doCheckIn(checkInRequestDTO).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(Call<BaseResponseDTO> call, Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if(!response.body().isOk()){
                        callback.onFailure(new Exception(response.body().getErrorMessage()));
                    }
                    String responseMessage = response.body().getResponseMessage();

                    callback.onSuccess(responseMessage);
                } else {
                    callback.onFailure(new Exception("Errore durante il check in"));
                }
            }

            @Override
            public void onFailure(Call<BaseResponseDTO> call, Throwable t) {
                callback.onFailure(new Exception("Errore durante il check in"));
            }
        });
    }

    public void logout(DataCallback<BaseResponseDTO> callback) {

        apiService.logout().enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nel logout"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void insertInvitato(Invitato model, DataCallback<BaseResponseDTO> callback) {
        InvitatoMapper mapper= new InvitatoMapper();
        InvitatoDTO dto =mapper.toDTO(model);

        apiService.insertInvitato(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nella create di "+ model.getInvNome() + " "+ model.getInvCognome()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void updateInvitato(Invitato model, DataCallback<BaseResponseDTO> callback) {
        InvitatoMapper mapper= new InvitatoMapper();
        InvitatoDTO dto =mapper.toDTO(model);

        apiService.updateInvitato(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nella update di "+ model.getInvNome() + " "+ model.getInvCognome()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void deleteInvitato(Invitato model, DataCallback<BaseResponseDTO> callback) {
        InvitatoMapper mapper= new InvitatoMapper();
        InvitatoDTO dto = mapper.toDTO(model);

        apiService.deleteInvitato(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nella delete di "+ model.getInvNome() + " "+ model.getInvCognome()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void sendEmailInvitato(Invitato model, DataCallback<BaseResponseDTO> callback) {
        InvitatoMapper mapper= new InvitatoMapper();
        InvitatoDTO dto = mapper.toDTO(model);

        apiService.sendEmailInvitato(dto).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nel'invio della email di "+ model.getInvNome() + " "+ model.getInvCognome()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchEventData(DataCallback<EventDataResponseDTO> callback) {
        apiService.getDataEvento().enqueue(new Callback<EventDataResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDataResponseDTO> call,
                                   @NonNull Response<EventDataResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nella ricezione dati evento"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventDataResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void sendEmailHelp(EmailHelpRequestDTO requestDTO, DataCallback<BaseResponseDTO> callback) {
        apiService.sendHelpEmail(requestDTO).enqueue(new Callback<BaseResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseDTO> call,
                                   @NonNull Response<BaseResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nell'invio dell'email'"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseDTO> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}