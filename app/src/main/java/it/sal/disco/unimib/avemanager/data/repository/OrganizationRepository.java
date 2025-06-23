package it.sal.disco.unimib.avemanager.data.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@Singleton
public class OrganizationRepository {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final FirebaseDataSource firebaseDataSource;
    private final ApiDataSource apiDataSource;

    @Inject
    public OrganizationRepository(FirebaseDataSource firebaseDataSource, ApiDataSource apiDataSource)
    {
        this.firebaseDataSource = firebaseDataSource;
        this.apiDataSource = apiDataSource;
    }

    public void fetchOrganizationList(DataCallback<List<Organization>> callback) {
        apiDataSource.fetchOrganizationList(callback);
    }

    public void fetchOrganizationImage(String orgId, DataCallback<String> callback) {
        firebaseDataSource.getOrganizationImageUrl(orgId , new DataCallback<String>(){
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void selectOrganization(Organization selectedOrganization, DataCallback<String> dataCallback) {
        apiDataSource.selectOrganization(selectedOrganization, new DataCallback<String>() {
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

