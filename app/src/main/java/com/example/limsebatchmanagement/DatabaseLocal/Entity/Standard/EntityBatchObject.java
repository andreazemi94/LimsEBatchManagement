package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity(primaryKeys = {"OBJECT_ID","SAMPLE_NUMBER","BATCH"})
public class EntityBatchObject extends EntityGeneric{
    @ColumnInfo(name = "OBJECT_ID")
    private int objectId;
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNUmber;
    @ColumnInfo(name = "BATCH")
    @NonNull private String batch;
    @ColumnInfo(name = "SAMPLE_TYPE")
    private String sampleType;
    @ColumnInfo(name = "ORDER_NUMBER")
    private int orderNumber;
    @ColumnInfo(name = "LABEL_ID")
    private String labelId;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityBatchObject bo = new EntityBatchObject();
        map.forEach((k,v)->{
            switch(k){
                case"OBJECT_ID": bo.objectId=Integer.parseInt(v); break;
                case"SAMPLE_NUMBER": bo.sampleNUmber=Integer.parseInt(v); break;
                case"BATCH": bo.batch=v; break;
                case"SAMPLE_TYPE": bo.sampleType=v; break;
                case"ORDER_NUMBER": bo.orderNumber=Integer.parseInt(v); break;
                case"LABEL_ID": bo.labelId=v; break;
            }
        });
        return bo;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("OBJECT_ID",this.objectId);
        docInt.put("SAMPLE_NUMBER",this.sampleNUmber);
        docInt.put("BATCH",this.batch);
        docInt.put("SAMPLE_TYPE",this.sampleType);
        docInt.put("ORDER_NUMBER",this.orderNumber);
        docInt.put("LABEL_ID",this.labelId);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.objectId,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlBatchObject().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        EntityBatchObject batchObjComp = (EntityBatchObject)entityGeneric;
        this.batch = batchObjComp.batch;
        this.orderNumber = batchObjComp.orderNumber;
        db.SqlBatchObject().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlBatchObject().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Batch Objects";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityBatchObject){
            EntityBatchObject bo = (EntityBatchObject) obj;
            return this.objectId==bo.objectId && this.sampleNUmber==bo.sampleNUmber && this.batch.equals(bo.batch);
        }
        return super.equals(obj);
    }
    public static BiFunction<List<EntityBatchObject>,Integer,EntityBatchObject> getBatchObjectFromSample = (list,number)->{
        List<EntityBatchObject> appoBo = new ArrayList<>(list);
        appoBo.removeIf(bo->bo.sampleNUmber!=number);
        return appoBo.get(0);
    };

    public int getObjectId() {return objectId;}
    public int getSampleNUmber() {return sampleNUmber;}
    @NonNull public String getBatch() {return batch;}
    public String getSampleType() {return sampleType;}
    public int getOrderNumber() {return orderNumber;}
    public String getLabelId() {return labelId;}

    public void setObjectId(int objectId) {this.objectId = objectId;}
    public void setSampleNUmber(int sampleNUmber) {this.sampleNUmber = sampleNUmber;}
    public void setBatch(@NonNull String batch) {this.batch = batch;}
    public void setSampleType(String sampleType) {this.sampleType = sampleType;}
    public void setOrderNumber(int orderNumber) {this.orderNumber = orderNumber;}
    public void setLabelId(String labelId) {this.labelId = labelId;}
}
