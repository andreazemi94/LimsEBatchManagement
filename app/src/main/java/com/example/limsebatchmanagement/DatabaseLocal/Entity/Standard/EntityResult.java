package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity(primaryKeys = {"RESULT_NUMBER"})
public class EntityResult extends EntityGeneric{
    public static final String ENTRY_VUOTA = "_", ENTRY_TEMPORANEA = "T", COMPONENT_DATE = "Y",COMPONENT_SPINNER = "L", DISPLAYED = "T",ENTRY_NOT_TEMPORANEA = "F";
    public static final String COMPONENT_ANALYSIS_DATE = "Analysis date", COMPONENT_PREP_DATE = "Preparative Date", COMPONENT_GRV_MEM = "Grv membrana";
    public static final String COMPONENT_COD_TEMP = "COD_TEMP_WET", COMPONENT_PHOSPHORUS_TEMP = "PHOSPHORUS-TEMP",COMPONENT_PHOSPHORUS = "PHOSPHORUS-R";
    public static final String ANALYSIS_DATE = "ANALYSIS_DATE";
    @ColumnInfo(name = "RESULT_NUMBER")
    private int resultNumber;
    @ColumnInfo(name = "TEST_NUMBER")
    private int testNumber;
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNumber;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "EN_COMPONENT_NAME")
    private String enComponentName;
    @ColumnInfo(name = "ANALYSIS")
    private String analysis;
    @ColumnInfo(name = "ENTRY")
    private String entry;
    @ColumnInfo(name = "RESULT_TYPE")
    private String resultType;
    @ColumnInfo(name = "LIST_KEY")
    private String listKey;
    @ColumnInfo(name = "DISPLAYED")
    private String displayed;
    @ColumnInfo(name = "ORDER_DISPLAY")
    private int orderDisplay;
    @ColumnInfo(name = "UNIT")
    private String unit;
    @ColumnInfo(name = "TEMPORARY")
    private String temporary;

    public static EntityResult getResultFromSampleAnalysisComponent(List<EntityResult>results,Integer sampleNumber,String analysis,String enComponent){
        results.removeIf(res->res.sampleNumber!=sampleNumber);
        results.removeIf(res->!res.analysis.equals(analysis));
        results.removeIf(res->!res.enComponentName.equals(enComponent));
        if (results.size()>0)
            return results.get(0);
        return null;
    }

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityResult r = new EntityResult();
        map.forEach((k,v)->{
            switch(k){
                case"RESULT_NUMBER": r.resultNumber=Integer.parseInt(v); break;
                case"SAMPLE_NUMBER": r.sampleNumber=Integer.parseInt(v); break;
                case"TEST_NUMBER": r.testNumber=Integer.parseInt(v); break;
                case"NAME": r.name=v; break;
                case"EN_COMPONENT_NAME": r.enComponentName=v; break;
                case"ANALYSIS": r.analysis=v; break;
                case"ENTRY": r.entry=v; break;
                case"RESULT_TYPE": r.resultType=v; break;
                case"LIST_KEY": r.listKey=v; break;
                case"DISPLAYED": r.displayed=v; break;
                case"ORDER_DISPLAY": r.orderDisplay=Integer.parseInt(v); break;
                case"UNIT": r.unit=v; break;
                case"TEMPORARY": r.temporary=v; break;
            }
        });
        return r;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("RESULT_NUMBER",this.resultNumber);
        docInt.put("SAMPLE_NUMBER",this.sampleNumber);
        docInt.put("TEST_NUMBER",this.testNumber);
        docInt.put("NAME",this.name);
        docInt.put("EN_COMPONENT_NAME",this.enComponentName);
        docInt.put("ANALYSIS",this.analysis);
        docInt.put("ENTRY",this.entry);
        docInt.put("RESULT_TYPE",this.resultType);
        docInt.put("LIST_KEY",this.listKey);
        docInt.put("DISPLAYED",this.displayed);
        docInt.put("ORDER_DISPLAY",this.orderDisplay);
        docInt.put("UNIT",this.unit);
        docInt.put("TEMPORARY",this.temporary);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.resultNumber,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlResult().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        EntityResult resultToCompare = (EntityResult) entityGeneric;
        if(this.entry.equals(EntityResult.ENTRY_VUOTA))
            this.entry = resultToCompare.entry;
        if(!this.temporary.equals(EntityResult.ENTRY_TEMPORANEA))
            this.temporary = resultToCompare.temporary;
        db.SqlResult().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlResult().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Result";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityResult)
            return this.resultNumber==((EntityResult) obj).resultNumber;
        return super.equals(obj);
    }

    public int getResultNumber() {return resultNumber;}
    public int getTestNumber() {return testNumber;}
    public int getSampleNumber() {return sampleNumber;}
    public String getName() {return name;}
    public String getEnComponentName() {return enComponentName;}
    public String getAnalysis() {return analysis;}
    public String getEntry() {return entry;}
    public String getResultType() {return resultType;}
    public String getListKey() {return listKey;}
    public String getDisplayed() {return displayed;}
    public int getOrderDisplay() {return orderDisplay;}
    public String getUnit() {return unit;}
    public String getTemporary() {return temporary;}
    public void setResultNumber(int resultNumber) {this.resultNumber = resultNumber;}
    public void setTestNumber(int testNumber) {this.testNumber = testNumber;}
    public void setSampleNumber(int sampleNumber) {this.sampleNumber = sampleNumber;}
    public void setName(String name) {this.name = name;}
    public void setEnComponentName(String enComponentName) {this.enComponentName = enComponentName;}
    public void setAnalysis(String analysis) {this.analysis = analysis;}
    public void setEntry(String entry) {this.entry = entry;}
    public void setResultType(String resultType) {this.resultType = resultType;}
    public void setListKey(String listKey) {this.listKey = listKey;}
    public void setDisplayed(String displayed) {this.displayed = displayed;}
    public void setOrderDisplay(int orderDisplay) {this.orderDisplay = orderDisplay;}
    public void setUnit(String unit) {this.unit = unit;}
    public void setTemporary(String temporary) {this.temporary = temporary;}
}
