package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import java.util.*;
import java.util.function.BiFunction;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity(primaryKeys = {"SAMPLE_NUMBER"})
public class EntitySample extends EntityGeneric{
    public static final String MB = "MB", CCV = "CCV";
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNumber;
    @ColumnInfo(name = "PROJECT")
    private String project;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;
    @ColumnInfo(name = "TEXT_ID")
    private String textId;
    @ColumnInfo(name = "LABEL_ID")
    private String labelId;
    @ColumnInfo(name = "MATRIX")
    private String matrix;
    @ColumnInfo(name = "CUSTOMER_DESCRIPTION")
    private String customerDescription;

    public static BiFunction<List<EntitySample>,Integer,EntitySample> getSampleFromSampleNumber = (list,num)->{
        List<EntitySample> appoSamples = new ArrayList<>(list);
        appoSamples.removeIf(sample -> sample.sampleNumber!=num);
        return appoSamples.get(0);
    };

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntitySample s = new EntitySample();
        map.forEach((k,v)->{
            switch(k){
                case"SAMPLE_NUMBER": s.sampleNumber=Integer.parseInt(v); break;
                case"PROJECT": s.project=v; break;
                case"DESCRIPTION": s.description=v; break;
                case"TEXT_ID": s.textId=v; break;
                case"LABEL_ID": s.labelId=v; break;
                case"MATRIX": s.matrix=v; break;
                case"DESCRIZIONE_CLIENTE": s.customerDescription=v; break;
            }
        });
        return s;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("SAMPLE_NUMBER",this.sampleNumber);
        docInt.put("PROJECT",this.project);
        docInt.put("DESCRIPTION",this.description);
        docInt.put("TEXT_ID",this.textId);
        docInt.put("LABEL_ID",this.labelId);
        docInt.put("MATRIX",this.matrix);
        docInt.put("DESCRIZIONE_CLIENTE",this.customerDescription);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.sampleNumber,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlSample().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlSample().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlSample().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Sample";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntitySample)
            return this.sampleNumber==((EntitySample) obj).sampleNumber;
        return super.equals(obj);
    }
    public int getSampleNumber() {return sampleNumber;}
    public String getProject() {return project;}
    public String getDescription() {return description;}
    public String getTextId() {return textId;}
    public String getLabelId() {return labelId;}
    public String getMatrix() {return matrix;}
    public String getCustomerDescription() {return customerDescription;}
    public void setSampleNumber(int sampleNumber) {this.sampleNumber = sampleNumber;}
    public void setProject(String project) {this.project = project;}
    public void setDescription(String description) {this.description = description;}
    public void setTextId(String textId) {this.textId = textId;}
    public void setLabelId(String labelId) {this.labelId = labelId;}
    public void setMatrix(String matrix) {this.matrix = matrix;}
    public void setCustomerDescription(String customerDescription) {this.customerDescription = customerDescription;}
}
