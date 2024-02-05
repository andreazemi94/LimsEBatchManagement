package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntitySample;
import java.util.List;

@Dao
public interface SqlSample {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntitySample")
    List<EntitySample> selectAllSamples();

    @Query("SELECT EntitySample.* FROM EntitySample INNER JOIN EntityBatchObject ON EntityBatchObject.SAMPLE_NUMBER=EntitySample.SAMPLE_NUMBER " +
            "WHERE EntityBatchObject.BATCH = :batch ORDER BY EntityBatchObject.ORDER_NUMBER")
    List<EntitySample> selectSamplesFromBatch(String batch);

    @Query("SELECT DISTINCT EntitySample.SAMPLE_NUMBER FROM EntitySample INNER JOIN EntityBatchObject ON EntityBatchObject.SAMPLE_NUMBER=EntitySample.SAMPLE_NUMBER " +
            "WHERE EntityBatchObject.BATCH = :batch ORDER BY EntityBatchObject.ORDER_NUMBER")
    List<Integer> selectDistinctSampleNumber(String batch);

    @Query("SELECT * FROM EntitySample WHERE SAMPLE_NUMBER = :sampleNumber")
    EntitySample selectSingleSample(int sampleNumber);
    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntitySample entitySample);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntitySample entitySample);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntitySample entitySample);

}
