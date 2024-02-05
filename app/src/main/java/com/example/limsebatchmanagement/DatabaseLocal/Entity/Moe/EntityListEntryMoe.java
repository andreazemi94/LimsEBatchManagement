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
public class EntityListEntryMoe extends EntityGeneric {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "MODULE")
    private String module;
    @ColumnInfo(name = "KEYS")
    private String keys;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityListEntryMoe t = new EntityListEntryMoe();
        //t.module = list.get(0).toString();
        //t.keys = list.get(1).toString();
        //t.description = list.get(2).toString();
        return t;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("MODULE",this.module);
        docInt.put("KEYS",this.keys);
        docInt.put("DESCRIPTION",this.description);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.module,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlListEntryMoe().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlListEntryMoe().update((EntityListEntryMoe) entityGeneric);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlListEntryMoe().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "ListEntryMoe";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityListEntryMoe)
            return this.module.equals(((EntityListEntryMoe) obj).module) && this.keys.equals(((EntityListEntryMoe) obj).keys);
        return super.equals(obj);
    }
    public String getModule() {return module;}
    public String getKeys() {return keys;}
    public String getDescription() {return description;}
    public void setModule(String module) {this.module = module;}
    public void setKeys(String key) {this.keys = key;}
    public void setDescription(String description) {this.description = description;}
}
