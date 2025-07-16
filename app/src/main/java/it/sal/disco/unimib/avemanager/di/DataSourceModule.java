package it.sal.disco.unimib.avemanager.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiDataSource;
import it.sal.disco.unimib.avemanager.data.datasource.api.ApiService;
import it.sal.disco.unimib.avemanager.data.datasource.api.TokenProvider;
import it.sal.disco.unimib.avemanager.data.datasource.firebase.FirebaseDataSource;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourceModule {

    @Provides
    @Singleton
    public static FirebaseDataSource provideFirebaseDataSource() {
        return new FirebaseDataSource(); // oppure passaci FirebaseAuth.getInstance(), ecc.
    }

    @Provides
    @Singleton
    public static ApiDataSource provideApiDataSource(ApiService apiService, TokenProvider tokenProvider) {
        return new ApiDataSource(apiService, tokenProvider); // idem
    }
}
