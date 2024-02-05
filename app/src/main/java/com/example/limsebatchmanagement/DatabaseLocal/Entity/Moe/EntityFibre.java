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
@Entity(primaryKeys = {"SAMPLE_NUMBER","PROJECT","ID_FIBRA"})
public class EntityFibre extends EntityGeneric {
    public static final String EMPTY = "_";
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNumber;
    @NonNull @ColumnInfo(name = "PROJECT")
    private String project;
    @ColumnInfo(name = "ID_FIBRA")
    private int idFibra;
    @ColumnInfo(name = "TIPO_FIBRA")
    private String tipoFibra;
    @ColumnInfo(name = "LUNGHEZZA")
    private String lunghezza;
    @ColumnInfo(name = "DIAMETRO")
    private String diametro;
    @ColumnInfo(name = "QUANTITA")
    private String quantita;

    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntityFibre t = new EntityFibre();
        //t.sampleNumber = Integer.parseInt(list.get(0).toString());
        //t.project = list.get(1).toString();
        //t.idFibra = Integer.parseInt(list.get(2).toString());
        //t.tipoFibra = list.get(3).toString();
        //t.lunghezza = list.get(4).toString();
        //t.diametro = list.get(5).toString();
        //t.quantita = list.get(6).toString();
        return t;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("SAMPLE_NUMBER",this.sampleNumber);
        docInt.put("PROJECT",this.project);
        docInt.put("ID_FIBRA",this.idFibra);
        docInt.put("TIPO_FIBRA",this.tipoFibra);
        docInt.put("LUNGHEZZA",this.lunghezza);
        docInt.put("DIAMETRO",this.diametro);
        docInt.put("QUANTITA",this.quantita);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.sampleNumber,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlFibre().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        EntityFibre fibraToCompare = (EntityFibre) entityGeneric;
        this.tipoFibra = (this.tipoFibra.equals(EMPTY))? fibraToCompare.tipoFibra:this.tipoFibra;
        this.lunghezza = (this.lunghezza.equals(EMPTY))? fibraToCompare.lunghezza:this.lunghezza;
        this.diametro = (this.diametro.equals(EMPTY))? fibraToCompare.diametro:this.diametro;
        this.quantita = (this.quantita.equals(EMPTY))? fibraToCompare.quantita:this.quantita;
        db.SqlFibre().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlFibre().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "Fibre";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntityFibre)
            return this.sampleNumber==((EntityFibre) obj).sampleNumber && this.project.equals(((EntityFibre) obj).project) && this.idFibra==((EntityFibre) obj).idFibra;
        return super.equals(obj);
    }
    public int getSampleNumber() {return sampleNumber;}
    public String getProject() {return project;}
    public int getIdFibra() {return idFibra;}
    public String getTipoFibra() {return tipoFibra;}
    public String getLunghezza() {return lunghezza;}
    public String getDiametro() {return diametro;}
    public String getQuantita() {return quantita;}
    public void setSampleNumber(int sampleNumber) {this.sampleNumber = sampleNumber;}
    public void setProject(String project) {this.project = project;}
    public void setIdFibra(int idFibra) {this.idFibra = idFibra;}
    public void setTipoFibra(String tipoFibra) {this.tipoFibra = tipoFibra;}
    public void setLunghezza(String lunghezza) {this.lunghezza = lunghezza;}
    public void setDiametro(String diametro) {this.diametro = diametro;}
    public void setQuantita(String quantita) {this.quantita = quantita;}
}
