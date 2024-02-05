package com.example.limsebatchmanagement.DatabaseFirebase.Download.Database;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityFibre;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadFibre extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadFibre.class.getSimpleName();
    private DownloadFibre(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                          String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadFibre generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlFibre().selectAllFibers());
        return new DownloadFibre(db,localValues,PROCESS_NAME,lab,new EntityFibre(),pd);
    }
}