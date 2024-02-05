package com.example.limsebatchmanagement.DatabaseFirebase.Download;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.Configurazione.*;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.Database.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityDateTimeDownloadCompleted;
import com.example.limsebatchmanagement.Enum.Laboratorio;
import com.example.limsebatchmanagement.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SchedulerDownload {
    private static final Map<DownloadProcess,Integer> esitiSched = new HashMap<>();
    private static  ExecutorService executor;
    public static CyclicBarrier barrierDownload;
    public static final int STARTED = 1, COMPLETE = 2, FAILED = 3;
    private static Handler handler = new Handler();
    public static void startExecutors(GoogleSignInAccount account, DatabaseBuilder db, ProgressDialog pd, MainActivity main) {
        List<DownloadProcess> tasks = loadTaskToSchedule( account, db, pd);
        pd.setMax(tasks.size());
        pd.show();
        barrierDownload = new CyclicBarrier(tasks.size(),()->managementBarrierBroken(db,pd,main,handler));
        executor = Executors.newCachedThreadPool();
        esitiSched.clear();
        tasks.forEach(task->{
            esitiSched.compute(task,(k,v)-> (v!=null)? v:STARTED);
            executor.execute(task);
        });
        executor.shutdown();
    }
    public static Map<DownloadProcess,Integer> getEsitiSched() {
        return esitiSched;
    }
    private static List<DownloadProcess> loadTaskToSchedule (GoogleSignInAccount account, DatabaseBuilder db,
                                                             ProgressDialog pd){
        List<DownloadProcess> tasks = new ArrayList<>();
        String lab = Laboratorio.getLab(account.getEmail()).name();
        if(Laboratorio.getLab(account.getEmail()).equals(Laboratorio.MOE)){
            tasks.add(DownloadSampleMoe.generate(db,lab,pd));
            tasks.add(DownloadModule.generate(db,lab,pd));
            tasks.add(DownloadListEntryMoe.generate(db,lab,pd));
        }
        tasks.add(DownloadBatch.generate(db,lab,pd));
        tasks.add(DownloadBatchObject.generate(db,lab,pd));
        tasks.add(DownloadSample.generate(db,lab,pd));
        tasks.add(DownloadTest.generate(db,lab,pd));
        tasks.add(DownloadResult.generate(db,lab,pd));
        tasks.add(DownloadListEntry.generate(db,lab,pd));
        tasks.add(DownloadCustomer.generate(db,lab,pd));
        tasks.add(DownloadLimit.generate(db,lab,pd));
        return tasks;
    }
    public static void updateCompleteDownload (DatabaseBuilder db){
        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        EntityDateTimeDownloadCompleted entityDateTimeDownloadCompleted = db.SqlDateTimeDownloadCompleted().select();
        entityDateTimeDownloadCompleted.setLastDateTimeDownloadCompleted(LocalDateTime.now().format(f));
        db.SqlDateTimeDownloadCompleted().update(entityDateTimeDownloadCompleted);
    }
    private static void managementBarrierBroken(DatabaseBuilder db, ProgressDialog pd,MainActivity main,Handler handler){
        barrierDownload.reset();
        pd.dismiss();
        if(esitiSched.values().size()==esitiSched.values().stream().filter(i->i==COMPLETE).count())
            allTaskCompleted(db,main,handler);
    }
    private static void allTaskCompleted(DatabaseBuilder db,MainActivity main,Handler handler){
        updateCompleteDownload(db);
        handler.post(main::onStart);
    }
}
