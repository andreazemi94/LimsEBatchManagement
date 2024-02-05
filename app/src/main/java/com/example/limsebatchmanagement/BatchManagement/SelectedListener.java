package com.example.limsebatchmanagement.BatchManagement;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.Adapter.AdapterBatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import java.util.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SelectedListener implements AdapterView.OnItemSelectedListener{
    Integer indexEt;
    List<Integer> listDistinctSampleNumber;
    List<EntityAnalysisComponent> ac;
    List<EntityResult> results;
    List<EntitySample> samples;
    DatabaseBuilder db;
    Context context;
    EditText et;
    List<EntityLimit> limits;
    List<String> listEntries;
    AdapterBatchManagement.ViewHolder holder;
    EntityBatch batch;
    private SelectedListener(Integer indexEt, List<Integer> listDistinctSampleNumber, List<EntityAnalysisComponent> ac, List<EntityResult> results, List<EntitySample> samples,
                             DatabaseBuilder db, Context context, EditText et, List<EntityLimit> limits, AdapterBatchManagement.ViewHolder holder,
                             EntityBatch batch){
        this.indexEt=indexEt;
        this.listDistinctSampleNumber=listDistinctSampleNumber;
        this.ac=ac;
        this.results=results;
        this.samples=samples;
        this.db=db;
        this.context=context;
        this.et=et;
        this.limits=limits;
        this.holder=holder;
        this.batch=batch;
    }
    public static SelectedListener getListenereRes(Integer indexEt, List<Integer> listDistinctSampleNumber, List<EntityAnalysisComponent> ac, List<EntityResult> results,
                                                   List<EntitySample> samples, DatabaseBuilder db, Context context, EditText et, List<EntityLimit> limits,
                                                   AdapterBatchManagement.ViewHolder holder, EntityBatch batch){
        return new SelectedListener(indexEt,listDistinctSampleNumber,ac,results,samples,db,context,et,limits,holder,batch);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        EntityResult resToUpdate = EntityResult.getResultFromSampleAnalysisComponent(new ArrayList<>(results),listDistinctSampleNumber.get(holder.getAbsoluteAdapterPosition()),
                ac.get(indexEt).getAnalysis(),ac.get(indexEt).getEnComponentName());
        if(Objects.nonNull(resToUpdate)){
            listEntries = db.SqlListEntry().selectEntryFromList(resToUpdate.getListKey());
            resToUpdate.setEntry(listEntries.get(i));
            resToUpdate.update(resToUpdate,db);
            batch.calculateBatchLocStatus(db);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
