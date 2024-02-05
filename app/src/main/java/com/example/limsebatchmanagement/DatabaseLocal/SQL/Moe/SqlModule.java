package com.example.limsebatchmanagement.DatabaseLocal.SQL.Moe;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityModule;
import java.util.*;

@Dao
public interface SqlModule {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityModule")
    List<EntityModule> selectAllModules();

    @Query("SELECT * FROM EntityModule WHERE ANALYSIS IN (:analysis)")
    List<EntityModule> selectModulesFromAnalysis(Set<String> analysis);

    @Query("SELECT * FROM EntityModule WHERE ANALYSIS IN (:analysis)")
    List<EntityModule> selectModulesFromSingleAnalysis (String analysis);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityModule entityModule);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityModule entityModule);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityModule entityModule);

}
