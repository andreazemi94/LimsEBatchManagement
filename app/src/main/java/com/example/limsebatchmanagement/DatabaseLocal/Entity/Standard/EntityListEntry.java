package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityListEntry extends EntityGeneric{
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo(name = "LIST")
    private String list;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "VALUE")
    private String value;
    @ColumnInfo(name = "IT_VALUE")
    private String itValue;
    @ColumnInfo(name = "PT_VALUE")
    private String ptValue;
    @ColumnInfo(name = "FR_VALUE")
    private String frValue;
    @ColumnInfo(name = "ORDER_NUMBER")
    private int orderNumber;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityListEntry le = new EntityListEntry();
        map.forEach((k,v)->{
            switch(k){
                case"NAME": le.name=v; break;
                case"LIST": le.list=v; break;
                case"VALUE": le.value=v; break;
                case"ORDER_NUMBER": le.orderNumber=Integer.parseInt(v); break;
                case"IT_VALUE": le.itValue=v; break;
                case"PT_VALUE": le.ptValue=v; break;
                case"FR_VALUE": le.frValue=v; break;
            }
        });
        return le;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("NAME",this.name);
        docInt.put("LIST",this.list);
        docInt.put("VALUE",this.value);
        docInt.put("ORDER_NUMBER",this.orderNumber);
        docInt.put("IT_VALUE",this.itValue);
        docInt.put("PT_VALUE",this.ptValue);
        docInt.put("FR_VALUE",this.frValue);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.name,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlListEntry().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlListEntry().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlListEntry().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "ListEntry";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityListEntry)
            return this.list.equals(((EntityListEntry) obj).list) && this.name.equals(((EntityListEntry) obj).name);
        return super.equals(obj);
    }

    public String getList() {return list;}
    public String getName() {return name;}
    public String getValue() {return value;}
    public String getItValue() {return itValue;}
    public String getPtValue() {return ptValue;}
    public String getFrValue() {return frValue;}
    public int getOrderNumber() {return orderNumber;}
    public void setList(String list) {this.list = list;}
    public void setName(String name) {this.name = name;}
    public void setValue(String value) {this.value = value;}
    public void setItValue(String itValue) {this.itValue = itValue;}
    public void setPtValue(String ptValue) {this.ptValue = ptValue;}
    public void setFrValue(String frValue) {this.frValue = frValue;}
    public void setOrderNumber(int orderNumber) {this.orderNumber = orderNumber;}
}
