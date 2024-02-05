package com.example.limsebatchmanagement.DatabaseFirebase.Download.Database;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntitySample;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;

import java.util.*;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadSample extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadSample.class.getSimpleName();
    private DownloadSample(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                           String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadSample generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlSample().selectAllSamples());
        return new DownloadSample(db,localValues,PROCESS_NAME,lab,new EntitySample(),pd);
    }
}