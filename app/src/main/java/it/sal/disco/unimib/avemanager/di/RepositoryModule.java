package it.sal.disco.unimib.avemanager.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;
import it.sal.disco.unimib.avemanager.data.repository.AuthRepository;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(FirebaseDataSource firebaseDataSource, ApiDataSource apiDataSource) {
        return new AuthRepository(firebaseDataSource, apiDataSource);
    }

    // Se hai altri repository, forniscili qui
}