package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity(primaryKeys = {"TEST_NUMBER"})
public class EntityTest extends EntityGeneric{
    @ColumnInfo(name = "TEST_NUMBER")
    private int testNumber;
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNumber;
    @ColumnInfo(name = "ANALYSIS")
    private String analysis;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityTest t = new EntityTest();
        map.forEach((k,v)->{
            switch(k){
                case"TEST_NUMBER": t.testNumber=Integer.parseInt(v); break;
                case"SAMPLE_NUMBER": t.sampleNumber=Integer.parseInt(v);break;
                case"ANALYSIS": t.analysis=v; break;
            }
        });
        return t;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("TEST_NUMBER",this.testNumber);
        docInt.put("SAMPLE_NUMBER",this.sampleNumber);
        docInt.put("ANALYSIS",this.analysis);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.testNumber,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlTest().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlTest().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlTest().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Test";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityTest)
            return this.testNumber==((EntityTest) obj).testNumber;
        return super.equals(obj);
    }
    public int getTestNumber() {return testNumber;}
    public int getSampleNumber() {return sampleNumber;}
    public String getAnalysis() {return analysis;}
    public void setTestNumber(int testNumber) {this.testNumber = testNumber;}
    public void setSampleNumber(int sampleNumber) {this.sampleNumber = sampleNumber;}
    public void setAnalysis(String analysis) {this.analysis = analysis;}
}
