package com.example.limsebatchmanagement.BackupDatabase;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DriveManagement.FileDrive;
import com.example.limsebatchmanagement.Enum.Laboratorio;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.services.drive.Drive;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BackupManagement {
    private static ScheduledExecutorService schedBakcup;
    public static void startExecutors(Drive drive, GoogleSignInAccount account, DatabaseBuilder db, Context context) {
        Runnable r = ()->{
            try{
                List<File> filesToUpload = new ArrayList<>();
                filesToUpload.add(CsvBackup.createSingleBackup("EntityBatch",db,context));
                filesToUpload.add(CsvBackup.createSingleBackup("EntityBatchObject",db,context));
                filesToUpload.add(CsvBackup.createSingleBackup("EntitySample",db,context));
                filesToUpload.add(CsvBackup.createSingleBackup("EntityTest",db,context));
                filesToUpload.add(CsvBackup.createSingleBackup("EntityResult",db,context));
                filesToUpload.add(CsvBackup.createSingleBackup("EntityListEntry",db,context));
                if(Laboratorio.getLab(account.getEmail()).equals(Laboratorio.MOE)){
                    filesToUpload.add(CsvBackup.createSingleBackup("EntitySampleMoe",db,context));
                    filesToUpload.add(CsvBackup.createSingleBackup("EntityFibre",db,context));
                }
                FileDrive.uploadBackup(filesToUpload,drive,context,db);
            } catch (Exception e){
                e.printStackTrace();
            }
        };
        schedBakcup = Executors.newSingleThreadScheduledExecutor();
        schedBakcup.scheduleWithFixedDelay(r,2,5, TimeUnit.MINUTES);
    }
    public static void shutDownBackup(){
        schedBakcup.shutdown();
    }
}
