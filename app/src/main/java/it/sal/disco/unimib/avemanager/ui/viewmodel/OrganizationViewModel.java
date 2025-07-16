package it.sal.disco.unimib.avemanager.ui.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.OrganizationRepository;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class OrganizationViewModel extends ViewModel {

    public enum OrganizationState { IDLE, LOADING, SUCCESS, ERROR }

    private final MutableLiveData<OrganizationState> organizationState = new MutableLiveData<>(OrganizationState.IDLE);
    private final MutableLiveData<List<Organization>> organizationList = new MutableLiveData<>();
    private final MutableLiveData<Organization> selectedOrganization = new MutableLiveData<>();

    private final OrganizationRepository organizationRepository;

    @Inject
    public OrganizationViewModel(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public LiveData<OrganizationState> getOrganizationState() {
        return organizationState;
    }

    public LiveData<List<Organization>> getOrganizationListLiveData() {
        return organizationList;
    }

    public LiveData<Organization> getSelectedOrganization() {
        return selectedOrganization;
    }

    public void selectOrganization(Organization organization) {
        organizationState.setValue(OrganizationState.LOADING);
        selectedOrganization.setValue(organization);

        organizationRepository.selectOrganization(organization, new DataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                organizationState.setValue(OrganizationState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                organizationState.setValue(OrganizationState.ERROR);
            }
        });
    }

    public void getOrganizationList() {
        organizationState.setValue(OrganizationState.LOADING);
        organizationRepository.fetchOrganizationList(new DataCallback<List<Organization>>() {
            @Override
            public void onSuccess(List<Organization> data) {
                organizationList.setValue(data);
                organizationState.setValue(OrganizationState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                organizationState.setValue(OrganizationState.ERROR);
            }
        });
    }

    public void getOrganizationImage(String orgId, DataCallback<Bitmap> callback) {
        String fileName = orgId+".png";
        organizationState.setValue(OrganizationState.LOADING);
        organizationRepository.fetchOrganizationImage(fileName, new DataCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap image) {
                callback.onSuccess(image);
                organizationState.setValue(OrganizationState.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
                organizationState.setValue(OrganizationState.ERROR);
            }
        });
    }
}
