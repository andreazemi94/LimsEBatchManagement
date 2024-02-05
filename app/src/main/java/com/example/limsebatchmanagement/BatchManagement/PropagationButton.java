package com.example.limsebatchmanagement.BatchManagement;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_TOTALFIBRES_OM_A;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PropagationButton {
    public static View.OnClickListener clickOnPropagationButton(EntityAnalysisComponent analysisComponent, List<EntityBatchObject> batchObjects, DatabaseBuilder db, BatchManagement batchManagement,
                                                                Context context, String prepDate,EntityBatch batch){
        return view ->{
            switch (analysisComponent.getEnComponentName()){
                case EntityResult.COMPONENT_ANALYSIS_DATE:
                case EntityResult.COMPONENT_PREP_DATE:
                    managementAnalysisPrepDate(new ArrayList<>(batchObjects),analysisComponent,db,context,prepDate,batchManagement);
                    break;
                case EntityResult.COMPONENT_GRV_MEM:
                    managementGrvMem(new ArrayList<>(batchObjects),analysisComponent,db);
                    break;
                default:
                    managementDefault(new ArrayList<>(batchObjects),analysisComponent,db);
                    break;
            }
            batch.calculateBatchLocStatus(db);
            batchManagement.onStart();
        };
    }
    private static void managementAnalysisPrepDate(List<EntityBatchObject> batchObjects, EntityAnalysisComponent ac, DatabaseBuilder db, Context context,
                                                   String prepDate, BatchManagement batchManagement){
        Dialog dialogDateTimePicker = DialogCustom.createDateTimePicker.apply(context,prepDate);
        dialogDateTimePicker.show();
        Button buttonSetDateTime = dialogDateTimePicker.findViewById(R.id.button_setdatetime);
        DatePicker datePicker = dialogDateTimePicker.findViewById(R.id.date_picker);
        TimePicker timePicker = dialogDateTimePicker.findViewById(R.id.time_picker);
        buttonSetDateTime.setOnClickListener(view -> {
            String giorno = String.valueOf((datePicker.getDayOfMonth()>9)? datePicker.getDayOfMonth():"0"+datePicker.getDayOfMonth());
            String mese = String.valueOf((datePicker.getMonth()+1>9)? datePicker.getMonth()+1:"0"+(datePicker.getMonth()+1));
            String anno = String.valueOf((datePicker.getYear()>9)? datePicker.getYear():"0"+datePicker.getYear());
            String ora = String.valueOf((timePicker.getCurrentHour()>9)? timePicker.getCurrentHour():"0"+timePicker.getCurrentHour());
            String minuti = String.valueOf((timePicker.getCurrentMinute()>9)? timePicker.getCurrentMinute():"0"+timePicker.getCurrentMinute());
            String dataAnalisi= giorno+"/"+mese+"/"+anno+" "+ ora+":"+minuti;
            List<EntityResult> tempResult = db.SqlResult().selectFirstSingleResult(batchObjects.get(0).getBatch(),ac.getAnalysis(),ac.getEnComponentName());
            EntityResult firstVisibleResult = tempResult.get(0);
            firstVisibleResult.setEntry(dataAnalisi);
            batchObjects.forEach(batchObject->{
                EntityResult resToUp = db.SqlResult().selectSingleResult(batchObject.getObjectId(), ac.getAnalysis(), ac.getEnComponentName());
                if(Objects.nonNull(resToUp)){
                    resToUp.setEntry(firstVisibleResult.getEntry());
                    resToUp.update(resToUp,db);
                }
            });
            dialogDateTimePicker.dismiss();
            batchManagement.onStart();
        });
    }
    private static void managementGrvMem(List<EntityBatchObject> batchObjects, EntityAnalysisComponent ac, DatabaseBuilder db){
        AtomicInteger idMem = new AtomicInteger(1);
        batchObjects.forEach(batchObject->{
            EntityResult tempResult = db.SqlResult().selectSingleResult(batchObject.getObjectId(), ac.getAnalysis(), ac.getEnComponentName());
            tempResult.setEntry(batchObject.getSampleType());
            if(batchObject.getSampleType().equals("SAMPLE"))
                tempResult.setEntry(String.valueOf(idMem.getAndIncrement()));
            tempResult.update(tempResult,db);
        });
    }
    private static void managementDefault(List<EntityBatchObject> batchObjects, EntityAnalysisComponent ac, DatabaseBuilder db){
        List<EntityResult> tempResult = db.SqlResult().selectFirstSingleResult(batchObjects.get(0).getBatch(),ac.getAnalysis(),ac.getEnComponentName());
        EntityResult firstVisibleResult = tempResult.get(0);
        batchObjects.forEach(batchObject->{
            EntityResult resToUp = db.SqlResult().selectSingleResult(batchObject.getObjectId(), ac.getAnalysis(), ac.getEnComponentName());
            if(Objects.nonNull(resToUp)){
                resToUp.setEntry(firstVisibleResult.getEntry());
                resToUp.update(resToUp,db);
                G_TOTALFIBRES_OM_A.calcMultiFilterAnalizedArea(batchObject.getBatch(),resToUp,db);
                G_TOTALFIBRES_OM_A.calcMultiLFI(batchObject.getBatch(),resToUp,db);
                G_TOTALFIBRES_OM_A.calcMultiLFS(batchObject.getBatch(),resToUp,db);
                G_TOTALFIBRES_OM_A.calcMultiFiltTotArea(batchObject.getBatch(),resToUp,db);
            }
        });
    }
}
