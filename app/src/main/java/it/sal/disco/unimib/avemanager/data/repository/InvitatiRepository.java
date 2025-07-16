package it.sal.disco.unimib.avemanager.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.local.db.InvitatoDao;
import it.sal.disco.unimib.avemanager.data.datasource.local.entity.InvitatoEntity;
import it.sal.disco.unimib.avemanager.data.mapper.InvitatoEntityModelMapper;
import it.sal.disco.unimib.avemanager.data.mapper.InvitatoMapper;
import it.sal.disco.unimib.avemanager.data.model.response.BaseResponseDTO;
import it.sal.disco.unimib.avemanager.data.model.response.InvitatiListResponseDTO;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;
import it.sal.disco.unimib.avemanager.util.DataCallback;
import it.sal.disco.unimib.avemanager.util.NetworkUtil;

@Singleton
public class InvitatiRepository {

    private final ApiDataSource apiDataSource;
    private final InvitatoDao invitatoDao;
    private final NetworkUtil networkUtil;
    private final MutableLiveData<Boolean> dataSynced = new MutableLiveData<>();



    @Inject
    public InvitatiRepository(ApiDataSource apiDataSource,InvitatoDao invitatoDao,NetworkUtil networkUtil)
    {
        this.invitatoDao = invitatoDao;
        this.apiDataSource = apiDataSource;
        this.networkUtil = networkUtil;
    }

