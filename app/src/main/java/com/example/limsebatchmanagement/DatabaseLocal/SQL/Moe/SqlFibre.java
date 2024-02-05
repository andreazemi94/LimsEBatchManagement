package com.example.limsebatchmanagement.DatabaseLocal.SQL.Moe;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityFibre;
import java.util.List;

@Dao
public interface SqlFibre {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityFibre")
    List<EntityFibre> selectAllFibers();

    @Query("SELECT EntityFibre.* FROM EntityFibre INNER JOIN EntityBatchObject ON EntityBatchObject.SAMPLE_NUMBER=EntityFibre.SAMPLE_NUMBER " +
            "WHERE EntityBatchObject.BATCH = :batch ORDER BY EntityBatchObject.ORDER_NUMBER")
    List<EntityFibre> selectFibersFromBatch(String batch);

    @Query("SELECT * FROM EntityFibre WHERE SAMPLE_NUMBER = :sampleNumber")
    List<EntityFibre> selectFibersFromSample(int sampleNumber);

    @Query("SELECT MAX(ID_FIBRA) FROM EntityFibre WHERE SAMPLE_NUMBER = :sampleNumber")
    int selectMaxIdFiberFromSample(int sampleNumber);

    @Query("SELECT * FROM EntityFibre WHERE SAMPLE_NUMBER = :sampleNumber AND ID_FIBRA = :idFibra")
    EntityFibre selectFiberToRemove(int sampleNumber,int idFibra);

    @Query("SELECT * FROM EntityFibre WHERE SAMPLE_NUMBER = :sampleNumber AND ID_FIBRA = :idFibra")
    EntityFibre selectSingleFiber(int sampleNumber,int idFibra);
    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityFibre entityFibre);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityFibre entityFibre);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityFibre entityFibre);

}
