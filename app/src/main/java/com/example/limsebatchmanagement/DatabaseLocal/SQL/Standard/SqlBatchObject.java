package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatchObject;
import java.util.List;

@Dao
public interface SqlBatchObject {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityBatchObject")
    List<EntityBatchObject> selectAllBatchObjects();

    @Query("SELECT * FROM EntityBatchObject WHERE BATCH =:batch ORDER BY ORDER_NUMBER")
    List<EntityBatchObject> selectBatchObjectsFromBatch(String batch);

    @Query("SELECT * FROM EntityBatchObject WHERE BATCH = :batchName " +
            "AND SAMPLE_NUMBER = :sampleNumber AND OBJECT_ID = :objectId")
    EntityBatchObject selectSingleBatchObject(String batchName,int sampleNumber,int objectId);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityBatchObject entityBatchObjects);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update
    int update(EntityBatchObject entityBatchObjects);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityBatchObject entityBatchObjects);

}
