package com.example.limsebatchmanagement.DatabaseFirebase.Upload;

import android.app.*;
import android.content.Context;
import android.os.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.BatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntitySampleMoe;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.Enum.Laboratorio;
import com.example.limsebatchmanagement.FirebaseManagement.FirebaseDatabaseMng;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UploadProcess {
    public static Runnable uploadResultAndInfoBatch(Context context, GoogleSignInAccount account,DatabaseBuilder db,
                                                    EntityBatch batch, List<EntityResult> results, Dialog pdSave, BatchManagement batchManagement){
        Handler handler = new Handler();
        return ()->{
            try {
                Laboratorio lab = Laboratorio.getLab(account.getEmail());
                batch.calculateBatchStatus(results);
                if(!lab.equals(Laboratorio.MOE)){
                    results.stream().map(EntityResult::createDocumentFb).forEach(doc->new FirebaseDatabaseMng(lab.name(),"Result").updateDocument(doc));
                    new FirebaseDatabaseMng(lab.name(),"Batch").updateDocument(batch.createDocumentFb());
                } else {
                    List<EntitySampleMoe> samplesMoe = db.SqlSampleMoe().selectSamplesMoeFromBatch(batch.getBatchName());
                    samplesMoe.stream().map(EntitySampleMoe::createDocumentFb).forEach(doc->new FirebaseDatabaseMng(lab.name(),"SampelMoe").updateDocument(doc));
                    results.stream().map(EntityResult::createDocumentFb).forEach(doc->new FirebaseDatabaseMng(lab.name(),"Result").updateDocument(doc));
                    new FirebaseDatabaseMng(lab.name(),"Batch").updateDocument(batch.createDocumentFb());
                }
                batch.update(batch,db);
                handler.post(() -> {
                    pdSave.dismiss();
                    batchManagement.onStart();
                });
            } catch (Exception e) {
                handler.post(()->{
                    pdSave.dismiss();
                    AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context,e.getMessage());
                    dialogError.show();
                });
            }
        };
    }
}
