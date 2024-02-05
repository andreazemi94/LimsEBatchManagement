package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.*;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityLimit extends EntityGeneric{
    @PrimaryKey
    @ColumnInfo(name = "ID")
    public int id;
    @ColumnInfo(name = "MATRICE")
    private String matrice;
    @ColumnInfo(name = "COMPONENT")
    private String component;
    @ColumnInfo(name = "DESCRIZIONE_COMPONENT")
    private String descrizioneComponent;
    @ColumnInfo(name = "UM")
    private String um;
    @ColumnInfo(name = "LIMITE_MIN")
    private double limiteMin;
    @ColumnInfo(name = "LIMITE_MAX")
    private double limiteMax;

    public static EntityLimit getLimitFromResultSample (EntityResult res, EntitySample sample, List<EntityLimit> limits){
        List<EntityLimit> limits1 = new ArrayList<>(limits);
        limits1.removeIf(lim->!lim.matrice.equals(sample.getMatrix()));
        limits1.removeIf(lim->!lim.component.equals(res.getName()));
        limits1.removeIf(lim->!lim.um.equals(res.getUnit()));
        if(limits1.size()>0)
            return limits1.get(0);
        return null;
    }

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityLimit l = new EntityLimit();
        map.forEach((k,v)->{
            switch (k){
                case"ID": l.id=Integer.parseInt(v); break;
                case"MATRICE": l.matrice=v; break;
                case"COMPONENT": l.component=v; break;
                case"DESCRIZIONE_COMPONENT": l.descrizioneComponent=v; break;
                case"UM": l.um=v; break;
                case"LIMITE_MIN": l.limiteMin=Double.parseDouble(v); break;
                case"LIMITE_MAX": l.limiteMax=Double.parseDouble(v); break;
            }
        });
        return l;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("ID",this.id);
        docInt.put("MATRICE",this.matrice);
        docInt.put("COMPONENT",this.component);
        docInt.put("DESCRIZIONE_COMPONENT",this.descrizioneComponent);
        docInt.put("UM",this.um);
        docInt.put("LIMITE_MIN",this.limiteMin);
        docInt.put("LIMITE_MAX",this.limiteMax);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(this.matrice,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlLimit().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        db.SqlLimit().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlLimit().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Limit";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityLimit) {
            EntityLimit el = (EntityLimit) obj;
            boolean checkString = this.matrice.equals(el.matrice) && this.component.equals(el.component)
                    && this.descrizioneComponent.equals(el.descrizioneComponent)
                    && this.um.equals(el.um);
            boolean checkLimiteMin = this.limiteMin==el.limiteMin;
            boolean checkLimiteMax = this.limiteMax==el.limiteMax;
            return checkString && checkLimiteMin && checkLimiteMax;
        }
        return super.equals(obj);
    }

    public String getMatrice() {return matrice;}
    public String getComponent() {return component;}
    public String getDescrizioneComponent() {return descrizioneComponent;}
    public String getUm() {return um;}
    public double getLimiteMin() {return limiteMin;}
    public double getLimiteMax() {return limiteMax;}

    public void setMatrice(String matrice) {this.matrice = matrice;}
    public void setComponent(String component) {this.component = component;}
    public void setDescrizioneComponent(String descrizioneComponent) {this.descrizioneComponent = descrizioneComponent;}
    public void setUm(String um) {this.um = um;}
    public void setLimiteMin(double limiteMin) {this.limiteMin = limiteMin;}
    public void setLimiteMax(double limiteMax) {this.limiteMax = limiteMax;}
}
