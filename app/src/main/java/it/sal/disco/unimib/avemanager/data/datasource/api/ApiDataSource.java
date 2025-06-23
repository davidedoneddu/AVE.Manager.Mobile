package it.sal.disco.unimib.avemanager.data.datasource.api;

import it.sal.disco.unimib.avemanager.data.model.FirebaseTokenRequestDto;
import it.sal.disco.unimib.avemanager.data.model.OrganizationDto;
import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ApiDataSource {

    private final ApiService apiService;

    @Inject
    public ApiDataSource(ApiService apiService) {
        this.apiService = apiService;
    }

    public void login(String token, DataCallback<String> callback) {
        FirebaseTokenRequestDto request = new FirebaseTokenRequestDto(token);
        apiService.login(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    //callback.onFailure(new Exception("Login fallito: " + response.code()));
                    callback.onSuccess("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //callback.onFailure(t);
                callback.onSuccess("");
            }
        });
    }

    public void getOrganizations(String token, DataCallback<List<OrganizationDto>> callback) {
        apiService.getOrganizations("Bearer " + token).enqueue(new Callback<List<OrganizationDto>>() {
            @Override
            public void onResponse(Call<List<OrganizationDto>> call, Response<List<OrganizationDto>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("Errore nel recupero organizzazioni: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<OrganizationDto>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void selectOrganization(Organization selectedOrganization, DataCallback<String> callback) {
        // Simulazione di selezione organizzazione da backend
        // Qui ci sarà una chiamata HTTP in un caso reale
        // Per ora rispondiamo sempre con una lista vuota o mock
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess("mockList");
        }, 2000);  // delay in millisecondi (2000 = 2 secondi)
    }

    public void fetchOrganizationList(DataCallback<List<Organization>> callback) {
        // Non usato attualmente, visto che il fetch è fatto in locale nel repository
        // Ma nel caso ti serva, puoi simulare così:
        List<Organization> mockList = new ArrayList<>();
        mockList.add(new Organization("1", "ASSOLOMBARDA", ""));
        mockList.add(new Organization("2", "CONFINDUSTRIA COMO", ""));
        mockList.add(new Organization("3", "LECCO SONDRIO FEDERCHIMICA", ""));

        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(mockList);
        }, 2000);  // delay in millisecondi (2000 = 2 secondi)
    }
    public void selectEvent(Evento selectedEvent, DataCallback<String> callback) {
        // Simulazione di selezione organizzazione da backend
        // Qui ci sarà una chiamata HTTP in un caso reale
        // Per ora rispondiamo sempre con una lista vuota o mock
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess("mockList");
        }, 2000);  // delay in millisecondi (2000 = 2 secondi)
    }

    public void fetchEventList(DataCallback<List<Evento>> callback) {
        // Non usato attualmente, visto che il fetch è fatto in locale nel repository
        List<Evento> mockList = new ArrayList<>();
        mockList.add(new Evento("1", "Assemblea Privata", "Elezione del presidente"));
        mockList.add(new Evento("2", "Consiglio Generale", "Revisione statuto associativo"));
        mockList.add(new Evento("3", "Assemblea gruppo Innovation Services", "Elezione del presidente e dei consiglieri"));

        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            callback.onSuccess(mockList);
        }, 2000);  // delay in millisecondi (2000 = 2 secondi)
    }
}