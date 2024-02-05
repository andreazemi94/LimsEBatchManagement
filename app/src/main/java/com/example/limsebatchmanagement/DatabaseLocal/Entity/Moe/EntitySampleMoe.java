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
@Entity(primaryKeys = {"SAMPLE_NUMBER"})
public class EntitySampleMoe extends EntityGeneric {
    public static final String EMPTY = "_";
    @ColumnInfo(name = "SAMPLE_NUMBER")
    private int sampleNumber;
    @ColumnInfo(name = "PROJECT")
    private String project;
    @ColumnInfo(name = "DESCRIPTION")
    private String description;
    @ColumnInfo(name = "DATA_PREP")
    private String dataPrep;
    @ColumnInfo(name = "ANALISTA_PREP")
    private String analistaPrep;
    @ColumnInfo(name = "MATERIALE")
    private String materiale;
    @ColumnInfo(name = "DESCRIZIONE_DETTAGLIATA")
    private String descrizioneDettagliata;
    @ColumnInfo(name = "TRATTAMENTO")
    private String trattamento;
    @ColumnInfo(name = "SCREENING")
    private String screening;
    @ColumnInfo(name = "NOTE_PREP")
    private String notePrep;
    @ColumnInfo(name = "PESO")
    private String peso;
    @ColumnInfo(name = "DATA_ANALISI")
    private String dataAnalisi;
    @ColumnInfo(name = "ANALISTA_ANALISI")
    private String analistaAnalisi;
    @ColumnInfo(name = "N_CAMPI")
    private String numCampi;
    @ColumnInfo(name = "INGRANDIMENTI")
    private String ingrandimenti;
    @ColumnInfo(name = "FIBRE_RILEVATE")
    private String fibreRilevate;
    @ColumnInfo(name = "TIPOLOGIA_1")
    private String tipologia1;
    @ColumnInfo(name = "TIPOLOGIA_1_MORFOLOGIA")
    private String tipologia1Morfologia;
    @ColumnInfo(name = "TIPOLOGIA_1_ASPECT_RATIO")
    private String tipologia1AspectRatio;
    @ColumnInfo(name = "TIPOLOGIA_1_ANALISI_CHIMICA_EDS")
    private String tipologia1AnalisiChimicaEds;
    @ColumnInfo(name = "TIPOLOGIA_1_TIPO_FIBRA")
    private String tipologia1TipoFibra;
    @ColumnInfo(name = "TIPOLOGIA_1_N_FIBRE")
    private String tipologia1NFibre;
    @ColumnInfo(name = "TIPOLOGIA_2")
    private String tipologia2;
    @ColumnInfo(name = "TIPOLOGIA_2_MORFOLOGIA")
    private String tipologia2Morfologia;
    @ColumnInfo(name = "TIPOLOGIA_2_ASPECT_RATIO")
    private String tipologia2AspectRatio;
    @ColumnInfo(name = "TIPOLOGIA_2_ANALISI_CHIMICA_EDS")
    private String tipologia2AnalisiChimicaEds;
    @ColumnInfo(name = "TIPOLOGIA_2_TIPO_FIBRA")
    private String tipologia2TipoFibra;
    @ColumnInfo(name = "TIPOLOGIA_2_N_FIBRE")
    private String tipologia2NFibre;
    @ColumnInfo(name = "TIPOLOGIA_3")
    private String tipologia3;
    @ColumnInfo(name = "TIPOLOGIA_3_MORFOLOGIA")
    private String tipologia3Morfologia;
    @ColumnInfo(name = "TIPOLOGIA_3_ASPECT_RATIO")
    private String tipologia3AspectRatio;
    @ColumnInfo(name = "TIPOLOGIA_3_ANALISI_CHIMICA_EDS")
    private String tipologia3AnalisiChimicaEds;
    @ColumnInfo(name = "TIPOLOGIA_3_TIPO_FIBRA")
    private String tipologia3TipoFibra;
    @ColumnInfo(name = "TIPOLOGIA_3_N_FIBRE")
    private String tipologia3NFibre;
    @ColumnInfo(name = "ESITO_ANALISI_QUALIT")
    private String esitoAnalisiQualitativa;
    @ColumnInfo(name = "NOTE_ANALISI")
    private String noteAnalisi;
    @ColumnInfo(name = "STUB")
    private String stub;
    @ColumnInfo(name = "MATRICE")
    private String matrice;
    @ColumnInfo(name = "STRUMENTO")
    private String strumento;
    @ColumnInfo(name = "CONTATORE_CAMPI")
    private String contatoreCampi;
    @ColumnInfo(name = "PESO_ANTE_CALCINAZIONE")
    private String pesoAnteCalcinazione;
    @ColumnInfo(name = "PESO_POST_CALCINAZIONE")
    private String pesoPostCalcinazione;
    @ColumnInfo(name = "METODO")
    private String metodo;
    @ColumnInfo(name = "BANDA_VISIBILE")
    private String bandaVisibile;
    @ColumnInfo(name = "PREP_DONE")
    private String prepDone;
    @ColumnInfo(name = "ANALYSIS_DONE")
    private String analysisDone;


