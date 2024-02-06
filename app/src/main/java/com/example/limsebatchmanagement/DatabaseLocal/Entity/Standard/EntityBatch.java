package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.N)
@Entity(primaryKeys = {"NAME"})
public class EntityBatch extends EntityGeneric{
    public static final String NEW = "1",IN_PROGRESS = "2", COMPLETE = "3",EXPORTED = "2",NOT_EXPORTED = "1", LOCKED = "2", NOTE_VUOTE = "Note";
    @ColumnInfo(name = "NAME")
    @NonNull private String batchName;
    @ColumnInfo(name = "INSTRUMENT")
    private String instrument;
    @ColumnInfo(name = "TOT_OBJ")
    private String totObj;
    @ColumnInfo(name = "FORECAST_DATE")
    private String forecastDate;
    @ColumnInfo(name = "FORECAST_FIN_DATE")
    private String forecastFinDate;
    @ColumnInfo(name = "FORECAST_USER")
    private String forecastUser;
    @ColumnInfo(name = "MIN_DUE_DATE")
    private String minDueDate;
    @ColumnInfo(name = "BATCH_QUALITY_LEVEL")
    private String batchQualityLevel;
    @ColumnInfo(name = "BATCH_DUE_DATE_TYPE_VALUE")
    private String batchDueDateTypeValue;
    @ColumnInfo(name = "BATCH_PHASE")
    private String batchPhase;
    @ColumnInfo(name = "STATUS")
    private String status;
    @ColumnInfo(name = "EXPORT_FILE")
    private String exportFile;
    @ColumnInfo(name = "NOTE_UFFICIALI")
    private String noteUfficiali;
    @ColumnInfo(name = "NOTE_LAB")
    private String noteLab;
    @ColumnInfo(name = "BATCH_SEQUENCE")
    private String batchSequence;
    @ColumnInfo(name = "SEMAPHORE")
    private String semaphore;
    @ColumnInfo(name = "LOC_STATUS")
    private String locStatus;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityBatch eb = new EntityBatch();
        map.forEach((k,v)->{

            switch (k){
                case"BATCH_NAME": eb.batchName=v; break;
                case"INSTRUMENT": eb.instrument=v; break;
                case"TOT_OBJ": eb.totObj=v; break;
                case"FORECAST_DATE_STR": eb.forecastDate=v; break;
                case"FORECAST_FIN_DATE_STR": eb.forecastFinDate=v; break;
                case"FORECAST_USER": eb.forecastUser=v; break;
                case"MIN_DUE_DATE_STR": eb.minDueDate=v; break;
                case"BATCH_QUALITY_VALUE": eb.batchQualityLevel=v; break;
                case"BATCH_DUE_DATE_TYPE_VALUE": eb.batchDueDateTypeValue=v; break;
                case"BATCH_STEP": eb.batchPhase=v; break;
                case"STATUS": eb.status=v; break;
                case"EXPORT_FILE": eb.exportFile=v; break;
                case"NOTE_UFFICIALI": eb.noteUfficiali=v; break;
                case"NOTE_LAB": eb.noteLab=v; break;
                case"BATCH_SEQUENCE": eb.batchSequence=v; break;
                case"SEMAPHORE": eb.semaphore=v; break;
            }
        });
        eb.locStatus = EntityBatch.NEW;
        return eb;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("BATCH_NAME",this.batchName);
        docInt.put("INSTRUMENT",this.instrument);
        docInt.put("TOT_OBJ",this.totObj);
        docInt.put("FORECAST_DATE_STR",this.forecastDate);
        docInt.put("FORECAST_FIN_DATE_STR",this.forecastFinDate);
        docInt.put("FORECAST_USER",this.forecastUser);
        docInt.put("MIN_DUE_DATE_STR",this.minDueDate);
        docInt.put("BATCH_QUALITY_VALUE",this.batchQualityLevel);
        docInt.put("BATCH_DUE_DATE_TYPE_VALUE",this.batchDueDateTypeValue);
        docInt.put("BATCH_STEP",this.batchPhase);
        docInt.put("STATUS",this.status);
        docInt.put("EXPORT_FILE",this.exportFile);
        docInt.put("NOTE_UFFICIALI",this.noteUfficiali);
        docInt.put("NOTE_LAB",this.noteLab);
        docInt.put("BATCH_SEQUENCE",this.batchSequence);
        docInt.put("SEMAPHORE",this.semaphore);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.batchName,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlBatch().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        EntityBatch batchCompare = (EntityBatch)entityGeneric;
        this.status = batchCompare.status;
        this.exportFile = batchCompare.exportFile;
        this.semaphore = batchCompare.semaphore;
        this.batchPhase = batchCompare.batchPhase;
        this.noteUfficiali = (this.noteUfficiali.equals(EntityBatch.NOTE_VUOTE))? batchCompare.noteUfficiali : this.noteUfficiali;
        this.noteLab = (this.noteLab.equals(EntityBatch.NOTE_VUOTE))? batchCompare.noteLab : this.noteLab;
        db.SqlBatch().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlBatch().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Batch";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityBatch)
            return this.batchName.equals(((EntityBatch) obj).batchName);
        return super.equals(obj);
    }
    public void calculateBatchStatus (List<EntityResult> results){
        List<EntityResult> appoResults = new ArrayList<>(results);
        List<EntityResult> temporaryResults = new ArrayList<>(results);
        appoResults.removeIf(res->!res.getEntry().equals(EntityResult.ENTRY_VUOTA));
        appoResults.removeIf(res->!res.getDisplayed().equals(EntityResult.DISPLAYED));
        temporaryResults.removeIf(res->!res.getTemporary().equals(EntityResult.ENTRY_TEMPORANEA));
        String valueToUp = EntityBatch.NEW;
        if (appoResults.size()==0 && temporaryResults.size()==0 && appoResults.size()!=results.size())
            valueToUp = EntityBatch.COMPLETE;
        else if(appoResults.size()!=results.size() || temporaryResults.size()>0)
            valueToUp = EntityBatch.IN_PROGRESS;
        this.status=valueToUp;
    }
    public void calculateBatchLocStatus (DatabaseBuilder db){
        List<EntityResult> results = db.SqlResult().selectResultsFromBatch(this.getBatchName());
        List<EntityResult> appoResults = new ArrayList<>(results);
        List<EntityResult> temporaryResults = new ArrayList<>(results);
        appoResults.removeIf(res->!res.getEntry().equals(EntityResult.ENTRY_VUOTA));
        appoResults.removeIf(res->!res.getDisplayed().equals(EntityResult.DISPLAYED));
        temporaryResults.removeIf(res->!res.getTemporary().equals(EntityResult.ENTRY_TEMPORANEA));
        String valueToUp = EntityBatch.NEW;
        if (appoResults.size()==0 && temporaryResults.size()==0 && appoResults.size()!=results.size())
            valueToUp = EntityBatch.COMPLETE;
        else if(appoResults.size()!=results.size() || temporaryResults.size()>0)
            valueToUp = EntityBatch.IN_PROGRESS;
        this.locStatus=valueToUp;
        this.update(this,db);
    }
    public String getBatchName(){return batchName;}
    public String getInstrument() {return instrument;}
    public String getTotObj() {return totObj;}
    public String getForecastDate() {return forecastDate;}
    public String getForecastFinDate() {return forecastFinDate;}
    public String getForecastUser() {return forecastUser;}
    public String getMinDueDate() {return minDueDate;}
    public String getBatchQualityLevel() {return batchQualityLevel;}
    public String getBatchDueDateTypeValue() {return batchDueDateTypeValue;}
    public String getBatchPhase() {return batchPhase;}
    public String getStatus() {return status;}
    public String getExportFile() {return exportFile;}
    public String getNoteUfficiali() {return noteUfficiali;}
    public String getNoteLab() {return noteLab;}
    public String getBatchSequence() {return batchSequence;}
    public String getSemaphore() {return semaphore;}
    public String getLocStatus() {return locStatus;}

    public void setBatchName(@NonNull String batchName) {this.batchName = batchName;}
    public void setInstrument(String instrument) {this.instrument = instrument;}
    public void setTotObj(String totObj) {this.totObj = totObj;}
    public void setForecastDate(String forecastDate) {this.forecastDate = forecastDate;}
    public void setForecastFinDate(String forecastFinDate) {this.forecastFinDate = forecastFinDate;}
    public void setForecastUser(String forecastUser) {this.forecastUser = forecastUser;}
    public void setMinDueDate(String minDueDate) {this.minDueDate = minDueDate;}
    public void setBatchQualityLevel(String batchQualityLevel) {this.batchQualityLevel = batchQualityLevel;}
    public void setBatchDueDateTypeValue(String batchDueDateTypeValue) {this.batchDueDateTypeValue = batchDueDateTypeValue;}
    public void setBatchPhase(String batchPhase) {this.batchPhase = batchPhase;}
    public void setStatus(String status) {this.status = status;}
    public void setExportFile(String exportFile) {this.exportFile = exportFile;}
    public void setNoteUfficiali(String noteUfficiali) {this.noteUfficiali = noteUfficiali;}
    public void setNoteLab(String noteLab) {this.noteLab = noteLab;}
    public void setBatchSequence(String batchSequence) {this.batchSequence = batchSequence;}
    public void setSemaphore(String semaphore) {this.semaphore = semaphore;}
    public void setLocStatus(String locStatus) {this.locStatus = locStatus;}
}
