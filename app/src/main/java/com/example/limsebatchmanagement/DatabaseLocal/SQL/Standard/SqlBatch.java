package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatch;
import java.util.List;

@Dao
public interface SqlBatch {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityBatch")
    List<EntityBatch> selectAllBatches();


    @Query("SELECT * FROM EntityBatch WHERE NAME = :name")
    EntityBatch selectSingleBatch(String name);

    @Query("SELECT * FROM EntityBatch WHERE STATUS = '3' AND EXPORT_FILE = '1'")
    List<EntityBatch> selectCompleteBatch();

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityBatch entityBatches);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update
    int update(EntityBatch entityBatches);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityBatch entityBatches);
}
