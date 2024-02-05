package com.example.limsebatchmanagement.BatchManagement;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.Adapter.AdapterBatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.Utility.GraphicManagement;
import java.util.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LongClick implements View.OnLongClickListener{
    Integer indexEt;
    List<Integer> listDistinctSampleNumber;
    List<EntityAnalysisComponent> ac;
    List<EntityResult> results;
    List<EntitySample> samples;
    DatabaseBuilder db;
    Context context;
    EditText et;
    List<EntityLimit> limits;
    AdapterBatchManagement.ViewHolder holder;
    EntityBatch batch;
    private LongClick(Integer indexEt, List<Integer> listDistinctSampleNumber, List<EntityAnalysisComponent> ac, List<EntityResult> results, List<EntitySample> samples,
                      DatabaseBuilder db, Context context, EditText et, List<EntityLimit> limits, AdapterBatchManagement.ViewHolder holder, EntityBatch batch){
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
        this.batch= batch;
    }
    public static LongClick getLongClick(Integer indexEt, List<Integer> listDistinctSampleNumber, List<EntityAnalysisComponent> ac, List<EntityResult> results,
                                         List<EntitySample> samples, DatabaseBuilder db, Context context, EditText et, List<EntityLimit> limits,
                                         AdapterBatchManagement.ViewHolder holder, EntityBatch batch){
        return new LongClick(indexEt,listDistinctSampleNumber,ac,results,samples,db,context,et,limits,holder,batch);
    }
    @Override
    public boolean onLongClick(View view) {
        EntityResult resToUpdate = EntityResult.getResultFromSampleAnalysisComponent(new ArrayList<>(results), listDistinctSampleNumber.get(holder.getAbsoluteAdapterPosition()),
                ac.get(indexEt).getAnalysis(),ac.get(indexEt).getEnComponentName());
        if(Objects.nonNull(resToUpdate)){
            resToUpdate.setTemporary((resToUpdate.getTemporary().equals(EntityResult.ENTRY_TEMPORANEA))? EntityResult.ENTRY_NOT_TEMPORANEA:EntityResult.ENTRY_TEMPORANEA);
            resToUpdate.update(resToUpdate,db);
            batch.calculateBatchLocStatus(db);
            GraphicManagement.setGraphicEditTextCompliance(context,et,resToUpdate,null,false);
        }
        return false;
    }
}
