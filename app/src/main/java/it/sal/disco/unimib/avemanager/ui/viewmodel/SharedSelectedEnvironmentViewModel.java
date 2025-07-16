package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.sal.disco.unimib.avemanager.ui.model.Evento;
import it.sal.disco.unimib.avemanager.ui.model.Organization;

public class SharedSelectedEnvironmentViewModel extends ViewModel {

    private final MutableLiveData<Organization> selectedOrganization = new MutableLiveData<>();
    private final MutableLiveData<Evento> selectedEvent = new MutableLiveData<>();

    public void setSelectedOrganization(Organization org) {
        selectedOrganization.setValue(org);
    }

    public LiveData<Organization> getSelectedOrganization() {
        return selectedOrganization;
    }

    public void setSelectedEvent(Evento event) {
        selectedEvent.setValue(event);
    }

    public LiveData<Evento> getSelectedEvent() {
        return selectedEvent;
    }
}
