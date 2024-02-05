package com.example.limsebatchmanagement.DatabaseFirebase.Download.Configurazione;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.DownloadProcess;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityListEntryMoe;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadListEntryMoe extends DownloadProcess {
    private static final String PROCESS_NAME = DownloadListEntryMoe.class.getSimpleName();
    private DownloadListEntryMoe(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                                 String processName, String lab, EntityGeneric entity, ProgressDialog pd) {
        super(db,localValues,processName,lab,entity,pd);
    }
    public static DownloadListEntryMoe generate (DatabaseBuilder db, String lab, ProgressDialog pd){
        Supplier<List<EntityGeneric>> localValues = ()->new ArrayList<>(db.SqlListEntryMoe().selectAllListEntriesMoe());
        return new DownloadListEntryMoe(db,localValues,PROCESS_NAME,lab,new EntityListEntryMoe(),pd);
    }
}