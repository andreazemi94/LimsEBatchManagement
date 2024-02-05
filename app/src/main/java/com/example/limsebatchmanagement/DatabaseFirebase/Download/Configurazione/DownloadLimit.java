package com.example.limsebatchmanagement.DatabaseFirebase.Download.Configurazione;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLimit;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;

import java.util.*;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadLimit extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadLimit.class.getSimpleName();
    private DownloadLimit(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                          String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadLimit generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlLimit().selectAllLimiti());
        return new DownloadLimit(db,localValues,PROCESS_NAME,lab,new EntityLimit(),pd);
    }
}