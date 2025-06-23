package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.EventRepository;
import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class EventViewModel extends ViewModel {

    public enum EventState { IDLE, LOADING, SUCCESS, ERROR }

    private final MutableLiveData<EventState> eventState = new MutableLiveData<>(EventState.IDLE);
    private final MutableLiveData<List<Evento>> eventList = new MutableLiveData<>();
    private final MutableLiveData<Evento> selectedEvent = new MutableLiveData<>();

    private final EventRepository eventRepository;

    @Inject
    public EventViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<EventState> getEventState() {
        return eventState;
    }

    public LiveData<List<Evento>> getEventListLiveData() {
        return eventList;
    }

    public LiveData<Evento> getSelectedEvent() {
        return selectedEvent;
    }

    public void selectEvent(Evento evento) {
        eventState.setValue(EventViewModel.EventState.LOADING);
        selectedEvent.setValue(evento);

        eventRepository.selectEvent(evento, new DataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                eventState.setValue(EventViewModel.EventState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                eventState.setValue(EventViewModel.EventState.ERROR);
            }
        });
    }

    public void getEventList() {
        eventState.setValue(EventState.LOADING);
        eventRepository.fetchEventList(new DataCallback<List<Evento>>() {
            @Override
            public void onSuccess(List<Evento> data) {
                eventList.setValue(data);
                eventState.setValue(EventState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                eventState.setValue(EventState.ERROR);
            }
        });
    }


}
