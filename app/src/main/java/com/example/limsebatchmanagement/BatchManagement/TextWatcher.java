package com.example.limsebatchmanagement.BatchManagement;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.Adapter.AdapterBatchManagement;
import com.example.limsebatchmanagement.CalcoliLab.INORG.G_P_TOT_S;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_TOTALFIBRES_OM_A;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.Utility.GraphicManagement;
import java.util.*;
import java.util.function.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TextWatcher implements android.text.TextWatcher {
    Consumer<Editable> cEditable;
    private TextWatcher(Consumer<Editable> cEditable){
        this.cEditable=cEditable;
    }
    public static TextWatcher getTextWatch (Consumer<Editable> cEditable){
        return new TextWatcher(cEditable);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void afterTextChanged(Editable editable) {cEditable.accept(editable);}

    public static TextWatcher getTextWatchResult(Integer indexEt, List<Integer> listDistinctSampleNumber, List<EntityAnalysisComponent> ac, List<EntityResult> results,
                                                 List<EntitySample> samples, DatabaseBuilder db, Context context, EditText et, List<EntityLimit> limits,
                                                 AdapterBatchManagement.ViewHolder holder, EntityBatch batch,List<EditText> editTexts){
        return new TextWatcher(editable -> {
            if(!editable.toString().equals("") && ! editable.toString().equals(EntityResult.ENTRY_VUOTA)){
                String textEt = et.getText().toString();
                if(textEt.contains("_")) {
                    textEt = textEt.replace("_", "");
                    et.setText(textEt);
                    et.setSelection(et.getText().length());
                } else {
                    EntitySample sample = EntitySample.getSampleFromSampleNumber.apply(new ArrayList<>(samples),listDistinctSampleNumber.get(holder.getAbsoluteAdapterPosition()));
                    String analysis = ac.get(indexEt).getAnalysis();
                    String enComponentName = ac.get(indexEt).getEnComponentName();
                    EntityResult resToUpdate = EntityResult.getResultFromSampleAnalysisComponent(new ArrayList<>(results),sample.getSampleNumber(),analysis,enComponentName);
                    if(Objects.nonNull(resToUpdate)){
                        boolean entryNotDouble = checkEntryDouble.test(resToUpdate,textEt);
                        if(!entryNotDouble){
                            resToUpdate.setEntry(textEt);
                            resToUpdate.update(resToUpdate,db);
                            G_P_TOT_S.calcPhosphorusSoluble(results,sample,resToUpdate,batch,db,editTexts,indexEt);
                            G_TOTALFIBRES_OM_A.calcSingleFilterAnalizedArea(results,resToUpdate,db,ac,editTexts);
                            G_TOTALFIBRES_OM_A.calcSingleLFI(results,resToUpdate,db,ac,editTexts);
                            G_TOTALFIBRES_OM_A.calcSingleLFS(results,resToUpdate,db,ac,editTexts);
                            G_TOTALFIBRES_OM_A.calcSingleFiltTotArea(results,resToUpdate,db,ac,editTexts);
                        }
                        GraphicManagement.setGraphicEditTextCompliance(context,et,resToUpdate,EntityLimit.getLimitFromResultSample(resToUpdate,sample,limits),entryNotDouble);
                    }
                    batch.calculateBatchLocStatus(db);
                }
            }
        });
    }
    private static final BiPredicate<EntityResult,String> checkEntryDouble = (r, s) -> {
        try{
            if(!s.equals("") && !s.equals(EntityResult.ENTRY_VUOTA) && !r.getEnComponentName().equals(EntityResult.COMPONENT_GRV_MEM) && !r.getResultType().equals(EntityResult.COMPONENT_DATE)
                    && !r.getResultType().equals(EntityResult.COMPONENT_SPINNER) && r.getDisplayed().equals(EntityResult.DISPLAYED))
                Double.parseDouble(s);
            return false;
        } catch (Exception e) {
            return true;
        }
    };
}
