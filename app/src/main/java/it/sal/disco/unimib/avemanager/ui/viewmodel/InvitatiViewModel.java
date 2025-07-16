package it.sal.disco.unimib.avemanager.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import it.sal.disco.unimib.avemanager.data.repository.InvitatiRepository;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@HiltViewModel
public class InvitatiViewModel extends ViewModel {

    private final MutableLiveData<List<Invitato>> invitatiLiveData = new MutableLiveData<>(new ArrayList<>());
    private final InvitatiRepository repository;

    private final List<Invitato> loadedInvitati = new ArrayList<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>(false);

    private final MutableLiveData<Invitato> currentInvitato = new MutableLiveData<>();

    private int currentPage = 0;
    private static final int PAGE_SIZE = 50;

    private String currentQuery = "";

    @Inject
    public InvitatiViewModel(InvitatiRepository repository) {
        this.repository = repository;
        loadPage(0); // carica la prima pagina all'avvio

        repository.getDataSynced().observeForever(synced -> {
            if (Boolean.TRUE.equals(synced)) {
                refresh();
                repository.resetSynced();
            }
        });
    }

    public LiveData<List<Invitato>> getInvitatiLiveData() {
        return invitatiLiveData;

    }

    public LiveData<Invitato> getCurrentInvitato() {
        return currentInvitato;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }

    public boolean isLoading() {
        Boolean value = isLoading.getValue();
        return value != null && value;
    }

    public boolean isLastPage() {
        Boolean value = isLastPage.getValue();
        return value != null && value;
    }

    public void setSearchQuery(String query) {
        if (!query.equals(currentQuery)) {
            currentQuery = query;
            currentPage = 0;
            isLastPage.setValue(false);
            invitatiLiveData.postValue(new ArrayList<>()); // svuota lista
            loadPage(0);
        }
    }

    public void loadMore() {
        if (!isLoading() && !isLastPage()) {
            loadPage(currentPage + 1);
        }
    }

    private void loadPage(int page) {
        isLoading.postValue(true);

        repository.getInvitati(currentQuery, page, PAGE_SIZE, new DataCallback<List<Invitato>>() {
            @Override
            public void onSuccess(List<Invitato> newData) {
                List<Invitato> currentList = new ArrayList<>(invitatiLiveData.getValue());
                if (page == 0) {
                    currentList = new ArrayList<>(); // resetta per nuova ricerca
                }
                currentList.addAll(newData);
                invitatiLiveData.postValue(currentList);

                isLoading.postValue(false);
                isLastPage.postValue(newData.size() < PAGE_SIZE);
                currentPage = page;

            }

            @Override
            public void onFailure(Throwable errorMessage) {
                isLoading.postValue(false);
                invitatiLiveData.postValue(new ArrayList<>());
            }
        });
    }

    public void refresh() {
        currentPage = 0;
        isLastPage.postValue(false);
        invitatiLiveData.postValue(new ArrayList<>());
        loadPage(0);
    }


    public void saveInvitato() {
        Invitato invitato = currentInvitato.getValue();
        if (invitato == null) return;

        isLoading.setValue(true);
        if (invitato.getInvId() != 0) {
            repository.updateInvitato(invitato, new DataCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    isLoading.postValue(false);
                    refresh();
                }
                @Override
                public void onFailure(Throwable error) {
                    isLoading.postValue(false);
                }
            });
        } else {
            repository.insertInvitato(invitato, new DataCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    isLoading.postValue(false);
                    refresh();
                }
                @Override
                public void onFailure(Throwable error) {
                    isLoading.postValue(false);
                }
            });
        }
    }

    public void loadInvitato(int id) {
        isLoading.setValue(true);
        repository.getInvitatoById(id, new DataCallback<Invitato>() {
            @Override
            public void onSuccess(Invitato invitato) {
                currentInvitato.postValue(invitato);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable error) {
                // gestione errore
                isLoading.postValue(false);
            }
        });
    }

    public void deleteInvitato(Invitato invitato) {
        isLoading.setValue(true);
        repository.deleteInvitato(invitato, new DataCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {

                isLoading.postValue(false);
                refresh();
            }

            @Override
            public void onFailure(Throwable error) {
                isLoading.postValue(false);

            }
        });
    }

    public void createNewInvitato() {
        Invitato nuovo = new Invitato();

        currentInvitato.setValue(nuovo);
    }


    public void sendEmailToInvitato(Invitato invitato) {
        isLoading.setValue(true);
        repository.sendEmail(invitato, new DataCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {

                isLoading.postValue(false);
                refresh();
            }

            @Override
            public void onFailure(Throwable error) {
                isLoading.postValue(false);

            }
        });
    }

}
