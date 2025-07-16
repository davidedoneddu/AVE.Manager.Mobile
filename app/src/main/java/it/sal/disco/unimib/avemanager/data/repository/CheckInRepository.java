package it.sal.disco.unimib.avemanager.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.ui.model.CheckInResult;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@Singleton
public class CheckInRepository {

    private final ApiDataSource apiDataSource;

    @Inject
    public CheckInRepository(ApiDataSource apiDataSource) {
        this.apiDataSource = apiDataSource;
    }

    public void checkInWithQr(String qrCode, DataCallback<CheckInResult> callback) {

        apiDataSource.doCheckIn(qrCode, new DataCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result.isEmpty()){
                    callback.onFailure(new Exception("No response from server"));
                    return;
                }
                if(result.endsWith("-")){
                    result = result.concat("Il check-in è stato completato con successo");
                }
                String[] res = result.split("-",2);
                if(res.length < 2){
                    callback.onSuccess(new CheckInResult(" - ","Il check-in è stato completato con successo"));
                    return;
                }
                CheckInResult checkInResult = new CheckInResult(res[0], res[1]);
                callback.onSuccess(checkInResult);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
