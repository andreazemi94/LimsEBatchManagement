package com.example.limsebatchmanagement.DatabaseFirebase.Download.Database;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatchObject;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;

import java.util.*;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadBatchObject extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadBatchObject.class.getSimpleName();
    private DownloadBatchObject(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                                String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadBatchObject generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlBatchObject().selectAllBatchObjects());
        return new DownloadBatchObject(db,localValues,PROCESS_NAME,lab,new EntityBatchObject(),pd);
    }
}
