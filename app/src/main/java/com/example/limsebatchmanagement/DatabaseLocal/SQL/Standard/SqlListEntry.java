package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityListEntry;
import java.util.List;

@Dao
public interface SqlListEntry {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityListEntry")
    List<EntityListEntry> selectAllListEntries();

    @Query("SELECT DISTINCT NAME FROM EntityListEntry WHERE LIST = :list ORDER BY ORDER_NUMBER")
    List<String> selectEntryFromList(String list);

    @Query("SELECT DISTINCT NAME FROM EntityListEntry WHERE LIST IN" +
            "(SELECT LIST_KEY FROM EntityResult WHERE TEST_NUMBER IN (SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch)) ORDER BY ORDER_NUMBER")
    List<String> selectEntryFromBatch(String batch);

    @Query("SELECT DISTINCT IT_VALUE FROM EntityListEntry WHERE NAME = :name")
    String selectValueFromName(String name);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityListEntry entityListEntry);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityListEntry entityListEntry);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityListEntry entityListEntry);

}
