package com.example.limsebatchmanagement.ModulesMoe.MOD2805;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntitySampleMoe;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.*;
import java.io.*;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MOD2805Pdf {
    private static File pdfFile;
    private static PdfDocument pdfDocument;
    private static PdfDocument.Page page;
    private static Canvas canvas;
    private static int pageNumber = 1;
    public static File create(Context context, Dialog pdUpload, String batchName, List<EntityBatchObject> batchObjects,
                              DatabaseBuilder db){
        try {
            pageNumber = 1;
            pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),batchName + " - MOD2805 - Analisi qualitativa.pdf");
            pdfDocument = new PdfDocument();
            batchObjects.forEach(bo->{
                EntitySampleMoe sampleMoe = db.SqlSampleMoe().selectSingleSampleMoe(bo.getSampleNUmber());
                EntitySample sample = db.SqlSample().selectSingleSample(bo.getSampleNUmber());
                addPages(context,sampleMoe,sample.getTextId());
            });
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
        } catch (Exception e) {
            pdUpload.dismiss();
            AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context, e.getMessage());
            dialogError.show();
        } finally {
            pdfDocument.close();
        }
        return pdfFile;
    }
    private static void addPages(Context context,EntitySampleMoe sampleMoe,String textId){
        page = PdfDraw.createPage(pdfDocument,pageNumber);
        canvas = page.getCanvas();
        setIntestazione(context,sampleMoe,textId);
        setPreparativa(context,sampleMoe);
        setAnalisi(context,sampleMoe);
        pdfDocument.finishPage(page);
        pageNumber++;
    }
    private static void setIntestazione(Context context,EntitySampleMoe sample,String textId){
        PdfDraw.drawBitMap(context,canvas, R.drawable.logo_merieux_color,270,100,50,50);
        PdfDraw.drawText(context,canvas,32,R.color.black,Typeface.BOLD,"Analisi qualitativa",450,70);
        PdfDraw.drawText(context,canvas,32,R.color.black,Typeface.BOLD,"dell'amianto in SEM",440,110);
        PdfDraw.drawText(context,canvas,32,R.color.black,Typeface.BOLD,"Scheda Campione",450,150);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Mod. 2805/SQ Rev.2",900,100);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,170,170);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1680,1680);
        PdfDraw.drawLine(context,canvas,R.color.black,50,50,170,1680);
        PdfDraw.drawLine(context,canvas,R.color.black,1080,1080,170,1680);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,51,171,350,1679);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"ID Campione",120,205);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, textId,360,205);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,220,220);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Descrizione cliente",85,250);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getDescription(),360,250);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,265,265);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,51,266,1079,321);
    }
    private static void setPreparativa(Context context,EntitySampleMoe sample){
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"PREPARATIVA",465,303);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,322,322);
        PdfDraw.drawLine(context,canvas,R.color.black,351,351,322,1000);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Data",165,361);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getDataPrep(),360,361);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,580,323,839,379);
        PdfDraw.drawLine(context,canvas,R.color.black,579,579,322,550);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Analista",660,361);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getAnalistaPrep(),850,361);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,322,550);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,380,380);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Materiale",140,470);
        PdfDraw.drawLine(context,canvas,R.color.black,351,1080,434,434);
        PdfDraw.drawLine(context,canvas,R.color.black,351,1080,463,463);
        PdfDraw.drawLine(context,canvas,R.color.black,351,1080,492,492);
        PdfDraw.drawLine(context,canvas,R.color.black,351,1080,521,521);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,550,550);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Altro tipo di materiale:",65,578);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,590,590);
        if(!sample.getMateriale().contains("-")){
            switch (sample.getMateriale()){
                case"Lastra/Copertura in cemento": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,382,578,433);break;
                case"Rivestimento edilizio": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,382,839,433);break;
                case"Intonaco friabile/materiale fibroso": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,382,1079,433);break;
                case"Guarnizione": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,435,578,462);break;
                case"Corda/Tessuto": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,435,839,462);break;
                case"Guaina bituminosa": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,435,1079,462);break;
                case"Pannello isolante": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,464,578,491);break;
                case"Asfalto": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,464,839,491);break;
                case"Inerti da costruzione": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,464,1079,491);break;
                case"Cartonato": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,493,578,520);break;
                case"Stucco/Mastice": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,493,839,520);break;
                case"Pavimentazione vinilica": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,493,1079,520);break;
                case"Suolo": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,522,578,549);break;
                case"Sedimento/Fango": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,522,839,549);break;
                case"Vernice": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,522,1079,549);break;
            }
        } else {
            PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,552,1078,589);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getMateriale().replace("-",""),
                    360,575);
        }
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Lastra/Copertura in",362,405);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"cemento",362,425);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Guarnizione",362,456);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Pannello isolante",362,485);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Cartonato",362,514);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Suolo",362,543);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Rivestimento edilizio",590,418);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Corda/Tessuto",590,456);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Asfalto",590,485);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Stucco/Mastice ",590,514);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Sedimento/Fango",590,543);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Intonaco friabile/",850,405);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"materiale fibroso",850,425);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Guaina bituminosa",850,456);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Inerti da costruzione",850,485);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Pavimentazione vinilica",850,514);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Vernice",850,543);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,665,665);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Descrizione dettagliata",65,615);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"(strati,colore,tessitura,ecc..)",65,648);
        writeNoteOrDescription(sample.getDescrizioneDettagliata(),605,655,context);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,800,800);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Trattamento del",95,753);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"campione",135,778);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,580,741,1079,759);
        PdfDraw.drawLine(context,canvas,R.color.black,579,1080,705,705);
        PdfDraw.drawLine(context,canvas,R.color.black,351,1080,740,740);
        PdfDraw.drawLine(context,canvas,R.color.black,579,1080,760,760);
        PdfDraw.drawLine(context,canvas,R.color.black,579,579,665,861);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,665,861);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Peso prima calcinazione (mg)",590,755);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Peso dopo calcinazione (mg)",850,755);
        switch (sample.getTrattamento()){
            case "Nessuno": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,666,577,739);break;
            case "Attacco acido": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,666,839,704);break;
            case "Calcinazione":
                PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,741,577,799);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,sample.getPesoAnteCalcinazione(),690,780);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,sample.getPesoPostCalcinazione(),951,780);
                break;
            case "Essiccazione": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,706,838,739);break;
            case "Macinazione in mortaio": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,666,1079,704);break;
            case "Macinazione con mulino": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,706,1079,739);break;
        }
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Nessuno",362,705);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Attacco acido",590,685);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Essiccazione",590,725);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Macinazione in mortaio",852,685);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Macinazione con mulino",852,725);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Calcinazione",362,780);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,861,861);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Screening",135,840);
        switch (sample.getScreening()){
            case "Nessuno": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,801,577,860);break;
            case "Stereo-microscopio": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,801,838,860);break;
            case "Microscopio Ottico": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,801,1079,860);break;
        }
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Nessuno",362,840);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Stereo - microscopio",590,840);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Microscopio",852,825);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Ottico",852,850);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,941,941);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Note",165,907);
        writeNoteOrDescription(sample.getNotePrep(),880,935,context);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1000,1000);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Peso Campione (mg)",80,977);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getPeso(),362,977);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,580,942,839,999);
        PdfDraw.drawLine(context,canvas,R.color.black,579,579,941,1000);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,941,1000);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Bilancia",660,977);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"BLC83",850,977);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,51,1001,1079,1056);
    }
    private static void setAnalisi(Context context,EntitySampleMoe sample){
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"ANALISI",500,1038);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,579,1058,839,1116);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,579,1118,839,1166);
        PdfDraw.drawRect(context,canvas,R.color.medium_grey,351,1218,1079,1266);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1057,1057);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1117,1117);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1167,1167);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1217,1217);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1267,1267);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1317,1317);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1367,1367);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1427,1427);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1477,1477);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1537,1537);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1600,1600);
        PdfDraw.drawLine(context,canvas,R.color.black,351,351,1057,1680);
        PdfDraw.drawLine(context,canvas,R.color.black,579,579,1057,1600);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,1057,1600);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Data",165,1092);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getDataAnalisi(),360,1092);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Analista",660,1092);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getAnalistaAnalisi(),850,1092);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Numero campi osservati",56,1152);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getNumCampi(),362,1152);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Ingrandimenti",625,1152);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getIngrandimenti(),851,1152);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Rilevate fibre",115,1202);
        if(sample.getFibreRilevate().equals("No"))
            PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,1168,578,1216);
        else {
            PdfDraw.drawRect(context, canvas, R.color.spinner_blue, 580, 1168, 839, 1216);
            if(sample.getTipologia1().equals("Si")){
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia1Morfologia(),362,1302);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia1AspectRatio(),362,1352);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia1AnalisiChimicaEds(),362,1402);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia1TipoFibra(),362,1462);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia1NFibre(),362,1517);
            }
            if(sample.getTipologia2().equals("Si")){
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia2Morfologia(),590,1302);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia2AspectRatio(),590,1352);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia2AnalisiChimicaEds(),590,1402);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia2TipoFibra(),590,1462);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia2NFibre(),590,1517);
            }
            if(sample.getTipologia3().equals("Si")){
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia3Morfologia(),851,1302);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia3AspectRatio(),851,1352);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia3AnalisiChimicaEds(),851,1402);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia3TipoFibra(),851,1462);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getTipologia3NFibre(),851,1517);
            }
        }
        switch (sample.getEsitoAnalisiQualitativa()){
            case "Non rilevato": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,352,1538,578,1599);break;
            case "Rilevato in tracce": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,580,1538,839,1599);break;
            case "Rilevato in alte concentrazioni": PdfDraw.drawRect(context,canvas,R.color.spinner_blue,841,1538,1079,1599);break;
        }
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Non rilevato",402,1577);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Rilevato in tracce",620,1577);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Rilevato in alte",851,1562);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"concentrazioni",851,1592);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"NO",450,1202);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"SI",695,1202);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Analisi delle fibre",95,1252);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Tipologia 1",407,1252);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Tipologia 2",635,1252);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Tipologia 3",896,1252);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Morfologia",135,1302);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Aspect Ratio",115,1352);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Analisi chimica EDS",90,1392);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"effettuata",135,1422);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Tipo di fibra identificata",61,1462);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Numero di fibre",105,1502);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"identificate",125,1532);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Esito analisi qualitativa",64,1562);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"amianto",140,1592);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Note",165,1640);
        writeNoteOrDescription(sample.getNoteAnalisi(),1620,1670,context);
    }
    private static void writeNoteOrDescription(String noteOrDescription, int heightPrint, int heightMax,Context context){
        String stringToPrint = "";
        String[] splitNote = noteOrDescription.split(" ");
        for(String singleSplit:splitNote){
            int charNumber = stringToPrint.length()+singleSplit.length()+360;
            if(charNumber<460){
                if(stringToPrint.length()==0)
                    stringToPrint = singleSplit + " ";
                else
                    stringToPrint = stringToPrint + singleSplit + " ";
            } else {
                if(heightPrint>heightMax)
                    break;
                else {
                    PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,stringToPrint,360,heightPrint);
                    heightPrint+=20;
                    stringToPrint="";
                }
            }
        }
        if(heightPrint<heightMax)
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,stringToPrint,360,heightPrint);
    }
}
