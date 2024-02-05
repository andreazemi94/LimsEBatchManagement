package com.example.limsebatchmanagement.DatabaseLocal.SQL.Moe;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntitySampleMoe;
import java.util.List;

@Dao
public interface SqlSampleMoe {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntitySampleMoe")
    List<EntitySampleMoe> selectAllSamplesMoe();

    @Query("SELECT EntitySampleMoe.* FROM EntitySampleMoe INNER JOIN EntityBatchObject ON EntityBatchObject.SAMPLE_NUMBER=EntitySampleMoe.SAMPLE_NUMBER " +
            "WHERE EntityBatchObject.BATCH = :batch ORDER BY EntityBatchObject.ORDER_NUMBER")
    List<EntitySampleMoe> selectSamplesMoeFromBatch(String batch);

    @Query("SELECT * FROM EntitySampleMoe WHERE SAMPLE_NUMBER = :sampleNumber")
    EntitySampleMoe selectSingleSampleMoe(int sampleNumber);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntitySampleMoe entitySampleMoe);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntitySampleMoe entitySampleMoe);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntitySampleMoe entitySampleMoe);

}
