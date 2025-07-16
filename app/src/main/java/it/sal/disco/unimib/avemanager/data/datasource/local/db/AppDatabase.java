package it.sal.disco.unimib.avemanager.data.datasource.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import it.sal.disco.unimib.avemanager.data.datasource.local.entity.InvitatoEntity;

@Database(entities = {InvitatoEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract InvitatoDao invitatoDao();
}
