package com.example.limsebatchmanagement.DatabaseFirebase.Download.Database;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityTest;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;

import java.util.*;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadTest extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadTest.class.getSimpleName();
    private DownloadTest(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                         String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadTest generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlTest().selectAllTests());
        return new DownloadTest(db,localValues,PROCESS_NAME,lab,new EntityTest(),pd);
    }
}