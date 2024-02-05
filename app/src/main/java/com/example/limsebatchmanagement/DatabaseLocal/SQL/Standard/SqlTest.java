package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityTest;
import java.util.List;

@Dao
public interface SqlTest {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityTest")
    List<EntityTest> selectAllTests();

    @Query("SELECT * FROM EntityTest WHERE TEST_NUMBER IN(SELECT OBJECT_ID FROM EntityBatchObject WHERE BATCH = :batch ORDER BY ORDER_NUMBER)")
    List<EntityTest> selectTestsFromBatch(String batch);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityTest entityTest);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityTest entityTest);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityTest entityTest);

}
