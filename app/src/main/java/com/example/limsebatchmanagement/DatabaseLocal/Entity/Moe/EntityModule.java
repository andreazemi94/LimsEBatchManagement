package com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityGeneric;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityModule extends EntityGeneric {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "ANALYSIS")
    private String analysis;
    @ColumnInfo(name = "MODULE")
    private String module;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityModule t = new EntityModule();
        //t.analysis = list.get(0).toString();
        //t.module = list.get(1).toString();
        //t.description = list.get(2).toString();
        return t;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("ANALYSIS",this.analysis);
        docInt.put("MODULE",this.module);
        docInt.put("DESCRIPTION",this.description);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.analysis,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlModule().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlModule().update((EntityModule) entityGeneric);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlModule().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Module";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityModule)
            return this.analysis.equals(((EntityModule) obj).analysis) && this.module.equals(((EntityModule) obj).module);
        return super.equals(obj);
    }
    public String getAnalysis() {return analysis;}
    public String getModule() {return module;}
    public String getDescription() {return description;}
    public void setAnalysis(String analysis) {this.analysis = analysis;}
    public void setModule(String module) {this.module = module;}
    public void setDescription(String description) {this.description = description;}
}
