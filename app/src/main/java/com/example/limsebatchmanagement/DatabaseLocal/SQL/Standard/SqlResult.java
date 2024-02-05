package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import java.util.List;

@Dao
public interface SqlResult {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityResult")
    List<EntityResult> selectAllResults();

    @Query("SELECT * FROM EntityResult WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch ORDER BY ORDER_NUMBER)")
    List<EntityResult> selectResultsFromBatch(String batch);

    @Query("SELECT DISTINCT ANALYSIS,EN_COMPONENT_NAME,RESULT_TYPE FROM EntityResult WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch) " +
            "ORDER BY TEST_NUMBER,ORDER_DISPLAY")
    List<EntityAnalysisComponent> selectAnalysisNameDistinctFromBatch(String batch);

    @Query("SELECT COUNT(RESULT_TYPE) FROM EntityResult WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch) AND RESULT_TYPE IN ('L','Y')")
    Integer countResultTypeSpinner(String batch);

    @Query("SELECT * FROM EntityResult WHERE TEST_NUMBER = :testNumber AND ANALYSIS = :analysis AND EN_COMPONENT_NAME = :enComponent")
    EntityResult selectSingleResult(int testNumber,String analysis,String enComponent);

    @Query("SELECT EntityResult.* FROM EntityResult INNER JOIN EntityBatchObject ON EntityBatchObject.OBJECT_ID = EntityResult.TEST_NUMBER WHERE BATCH = :batch AND ANALYSIS = :analysis AND EN_COMPONENT_NAME = :enComponent " +
            "AND DISPLAYED = 'T' ORDER BY EntityBatchObject.ORDER_NUMBER")
    List<EntityResult> selectFirstSingleResult(String batch,String analysis,String enComponent);

    @Query("SELECT * FROM EntityResult WHERE TEST_NUMBER = :testNumber AND NAME = 'PHOSPHORUS-R'")
    EntityResult selectResultPhosphorusSoluble(int testNumber);

    @Query("SELECT * FROM EntityResult WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch AND SAMPLE_TYPE = 'MB') AND NAME = 'PHOSPHORUS-TEMP'")
    EntityResult selectTempResultPhosphorusSolubleMB(String batch);

    @Query("SELECT * FROM EntityResult WHERE SAMPLE_NUMBER = :sampleNumber AND NAME = :name")
    EntityResult selectResultFromSampleAndName(int sampleNumber,String name);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityResult entityResult);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityResult entityResult);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityResult entityResult);

}
