package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLimit;
import java.util.List;

@Dao
public interface SqlLimit {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityLimit")
    List<EntityLimit> selectAllLimiti();

    @Query("SELECT * FROM EntityLimit WHERE COMPONENT IN (SELECT NAME FROM EntityResult WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch)) " +
            "AND MATRICE IN (SELECT MATRIX FROM EntitySample WHERE SAMPLE_NUMBER IN(SELECT SAMPLE_NUMBER FROM EntityBatchObject WHERE BATCH = :batch))")
    List<EntityLimit> selectLimitsFromBatch(String batch);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityLimit entityLimit);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityLimit entityLimit);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityLimit entityLimit);

}
