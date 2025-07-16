package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.EventRepository;
import it.sal.disco.unimib.avemanager.ui.model.EventData;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class EventDataViewModel extends ViewModel {

    private final EventRepository eventRepository;

    private final MutableLiveData<EventData> eventData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isSended = new MutableLiveData<>(false);
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();

    @Inject
    public EventDataViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<EventData> getEventData() {
        return eventData;
    }

    public LiveData<Boolean> getIsSended() {
        return isSended;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void loadEventData() {
        isLoading.setValue(true);

        eventRepository.fetchEventData(new DataCallback<EventData>() {
            @Override
            public void onSuccess(EventData result) {
                isLoading.setValue(false);
                eventData.setValue(result);
            }

            @Override
            public void onFailure(Throwable t) {
                isLoading.setValue(false);
                error.setValue(t);
            }
        });
    }

    public void sendEmailHelp(String subject, String body) {
        isLoading.postValue(true);
        eventRepository.sendEmailHelp(subject, body,new DataCallback<Boolean>(){

            @Override
            public void onSuccess(Boolean result) {
                isLoading.postValue(false);
                isSended.postValue(true);
            }

            @Override
            public void onFailure(Throwable t) {
                isLoading.postValue(false);
                error.setValue(t);
            }
        });
    }


}
