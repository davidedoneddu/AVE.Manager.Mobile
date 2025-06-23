package it.sal.disco.unimib.avemanager.data.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.ui.model.Evento;
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
        apiDataSource.fetchEventList(callback);
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


}

