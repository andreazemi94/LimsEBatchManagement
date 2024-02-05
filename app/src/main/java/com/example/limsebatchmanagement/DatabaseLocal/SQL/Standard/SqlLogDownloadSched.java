package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLogDownloadSched;
import java.util.List;

@Dao
public interface SqlLogDownloadSched {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityLogDownloadSched")
    List<EntityLogDownloadSched> selectAllLogs();

    @Insert
    void insert(EntityLogDownloadSched... entityLogDownloadScheds);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    void delete(EntityLogDownloadSched entityLogDownloadSched);

    @Query("DELETE FROM EntityLogDownloadSched WHERE PROCESS = :process")
    void deleteLogFromProcess(String process);

}
