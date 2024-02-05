package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.room.*;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityDateTimeDownloadCompleted {
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo(name = "LAST_DATE_TIME_DOWNLOAD_COMPLETED")
    private String lastDateTimeDownloadCompleted;

    public static EntityDateTimeDownloadCompleted getDateTime(String dateTime){
        EntityDateTimeDownloadCompleted dt = new EntityDateTimeDownloadCompleted();
        dt.lastDateTimeDownloadCompleted = dateTime;
        return dt;
    }

    public String getLastDateTimeDownloadCompleted() {return lastDateTimeDownloadCompleted;}
    public void setLastDateTimeDownloadCompleted(String lastDateTimeDownloadCompleted) {this.lastDateTimeDownloadCompleted = lastDateTimeDownloadCompleted;}
}