    @Override
    public EntityGeneric create(Map<String,String> map) {
        EntitySampleMoe c = new EntitySampleMoe();
        //c.sampleNumber = Integer.parseInt(list.get(0).toString());
        //c.project = list.get(1).toString();
        //c.description = list.get(2).toString();
        //c.dataPrep = list.get(3).toString();
        //c.analistaPrep = list.get(4).toString();
        //c.materiale = list.get(5).toString();
        //c.descrizioneDettagliata = list.get(6).toString();
        //c.trattamento = list.get(7).toString();
        //c.screening = list.get(8).toString();
        //c.notePrep = list.get(9).toString();
        //c.peso = list.get(10).toString();
        //c.dataAnalisi = list.get(11).toString();
        //c.analistaAnalisi = list.get(12).toString();
//        c.numCampi = list.get(13).toString();
//        c.ingrandimenti = list.get(14).toString();
//        c.fibreRilevate = list.get(15).toString();
//        c.tipologia1 = list.get(16).toString();
//        c.tipologia1Morfologia = list.get(17).toString();
//        c.tipologia1AspectRatio = list.get(18).toString();
//        c.tipologia1AnalisiChimicaEds = list.get(19).toString();
//        c.tipologia1TipoFibra = list.get(20).toString();
//        c.tipologia1NFibre = list.get(21).toString();
//        c.tipologia2 = list.get(22).toString();
//        c.tipologia2Morfologia = list.get(23).toString();
//        c.tipologia2AspectRatio = list.get(24).toString();
//        c.tipologia2AnalisiChimicaEds = list.get(25).toString();
//        c.tipologia2TipoFibra = list.get(26).toString();
//        c.tipologia2NFibre = list.get(27).toString();
//        c.tipologia3 = list.get(28).toString();
//        c.tipologia3Morfologia = list.get(29).toString();
//        c.tipologia3AspectRatio = list.get(30).toString();
//        c.tipologia3AnalisiChimicaEds = list.get(31).toString();
//        c.tipologia3TipoFibra = list.get(32).toString();
//        c.tipologia3NFibre = list.get(33).toString();
//        c.esitoAnalisiQualitativa = list.get(34).toString();
//        c.noteAnalisi = list.get(35).toString();
//        c.stub = list.get(36).toString();
//        c.matrice = list.get(37).toString();
//        c.strumento = list.get(38).toString();
//        c.contatoreCampi = list.get(39).toString();
//        c.pesoAnteCalcinazione = list.get(40).toString();
//        c.pesoPostCalcinazione = list.get(41).toString();
//        c.metodo = list.get(42).toString();
//        c.bandaVisibile = list.get(43).toString();
//        c.prepDone = list.get(44).toString();
//        c.analysisDone = list.get(45).toString();
        return c;
    }
    @Override
    public Map<Object,Map<Object,Object>> createDocumentFb() {
        Map<Object,Object> docInt = new HashMap<>();
        docInt.put("SAMPLE_NUMBER",this.sampleNumber);
        docInt.put("PROJECT",this.project);
        docInt.put("DESCRIPTION",this.description);
        docInt.put("DATA_PREP",this.dataPrep);
        docInt.put("ANALISTA_PREP",this.analistaPrep);
        docInt.put("MATERIALE",this.materiale);
        docInt.put("DESCRIZIONE_DETTAGLIATA",this.descrizioneDettagliata);
        docInt.put("TRATTAMENTO",this.trattamento);
        docInt.put("SCREENING",this.screening);
        docInt.put("NOTE_PREP",this.notePrep);
        docInt.put("PESO",this.peso);
        docInt.put("DATA_ANALISI",this.dataAnalisi);
        docInt.put("ANALISTA_ANALISI",this.analistaAnalisi);
        docInt.put("N_CAMPI",this.numCampi);
        docInt.put("INGRANDIMENTI",this.ingrandimenti);
        docInt.put("FIBRE_RILEVATE",this.fibreRilevate);
        docInt.put("TIPOLOGIA_1",this.tipologia1);
        docInt.put("TIPOLOGIA_1_MORFOLOGIA",this.tipologia1Morfologia);
        docInt.put("TIPOLOGIA_1_ASPECT_RATIO",this.tipologia1AspectRatio);
        docInt.put("TIPOLOGIA_1_ANALISI_CHIMICA_EDS",this.tipologia1AnalisiChimicaEds);
        docInt.put("TIPOLOGIA_1_TIPO_FIBRA",this.tipologia1TipoFibra);
        docInt.put("TIPOLOGIA_1_N_FIBRE",this.tipologia1NFibre);
        docInt.put("TIPOLOGIA_2",this.tipologia2);
        docInt.put("TIPOLOGIA_2_MORFOLOGIA",this.tipologia2Morfologia);
        docInt.put("TIPOLOGIA_2_ASPECT_RATIO",this.tipologia2AspectRatio);
        docInt.put("TIPOLOGIA_2_ANALISI_CHIMICA_EDS",this.tipologia2AnalisiChimicaEds);
        docInt.put("TIPOLOGIA_2_TIPO_FIBRA",this.tipologia2TipoFibra);
        docInt.put("TIPOLOGIA_2_N_FIBRE",this.tipologia2NFibre);
        docInt.put("TIPOLOGIA_3",this.tipologia3);
        docInt.put("TIPOLOGIA_3_MORFOLOGIA",this.tipologia3Morfologia);
        docInt.put("TIPOLOGIA_3_ASPECT_RATIO",this.tipologia3AspectRatio);
        docInt.put("TIPOLOGIA_3_ANALISI_CHIMICA_EDS",this.tipologia3AnalisiChimicaEds);
        docInt.put("TIPOLOGIA_3_TIPO_FIBRA",this.tipologia3TipoFibra);
        docInt.put("TIPOLOGIA_3_N_FIBRE",this.tipologia3NFibre);
        docInt.put("ESITO_ANALISI_QUALIT",this.esitoAnalisiQualitativa);
        docInt.put("NOTE_ANALISI",this.noteAnalisi);
        docInt.put("STUB",this.stub);
        docInt.put("MATRICE",this.matrice);
        docInt.put("STRUMENTO",this.strumento);
        docInt.put("CONTATORE_CAMPI",this.contatoreCampi);
        docInt.put("PESO_ANTE_CALCINAZIONE",this.pesoAnteCalcinazione);
        docInt.put("PESO_POST_CALCINAZIONE",this.pesoPostCalcinazione);
        docInt.put("METODO",this.metodo);
        docInt.put("BANDA_VISIBILE",this.bandaVisibile);
        docInt.put("PREP_DONE",this.prepDone);
        docInt.put("ANALYSIS_DONE",this.analysisDone);
        Map<Object,Map<Object,Object>> doc = new HashMap<>();
        doc.put(""+this.sampleNumber,docInt);
        return doc;
    }
    @Override
    public void insert(DatabaseBuilder db) {
        db.SqlSampleMoe().insert(this);
    }
    @Override
    public void update(EntityGeneric entityGeneric, DatabaseBuilder db) {
        EntitySampleMoe entitySampleMoe = (EntitySampleMoe) entityGeneric;
        this.dataPrep = (this.dataPrep.equals(EMPTY))? entitySampleMoe.dataPrep:this.dataPrep;
        this.analistaPrep = (this.analistaPrep.equals(EMPTY))? entitySampleMoe.analistaPrep:this.analistaPrep;
        this.materiale = (this.materiale.equals(EMPTY))? entitySampleMoe.materiale:this.materiale;
        this.descrizioneDettagliata = (this.descrizioneDettagliata.equals(EMPTY))? entitySampleMoe.descrizioneDettagliata:this.descrizioneDettagliata;
        this.trattamento = (this.trattamento.equals(EMPTY))? entitySampleMoe.trattamento:this.trattamento;
        this.screening = (this.screening.equals(EMPTY))? entitySampleMoe.screening:this.screening;
        this.notePrep = (this.notePrep.equals(EMPTY))? entitySampleMoe.notePrep:this.notePrep;
        this.peso = (this.peso.equals(EMPTY))? entitySampleMoe.peso:this.peso;
        this.dataAnalisi = (this.dataAnalisi.equals(EMPTY))? entitySampleMoe.dataAnalisi:this.dataAnalisi;
        this.analistaAnalisi = (this.analistaAnalisi.equals(EMPTY))? entitySampleMoe.analistaAnalisi:this.analistaAnalisi;
        this.numCampi = (this.numCampi.equals(EMPTY))? entitySampleMoe.numCampi:this.numCampi;
        this.ingrandimenti = (this.ingrandimenti.equals(EMPTY))? entitySampleMoe.ingrandimenti:this.ingrandimenti;
        this.fibreRilevate = (this.fibreRilevate.equals(EMPTY))? entitySampleMoe.fibreRilevate:this.fibreRilevate;
        this.tipologia1 = (this.tipologia1.equals(EMPTY))? entitySampleMoe.tipologia1:this.tipologia1;
        this.tipologia1Morfologia = (this.tipologia1Morfologia.equals(EMPTY))? entitySampleMoe.tipologia1Morfologia:this.tipologia1Morfologia;
        this.tipologia1AspectRatio = (this.tipologia1AspectRatio.equals(EMPTY))? entitySampleMoe.tipologia1AspectRatio:this.tipologia1AspectRatio;
        this.tipologia1AnalisiChimicaEds = (this.tipologia1AnalisiChimicaEds.equals(EMPTY))? entitySampleMoe.tipologia1AnalisiChimicaEds:this.tipologia1AnalisiChimicaEds;
        this.tipologia1TipoFibra = (this.tipologia1TipoFibra.equals(EMPTY))? entitySampleMoe.tipologia1TipoFibra:this.tipologia1TipoFibra;
        this.tipologia1NFibre = (this.tipologia1NFibre.equals(EMPTY))? entitySampleMoe.tipologia1NFibre:this.tipologia1NFibre;
        this.tipologia2 = (this.tipologia2.equals(EMPTY))? entitySampleMoe.tipologia2:this.tipologia2;
        this.tipologia2Morfologia = (this.tipologia2Morfologia.equals(EMPTY))? entitySampleMoe.tipologia2Morfologia:this.tipologia2Morfologia;
        this.tipologia2AspectRatio = (this.tipologia2AspectRatio.equals(EMPTY))? entitySampleMoe.tipologia2AspectRatio:this.tipologia2AspectRatio;
        this.tipologia2AnalisiChimicaEds = (this.tipologia2AnalisiChimicaEds.equals(EMPTY))? entitySampleMoe.tipologia2AnalisiChimicaEds:this.tipologia2AnalisiChimicaEds;
        this.tipologia2TipoFibra = (this.tipologia2TipoFibra.equals(EMPTY))? entitySampleMoe.tipologia2TipoFibra:this.tipologia2TipoFibra;
        this.tipologia2NFibre = (this.tipologia2NFibre.equals(EMPTY))? entitySampleMoe.tipologia2NFibre:this.tipologia2NFibre;
        this.tipologia3 = (this.tipologia3.equals(EMPTY))? entitySampleMoe.tipologia3:this.tipologia3;
        this.tipologia3Morfologia = (this.tipologia3Morfologia.equals(EMPTY))? entitySampleMoe.tipologia3Morfologia:this.tipologia3Morfologia;
        this.tipologia3AspectRatio = (this.tipologia3AspectRatio.equals(EMPTY))? entitySampleMoe.tipologia3AspectRatio:this.tipologia3AspectRatio;
        this.tipologia3AnalisiChimicaEds = (this.tipologia3AnalisiChimicaEds.equals(EMPTY))? entitySampleMoe.tipologia3AnalisiChimicaEds:this.tipologia3AnalisiChimicaEds;
        this.tipologia3TipoFibra = (this.tipologia3TipoFibra.equals(EMPTY))? entitySampleMoe.tipologia3TipoFibra:this.tipologia3TipoFibra;
        this.tipologia3NFibre = (this.tipologia3NFibre.equals(EMPTY))? entitySampleMoe.tipologia3NFibre:this.tipologia3NFibre;
        this.esitoAnalisiQualitativa = (this.esitoAnalisiQualitativa.equals(EMPTY))? entitySampleMoe.esitoAnalisiQualitativa:this.esitoAnalisiQualitativa;
        this.noteAnalisi = (this.noteAnalisi.equals(EMPTY))? entitySampleMoe.noteAnalisi:this.noteAnalisi;
        this.stub = (this.stub.equals(EMPTY))? entitySampleMoe.stub:this.stub;
        this.matrice = (this.matrice.equals(EMPTY))? entitySampleMoe.matrice:this.matrice;
        this.strumento = (this.strumento.equals(EMPTY))? entitySampleMoe.strumento:this.strumento;
        this.contatoreCampi = (this.contatoreCampi.equals(EMPTY))? entitySampleMoe.contatoreCampi:this.contatoreCampi;
        this.pesoAnteCalcinazione = (this.pesoAnteCalcinazione.equals(EMPTY))? entitySampleMoe.pesoAnteCalcinazione:this.pesoAnteCalcinazione;
        this.pesoPostCalcinazione = (this.pesoPostCalcinazione.equals(EMPTY))? entitySampleMoe.pesoPostCalcinazione:this.pesoPostCalcinazione;
        this.metodo = (this.metodo.equals(EMPTY))? entitySampleMoe.metodo:this.metodo;
        this.bandaVisibile = (this.bandaVisibile.equals(EMPTY))? entitySampleMoe.bandaVisibile:this.bandaVisibile;
        this.prepDone = (this.prepDone.equals(EMPTY))? entitySampleMoe.prepDone:this.prepDone;
        this.analysisDone = (this.analysisDone.equals(EMPTY))? entitySampleMoe.analysisDone:this.analysisDone;
        db.SqlSampleMoe().update(this);
    }
    @Override
    public void delete(DatabaseBuilder db) {
        db.SqlSampleMoe().delete(this);
    }