    public void getInvitati(String currentQuery, int currentPage, int pageSize, DataCallback<List<Invitato>> callback) {
        if (networkUtil.isNetworkAvailable()) {
            // Primo: fetch da rete
            apiDataSource.fetchInvitatiList(new DataCallback<InvitatiListResponseDTO>() {
                @Override
                public void onSuccess(InvitatiListResponseDTO result) {
                    // Esegui su thread in background (non sul main thread!)
                    new Thread(() -> {
                        try {
                            InvitatoMapper invitatoMapper = new InvitatoMapper();
                            List<Invitato> remoteData = invitatoMapper.toModelList(result.getInvitati());
                            List<InvitatoEntity> entities = InvitatoEntityModelMapper.toEntityList(remoteData);

                            invitatoDao.deleteAll();
                            invitatoDao.insertAll(entities);

                            // Dopo il salvataggio, leggi da Room
                            String query = (currentQuery == null) ? "" : currentQuery.trim().toLowerCase();
                            int offset = currentPage * pageSize;
                            List<InvitatoEntity> entityPage = invitatoDao.getInvitatiPaged(query, pageSize, offset);
                            List<Invitato> pageData = InvitatoEntityModelMapper.toModelList(entityPage);

                            callback.onSuccess(pageData);
                        } catch (Exception e) {

                            callback.onFailure(e);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Throwable t) {
                    callback.onFailure(t);
                }
            });
        } else {
            // Se offline: leggi da Room direttamente
            new Thread(() -> {
                try {
                    String query = (currentQuery == null) ? "" : currentQuery.trim().toLowerCase();
                    int offset = currentPage * pageSize;
                    List<InvitatoEntity> entityPage = invitatoDao.getInvitatiPaged(query, pageSize, offset);
                    List<Invitato> pageData = InvitatoEntityModelMapper.toModelList(entityPage);

                    callback.onSuccess(pageData);
                } catch (Exception e) {

                    callback.onFailure(e);
                }
            }).start();
        }
    }


    public void updateInvitato(Invitato invitato, DataCallback<Boolean> dataCallback) {
        if (networkUtil.isNetworkAvailable()) {
            apiDataSource.updateInvitato(invitato, new DataCallback<BaseResponseDTO>() {
                @Override
                public void onSuccess(BaseResponseDTO result) {
                    new Thread(() -> {
                        try {
                            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
                            invitatoDao.update(entity);
                            dataCallback.onSuccess(true);
                        } catch (Exception e) {

                            dataCallback.onFailure(e);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Throwable t) {
                    dataCallback.onFailure(t);
                }
            });
        } else {
            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
            entity.needsSync = true;
            entity.syncAction = "UPDATE";
            invitatoDao.insert(entity);
            dataCallback.onSuccess(true);
        }
    }

    public void deleteInvitato(Invitato invitato, DataCallback<Boolean> dataCallback) {
        if (networkUtil.isNetworkAvailable()) {
            apiDataSource.deleteInvitato(invitato, new DataCallback<BaseResponseDTO>() {
                @Override
                public void onSuccess(BaseResponseDTO result) {
                    new Thread(() -> {
                        try {
                            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
                            invitatoDao.delete(entity);
                            dataCallback.onSuccess(true);
                        } catch (Exception e) {

                            dataCallback.onFailure(e);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Throwable t) {
                    dataCallback.onFailure(t);
                }
            });
        } else {
            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
            entity.needsSync = true;
            entity.syncAction = "DELETE";
            invitatoDao.insert(entity);
            dataCallback.onSuccess(true);
        }
    }

    public void insertInvitato(Invitato invitato, DataCallback<Boolean> dataCallback) {
        if (networkUtil.isNetworkAvailable()) {
            apiDataSource.insertInvitato(invitato, new DataCallback<BaseResponseDTO>() {
                @Override
                public void onSuccess(BaseResponseDTO result) {
                    new Thread(() -> {
                        try {
                            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
                            invitatoDao.insert(entity);
                            dataCallback.onSuccess(true);
                        } catch (Exception e) {

                            dataCallback.onFailure(e);
                        }
                    }).start();
                }

                @Override
                public void onFailure(Throwable t) {
                    dataCallback.onFailure(t);
                }
            });
        } else {
            InvitatoEntity entity = InvitatoEntityModelMapper.toEntity(invitato);
            entity.needsSync = true;
            entity.syncAction = "INSERT";
            invitatoDao.insert(entity);
            dataCallback.onSuccess(true);
        }
    }

    public void getInvitatoById(int id, DataCallback<Invitato> dataCallback) {
        new Thread(() -> {
            try {
                InvitatoEntity entity = invitatoDao.getById(id);
                if (entity != null) {
                    Invitato model = InvitatoEntityModelMapper.toModel(entity);
                    dataCallback.onSuccess(model);
                } else {
                    dataCallback.onFailure(new Exception("Invitato non trovato"));
                }
            } catch (Exception e) {

                dataCallback.onFailure(e);
            }
        }).start();
    }

    public void syncPendingChanges() {
        new Thread(() -> {
            List<InvitatoEntity> dirtyItems = invitatoDao.getAllToSync();
            if (dirtyItems.isEmpty()) {
                dataSynced.postValue(true); // niente da sincronizzare
                return;
            }

            final AtomicInteger remaining = new AtomicInteger(dirtyItems.size());

            for (InvitatoEntity entity : dirtyItems) {
                Invitato model = InvitatoEntityModelMapper.toModel(entity);
                String action = entity.syncAction;

                if (action == null) {
                    if (remaining.decrementAndGet() == 0) {
                        dataSynced.postValue(true);
                    }
                    continue;
                }

                DataCallback<BaseResponseDTO> callback = new DataCallback<BaseResponseDTO>() {
                    @Override
                    public void onSuccess(BaseResponseDTO result) {
                        if (remaining.decrementAndGet() == 0) {
                            dataSynced.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        if (remaining.decrementAndGet() == 0) {
                            dataSynced.postValue(true);
                        }
                    }
                };

                switch (action) {
                    case "INSERT":
                        apiDataSource.insertInvitato(model, callback);
                        break;
                    case "UPDATE":
                        apiDataSource.updateInvitato(model, callback);
                        break;
                    case "DELETE":
                        apiDataSource.deleteInvitato(model, callback);
                        break;
                    default:
                        if (remaining.decrementAndGet() == 0) {
                            dataSynced.postValue(true);
                        }
                        break;
                }
            }
        }).start();
    }


    public void sendEmail(Invitato invitato, DataCallback<Boolean> dataCallback) {
        apiDataSource.sendEmailInvitato(invitato, new DataCallback<BaseResponseDTO>() {
            @Override
            public void onSuccess(BaseResponseDTO result) {

                dataCallback.onSuccess(true);
            }

            @Override
            public void onFailure(Throwable t) {
                dataCallback.onFailure(t);
            }
        });
    }

    public LiveData<Boolean> getDataSynced() {
        return dataSynced;
    }

    public void resetSynced() {
        dataSynced.postValue(false);
    }
}
