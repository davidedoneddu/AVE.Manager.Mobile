package it.sal.disco.unimib.avemanager.data.datasource.local.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.sal.disco.unimib.avemanager.data.datasource.local.entity.InvitatoEntity;

@Dao
public interface InvitatoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InvitatoEntity invitato);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<InvitatoEntity> invitati);

    @Update
    void update(InvitatoEntity invitato);

    @Delete
    void delete(InvitatoEntity invitato);

    @Query("DELETE FROM invitati")
    void deleteAll();

    @Query("SELECT * FROM invitati WHERE inv_id = :id")
    InvitatoEntity getById(int id);

    @Query("SELECT * FROM invitati ORDER BY inv_cognome, inv_nome LIMIT :limit OFFSET :offset")
    List<InvitatoEntity> getPaged(int limit, int offset);

    @Query("SELECT * FROM invitati WHERE inv_cognome LIKE '%' || :search || '%' OR inv_nome LIKE '%' || :search || '%' ORDER BY inv_cognome, inv_nome")
    List<InvitatoEntity> searchByName(String search);

    @Query("SELECT * FROM invitati " +
            "WHERE (LOWER(inv_nome) LIKE '%' || :query || '%' " +
            "OR LOWER(inv_cognome) LIKE '%' || :query || '%') " +
            "ORDER BY inv_id " +
            "LIMIT :pageSize OFFSET :offset")
    List<InvitatoEntity> getInvitatiPaged(String query, int pageSize, int offset);

    @Query("SELECT * FROM invitati WHERE needs_sync = 1")
    List<InvitatoEntity> getAllToSync();

}