    @Override
    public String getNameEntity() {
        return "SampleMoe";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof EntitySampleMoe)
            return this.sampleNumber==((EntitySampleMoe) obj).sampleNumber;
        return super.equals(obj);
    }
    public int getSampleNumber() {return sampleNumber;}
    public String getProject() {return project;}
    public String getDescription() {return description;}
    public String getDataPrep() {return dataPrep;}
    public String getAnalistaPrep() {return analistaPrep;}
    public String getMateriale() {return materiale;}
    public String getDescrizioneDettagliata() {return descrizioneDettagliata;}
    public String getTrattamento() {return trattamento;}
    public String getScreening() {return screening;}
    public String getNotePrep() {return notePrep;}
    public String getPeso() {return peso;}
    public String getDataAnalisi() {return dataAnalisi;}
    public String getAnalistaAnalisi() {return analistaAnalisi;}
    public String getNumCampi() {return numCampi;}
    public String getIngrandimenti() {return ingrandimenti;}
    public String getFibreRilevate() {return fibreRilevate;}
    public String getTipologia1() {return tipologia1;}
    public String getTipologia1Morfologia() {return tipologia1Morfologia;}
    public String getTipologia1AspectRatio() {return tipologia1AspectRatio;}
    public String getTipologia1AnalisiChimicaEds() {return tipologia1AnalisiChimicaEds;}
    public String getTipologia1TipoFibra() {return tipologia1TipoFibra;}
    public String getTipologia1NFibre() {return tipologia1NFibre;}
    public String getTipologia2() {return tipologia2;}
    public String getTipologia2Morfologia() {return tipologia2Morfologia;}
    public String getTipologia2AspectRatio() {return tipologia2AspectRatio;}
    public String getTipologia2AnalisiChimicaEds() {return tipologia2AnalisiChimicaEds;}
    public String getTipologia2TipoFibra() {return tipologia2TipoFibra;}
    public String getTipologia2NFibre() {return tipologia2NFibre;}
    public String getTipologia3() {return tipologia3;}
    public String getTipologia3Morfologia() {return tipologia3Morfologia;}
    public String getTipologia3AspectRatio() {return tipologia3AspectRatio;}
    public String getTipologia3AnalisiChimicaEds() {return tipologia3AnalisiChimicaEds;}
    public String getTipologia3TipoFibra() {return tipologia3TipoFibra;}
    public String getTipologia3NFibre() {return tipologia3NFibre;}
    public String getEsitoAnalisiQualitativa() {return esitoAnalisiQualitativa;}
    public String getNoteAnalisi() {return noteAnalisi;}
    public String getStub() {return stub;}
    public String getMatrice() {return matrice;}
    public String getStrumento() {return strumento;}
    public String getContatoreCampi() {return contatoreCampi;}
    public String getPesoAnteCalcinazione() {return pesoAnteCalcinazione;}
    public String getPesoPostCalcinazione() {return pesoPostCalcinazione;}
    public String getMetodo() {return metodo;}
    public String getBandaVisibile() {return bandaVisibile;}
    public String getPrepDone() {return prepDone;}
    public String getAnalysisDone() {return analysisDone;}
    public void setSampleNumber(int sampleNumber) {this.sampleNumber = sampleNumber;}
    public void setProject(String project) {this.project = project;}
    public void setDescription(String description) {this.description = description;}
    public void setDataPrep(String dataPrep) {this.dataPrep = dataPrep;}
    public void setAnalistaPrep(String analistaPrep) {this.analistaPrep = analistaPrep;}
    public void setMateriale(String materiale) {this.materiale = materiale;}
    public void setDescrizioneDettagliata(String descrizioneDettagliata) {this.descrizioneDettagliata = descrizioneDettagliata;}
    public void setTrattamento(String trattamento) {this.trattamento = trattamento;}
    public void setScreening(String screening) {this.screening = screening;}
    public void setNotePrep(String notePrep) {this.notePrep = notePrep;}
    public void setPeso(String peso) {this.peso = peso;}
    public void setDataAnalisi(String dataAnalisi) {this.dataAnalisi = dataAnalisi;}
    public void setAnalistaAnalisi(String analistaAnalisi) {this.analistaAnalisi = analistaAnalisi;}
    public void setNumCampi(String numCampi) {this.numCampi = numCampi;}
    public void setIngrandimenti(String ingrandimenti) {this.ingrandimenti = ingrandimenti;}
    public void setFibreRilevate(String fibreRilevate) {this.fibreRilevate = fibreRilevate;}
    public void setTipologia1(String tipologia1) {this.tipologia1 = tipologia1;}
    public void setTipologia1Morfologia(String tipologia1Morfologia) {this.tipologia1Morfologia = tipologia1Morfologia;}
    public void setTipologia1AspectRatio(String tipologia1AspectRatio) {this.tipologia1AspectRatio = tipologia1AspectRatio;}
    public void setTipologia1AnalisiChimicaEds(String tipologia1AnalisiChimicaEds) {this.tipologia1AnalisiChimicaEds = tipologia1AnalisiChimicaEds;}
    public void setTipologia1TipoFibra(String tipologia1TipoFibra) {this.tipologia1TipoFibra = tipologia1TipoFibra;}
    public void setTipologia1NFibre(String tipologia1NFibre) {this.tipologia1NFibre = tipologia1NFibre;}
    public void setTipologia2(String tipologia2) {this.tipologia2 = tipologia2;}
    public void setTipologia2Morfologia(String tipologia2Morfologia) {this.tipologia2Morfologia = tipologia2Morfologia;}
    public void setTipologia2AspectRatio(String tipologia2AspectRatio) {this.tipologia2AspectRatio = tipologia2AspectRatio;}
    public void setTipologia2AnalisiChimicaEds(String tipologia2AnalisiChimicaEds) {this.tipologia2AnalisiChimicaEds = tipologia2AnalisiChimicaEds;}
    public void setTipologia2TipoFibra(String tipologia2TipoFibra) {this.tipologia2TipoFibra = tipologia2TipoFibra;}
    public void setTipologia2NFibre(String tipologia2NFibre) {this.tipologia2NFibre = tipologia2NFibre;}
    public void setTipologia3(String tipologia3) {this.tipologia3 = tipologia3;}
    public void setTipologia3Morfologia(String tipologia3Morfologia) {this.tipologia3Morfologia = tipologia3Morfologia;}
    public void setTipologia3AspectRatio(String tipologia3AspectRatio) {this.tipologia3AspectRatio = tipologia3AspectRatio;}
    public void setTipologia3AnalisiChimicaEds(String tipologia3AnalisiChimicaEds) {this.tipologia3AnalisiChimicaEds = tipologia3AnalisiChimicaEds;}
    public void setTipologia3TipoFibra(String tipologia3TipoFibra) {this.tipologia3TipoFibra = tipologia3TipoFibra;}
    public void setTipologia3NFibre(String tipologia3NFibre) {this.tipologia3NFibre = tipologia3NFibre;}
    public void setEsitoAnalisiQualitativa(String esitoAnalisiQualitativa) {this.esitoAnalisiQualitativa = esitoAnalisiQualitativa;}
    public void setNoteAnalisi(String noteAnalisi) {this.noteAnalisi = noteAnalisi;}
    public void setStub(String stub) {this.stub = stub;}
    public void setMatrice(String matrice) {this.matrice = matrice;}
    public void setStrumento(String strumento) {this.strumento = strumento;}
    public void setContatoreCampi(String contatoreCampi) {this.contatoreCampi = contatoreCampi;}
    public void setPesoAnteCalcinazione(String pesoAnteCalcinazione) {this.pesoAnteCalcinazione = pesoAnteCalcinazione;}
    public void setPesoPostCalcinazione(String pesoPostCalcinazione) {this.pesoPostCalcinazione = pesoPostCalcinazione;}
    public void setMetodo(String metodo) {this.metodo = metodo;}
    public void setBandaVisibile(String bandaVisibile) {this.bandaVisibile = bandaVisibile;}
    public void setPrepDone(String prepDone) {this.prepDone = prepDone;}
    public void setAnalysisDone(String analysisDone) {this.analysisDone = analysisDone;}
}
