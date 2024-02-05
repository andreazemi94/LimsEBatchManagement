package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityDateTimeDownloadCompleted;

@Dao
public interface SqlDateTimeDownloadCompleted {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityDateTimeDownloadCompleted WHERE id = 1")
    EntityDateTimeDownloadCompleted select();

    @Insert
    void insert(EntityDateTimeDownloadCompleted... EntityDateTimeDownloadCompleted);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update
    void update(EntityDateTimeDownloadCompleted EntityDateTimeDownloadCompleted);

}
