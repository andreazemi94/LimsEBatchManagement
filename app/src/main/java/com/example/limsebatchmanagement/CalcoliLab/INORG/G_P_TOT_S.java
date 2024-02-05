package com.example.limsebatchmanagement.CalcoliLab.INORG;

import android.os.Build;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import java.text.DecimalFormat;
import java.util.*;
@RequiresApi(api = Build.VERSION_CODES.N)
public class G_P_TOT_S {

    public static void calcPhosphorusSoluble(List<EntityResult> results, EntitySample sample, EntityResult resToUpdate, EntityBatch batch, DatabaseBuilder db,
                                                List<EditText> editTexts, Integer indexEt){
        DecimalFormat doubleFormat = new DecimalFormat("##.###");
        if(resToUpdate.getName().equals(EntityResult.COMPONENT_PHOSPHORUS_TEMP) && !resToUpdate.getEntry().equals(EntityResult.ENTRY_VUOTA)){
            EntityResult resultPhosphorusSoluble = db.SqlResult().selectResultPhosphorusSoluble(resToUpdate.getTestNumber());
            if(!sample.getTextId().startsWith(EntitySample.MB) && !sample.getTextId().startsWith(EntitySample.CCV)){
                Double tempPhosphorusSoluble = Double.parseDouble(resToUpdate.getEntry());
                Double tempMBPhosphorusSoluble = Double.parseDouble(db.SqlResult().selectTempResultPhosphorusSolubleMB(batch.getBatchName()).getEntry());
                resultPhosphorusSoluble.setEntry(doubleFormat.format(tempPhosphorusSoluble - tempMBPhosphorusSoluble).replace(",","."));
                resultPhosphorusSoluble.update(resultPhosphorusSoluble,db);
                editTexts.get(indexEt+1).setText(resultPhosphorusSoluble.getEntry());
            } else if (sample.getTextId().startsWith(EntitySample.MB)){
                resultPhosphorusSoluble.setEntry(resToUpdate.getEntry());
                Double tempMBPhosphorusSoluble = Double.parseDouble(resToUpdate.getEntry());
                List<EntityResult> allPhosphorusSoluble = new ArrayList<>(results);
                allPhosphorusSoluble.removeIf(res->!res.getName().equals(EntityResult.COMPONENT_PHOSPHORUS_TEMP));
                resultPhosphorusSoluble.update(resultPhosphorusSoluble,db);
                editTexts.get(indexEt+1).setText(resultPhosphorusSoluble.getEntry());
                calcMassivePhosphorusSoluble(allPhosphorusSoluble,doubleFormat,tempMBPhosphorusSoluble,db);
            } else {
                resultPhosphorusSoluble.setEntry(resToUpdate.getEntry());
                resultPhosphorusSoluble.update(resultPhosphorusSoluble,db);
                editTexts.get(indexEt+1).setText(resultPhosphorusSoluble.getEntry());
            }
        }
    }
    private static void calcMassivePhosphorusSoluble(List<EntityResult> allPhosphorusSoluble,DecimalFormat doubleFormat,Double tempMBPhosphorusSoluble,
                                                        DatabaseBuilder db){
        allPhosphorusSoluble.forEach(singlePho->{
            EntitySample tempSample = db.SqlSample().selectSingleSample(singlePho.getSampleNumber());
            if(!tempSample.getTextId().startsWith(EntitySample.MB) && !tempSample.getTextId().startsWith(EntitySample.CCV)
                    && !singlePho.getEntry().equals(EntityResult.ENTRY_VUOTA)){
                EntityResult resPho = db.SqlResult().selectResultPhosphorusSoluble(singlePho.getTestNumber());
                if(!resPho.getEntry().equals(EntityResult.ENTRY_VUOTA)){
                    Double tempPhosphorusSoluble = Double.parseDouble(singlePho.getEntry());
                    resPho.setEntry(doubleFormat.format(tempPhosphorusSoluble - tempMBPhosphorusSoluble).replace(",","."));
                    resPho.update(resPho,db);
                }
            }
        });
    }
}
