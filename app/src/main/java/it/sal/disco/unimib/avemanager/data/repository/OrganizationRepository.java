package it.sal.disco.unimib.avemanager.data.repository;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.mapper.OrganizationMapper;
import it.sal.disco.unimib.avemanager.data.model.response.OrganizationListResponseDTO;
import it.sal.disco.unimib.avemanager.ui.model.Organization;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@Singleton
public class OrganizationRepository {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ApiDataSource apiDataSource;

    @Inject
    public OrganizationRepository(ApiDataSource apiDataSource)
    {
        this.apiDataSource = apiDataSource;
    }

    public void fetchOrganizationList(DataCallback<List<Organization>> callback) {
        apiDataSource.fetchOrganizationList(new DataCallback<OrganizationListResponseDTO>() {
            @Override
            public void onSuccess(OrganizationListResponseDTO result) {
                OrganizationMapper organizationMapper = new OrganizationMapper();

                if(!result.isOk()){
                    callback.onFailure(new Exception("Error while retriving organization list :" +result.getErrorMessage()));
                }

                List<Organization> orgList = organizationMapper.toModelList(result.getOrganizationDTOList());
                callback.onSuccess(orgList);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void fetchOrganizationImage(String fileName, DataCallback<Bitmap> callback) {
        apiDataSource.fetchImage(fileName , new DataCallback<Bitmap>(){
            @Override
            public void onSuccess(Bitmap result) {
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

