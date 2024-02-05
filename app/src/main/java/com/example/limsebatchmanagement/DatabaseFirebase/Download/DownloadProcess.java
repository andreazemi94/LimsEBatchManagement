package com.example.limsebatchmanagement.DatabaseFirebase.Download;

import android.app.ProgressDialog;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.FirebaseManagement.FirebaseDatabaseMng;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class DownloadProcess implements Runnable {
    protected DatabaseBuilder db;
    private Supplier<List<EntityGeneric>> localValues;
    private List<EntityGeneric> local;
    private String processName;
    private String lab;
    private EntityGeneric entity;
    private ProgressDialog pd;
    protected DownloadProcess(DatabaseBuilder db, Supplier<List<EntityGeneric>> localValues,
                              String processName, String lab, EntityGeneric entity, ProgressDialog pd){
        this.db=db;
        this.localValues=localValues;
        this.processName=processName;
        this.lab=lab;
        this.entity=entity;
        this.pd=pd;
    }
    @Override
    public void run() {
        try{
            db.SqlLogDownloadSched().deleteLogFromProcess(processName);
            local = localValues.get();
            download(lab,entity,local);
            SchedulerDownload.getEsitiSched().computeIfPresent(this,(k,v)->SchedulerDownload.COMPLETE);
        } catch ( Exception e){
            SchedulerDownload.getEsitiSched().computeIfPresent(this,(k,v)->SchedulerDownload.FAILED);
            EntityLogDownloadSched log = new EntityLogDownloadSched();
            log.setProcess(processName);
            log.setError(e.getMessage());
            db.SqlLogDownloadSched().insert(log);
        }
        SchedulerDownload.updateCompleteDownload(db);
        try {
            pd.setProgress(SchedulerDownload.barrierDownload.getNumberWaiting()+1);
            SchedulerDownload.barrierDownload.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void download(String lab, EntityGeneric entityGeneric, List<EntityGeneric> locEntityGeneric) throws Exception{
        List<Map<String,String>> fireBaseDownloaded = new FirebaseDatabaseMng(lab,entityGeneric.getNameEntity()).getDocumentsDb();
        if(!fireBaseDownloaded.isEmpty()){
            List<EntityGeneric> firebase = new ArrayList<>();
            fireBaseDownloaded.forEach(document->firebase.add(entityGeneric.create(document)));
            List<EntityGeneric> local = new ArrayList<>(locEntityGeneric);
            firebase.forEach(fireBaseEnt->{
                Optional<EntityGeneric> localEntity = local.stream().parallel().filter(localEnt->localEnt.equals(fireBaseEnt)).findAny();
                if(localEntity.equals(Optional.empty()))
                    fireBaseEnt.insert(db);
                else
                    localEntity.get().update(fireBaseEnt,db);
            });
            local.forEach(localEnt->{
                if(firebase.stream().parallel().noneMatch(driveEnt->driveEnt.equals(localEnt)))
                    localEnt.delete(db);
            });
        }
    }
}
