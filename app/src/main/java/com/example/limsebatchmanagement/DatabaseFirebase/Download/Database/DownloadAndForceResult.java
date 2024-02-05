package com.example.limsebatchmanagement.DatabaseFirebase.Download.Database;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.BatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatchObject;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityResult;
import com.example.limsebatchmanagement.Enum.Laboratorio;
import com.example.limsebatchmanagement.FirebaseManagement.FirebaseDatabaseMng;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.Drive;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DownloadAndForceResult {
    private static AtomicInteger countForced = new AtomicInteger();
    public static void run (GoogleSignInAccount account, List<EntityResult> results, DatabaseBuilder db,
                            BatchManagement batchManagement, Context context){
        Laboratorio lab = Laboratorio.getLab(account.getEmail());
        AlertDialog.Builder confirmForce = DialogCustom.confirmDialog(context,"Attenzione","Con la seguente operazione verranno recuperati i dati del batch " +
                "salvati in Google Drive,si desidera procedere?");
        confirmForce.setPositiveButton("Conferma", (dialog, which) -> downlaodAndForceResult(dialog,lab.name(),results,db,batchManagement,context));
        confirmForce.show();
    }
    private static void downlaodAndForceResult(DialogInterface dialog, String lab, List<EntityResult> results, DatabaseBuilder db,
                                               BatchManagement batchManagement, Context context){
        dialog.dismiss();
        ProgressDialog pdUpload = DialogCustom.setOptionProgressDialog(context,"Force Result","Download and force result from Drive.");
        pdUpload.show();
        Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(()-> {
            try{
                countForced.set(0);
                results.stream()
                        .forEach(res->{
                            Map<String,String> fireBaseDownloaded = new FirebaseDatabaseMng(lab,"Result",""+res.getResultNumber()).getSingleDocument();
                            List<EntityGeneric> firebase = new ArrayList<>();
                            firebase.add(res.create(fireBaseDownloaded));
                            firebase.removeIf(resr-> ((EntityResult)resr).getEntry().equals(EntityResult.ENTRY_VUOTA));
                            if(!firebase.isEmpty()){
                                EntityResult resToUpdate = db.SqlResult().selectSingleResult(res.getTestNumber(),res.getAnalysis(),res.getEnComponentName());
                                if(Objects.nonNull(resToUpdate)){
                                    resToUpdate.setEntry(((EntityResult)firebase.get(0)).getEntry());
                                    resToUpdate.setTemporary(((EntityResult)firebase.get(0)).getTemporary());
                                    resToUpdate.update(resToUpdate,db);
                                    countForced.incrementAndGet();
                                }
                            }
                        });
                handler.post(() -> {
                    batchManagement.onStart();
                    pdUpload.dismiss();
                    DialogCustom.confirmDialog(context,"Risultati forzati","N° risultati forzati: " + countForced.get()).show();
                });
            } catch (Exception e){
                handler.post(() -> {
                    pdUpload.dismiss();
                    AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context, e.getMessage());
                    dialogError.show();
                });
            }
        });
    }
}