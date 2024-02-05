package com.example.limsebatchmanagement.DatabaseLocal.SQL.Moe;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityListEntryMoe;
import java.util.List;

@Dao
public interface SqlListEntryMoe {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityListEntryMoe")
    List<EntityListEntryMoe> selectAllListEntriesMoe();

    @Query("SELECT DESCRIPTION FROM EntityListEntryMoe WHERE MODULE = :module AND KEYS = :keys")
    List<String> selectEntriesFromModuleAndKeys(String module,String keys);

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityListEntryMoe entityListEntryMoe);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(EntityListEntryMoe entityListEntryMoe);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityListEntryMoe entityListEntryMoe);

}
