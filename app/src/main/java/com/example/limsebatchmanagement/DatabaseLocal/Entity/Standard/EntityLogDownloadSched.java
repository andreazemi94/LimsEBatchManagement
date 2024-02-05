package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityLogDownloadSched {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo(name = "PROCESS")
    private String process;
    @ColumnInfo(name = "ERROR")
    private String error;

    public static EntityLogDownloadSched loadLogError (String process, String error){
        EntityLogDownloadSched log = new EntityLogDownloadSched();
        log.process=process;
        log.error=error;
        return log;
    }

    public String getProcess() {return process;}
    public String getError() {return error;}
    public void setProcess(String process) {this.process = process;}
    public void setError(String error) {this.error = error;}
}
