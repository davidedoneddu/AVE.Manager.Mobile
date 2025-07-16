package it.sal.disco.unimib.avemanager.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import it.sal.disco.unimib.avemanager.data.datasource.local.db.AppDatabase;
import it.sal.disco.unimib.avemanager.data.datasource.local.db.InvitatoDao;
import it.sal.disco.unimib.avemanager.util.NetworkUtil;

@Module
@InstallIn(SingletonComponent.class)  // scope singleton per tutta l'app
public class RoomModule {

    @Provides
    @Singleton
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "ave_room_database")
                .fallbackToDestructiveMigration(true)
                .build();
    }

    @Provides
    public static InvitatoDao provideInvitatoDao(AppDatabase db) {
        return db.invitatoDao();
    }

    @Provides
    @Singleton
    public NetworkUtil provideNetworkUtil(@ApplicationContext Context context) {
        return new NetworkUtil(context);
    }
}
