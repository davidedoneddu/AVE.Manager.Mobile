package it.sal.disco.unimib.avemanager.data.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.mapper.EventDataMapper;
import it.sal.disco.unimib.avemanager.data.mapper.EventoMapper;
import it.sal.disco.unimib.avemanager.data.model.request.EmailHelpRequestDTO;
import it.sal.disco.unimib.avemanager.data.model.response.BaseResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.EventDataResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.EventListResponseDTO;
import it.sal.disco.unimib.avemanager.ui.model.EventData;
import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@Singleton
public class EventRepository {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ApiDataSource apiDataSource;

    @Inject
    public EventRepository( ApiDataSource apiDataSource)
    {
        this.apiDataSource = apiDataSource;
    }

    public void fetchEventList(DataCallback<List<Evento>> callback) {
        apiDataSource.fetchEventList(new DataCallback<EventListResponseDTO>() {
            @Override
            public void onSuccess(EventListResponseDTO result) {
                if(!result.isOk()){
                    callback.onFailure(new Exception("Errore nel leggere la lista eventi : " + result.getErrorMessage()));
                }
                EventoMapper eventoMapper = new EventoMapper();
                List<Evento> eventoList = eventoMapper.toModelList(result.getEventiList());

                callback.onSuccess(eventoList);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void selectEvent(Evento selectedEvent, DataCallback<String> dataCallback) {
        apiDataSource.selectEvent(selectedEvent, new DataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                dataCallback.onSuccess(data);
            }

            @Override
            public void onFailure(Throwable t) {
                dataCallback.onSuccess(" ");
            }
        });
    }


    public void fetchEventData( DataCallback<EventData> callback) {
        apiDataSource.fetchEventData(new DataCallback<EventDataResponseDTO>(){

            @Override
            public void onSuccess(EventDataResponseDTO result) {
                if(!result.isOk()){
                    callback.onFailure(new Exception("Errore nella richiesta dei dati evento : " + result.getErrorMessage()));
                }
                EventDataMapper mapper = new EventDataMapper();
                EventData model = mapper.toModel(result);
                callback.onSuccess(model);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });


    }


    public void sendEmailHelp(String subject, String body, DataCallback<Boolean> callback) {
        EmailHelpRequestDTO requestDTO = new EmailHelpRequestDTO();
        requestDTO.setEmailBody(body);
        requestDTO.setEmailSubject(subject);
        apiDataSource.sendEmailHelp(requestDTO, new DataCallback<BaseResponseDTO>(){

            @Override
            public void onSuccess(BaseResponseDTO result) {
                if(!result.isOk()){
                    callback.onFailure(new Exception("Errore durante l'invio della mail di help"));
                }

                callback.onSuccess(true);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}

