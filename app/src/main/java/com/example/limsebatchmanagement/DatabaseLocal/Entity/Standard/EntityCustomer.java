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
public class EntityCustomer extends EntityGeneric{
    @PrimaryKey (autoGenerate = true)
    public int id;
    @ColumnInfo(name = "CODICE_CLIENTE")
    public String codiceCliente;
    @ColumnInfo(name = "DESCRIZIONE_CLIENTE")
    public String descrizioneCliente;
    @ColumnInfo(name = "ATTENZIONARE")
    public boolean attenzionare;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityCustomer c = new EntityCustomer();
        map.forEach((k,v)->{
            switch (k){
                case"CODICE_CLIENTE": c.codiceCliente=v; break;
                case"DESCRIZIONE_CLIENTE": c.descrizioneCliente=v; break;
                case"ATTENZIONARE": c.attenzionare=v.equals("T"); break;
            }
        });
        return c;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("CODICE_CLIENTE",this.codiceCliente);
        docInt.put("DESCRIZIONE_CLIENTE",this.descrizioneCliente);
        docInt.put("ATTENZIONARE",this.attenzionare);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.codiceCliente,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlCustomer().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlCustomer().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlCustomer().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Clienti Attenzionati";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityCustomer)
            return this.codiceCliente==((EntityCustomer) obj).codiceCliente;
        return super.equals(obj);
    }
}
