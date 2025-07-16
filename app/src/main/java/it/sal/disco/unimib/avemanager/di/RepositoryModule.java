package it.sal.disco.unimib.avemanager.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.local.db.InvitatoDao;
import it.sal.disco.unimib.avemanager.data.repository.AuthRepository;
import it.sal.disco.unimib.avemanager.data.repository.EventRepository;
import it.sal.disco.unimib.avemanager.data.repository.InvitatiRepository;
import it.sal.disco.unimib.avemanager.util.NetworkUtil;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(FirebaseDataSource firebaseDataSource, ApiDataSource apiDataSource) {
        return new AuthRepository(firebaseDataSource, apiDataSource);
    }

    @Provides
    @Singleton
    public EventRepository provideEventRepository( ApiDataSource apiDataSource) {
        return new EventRepository( apiDataSource);
    }
    @Provides
    @Singleton
    public InvitatiRepository provideInvitatiRepository(ApiDataSource apiDataSource, InvitatoDao dao, NetworkUtil networkUtil) {
        return new InvitatiRepository(apiDataSource, dao, networkUtil);
    }

    // Se hai altri repository, forniscili qui
}