package com.example.limsebatchmanagement.ModulesMoe.MOD2548;

import android.app.*;
import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.*;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MOD2548Pdf {
    private static File pdfFile;
    private static PdfDocument pdfDocument;
    private static PdfDocument.Page page;
    private static Canvas canvas;
    private static int pageNumber = 1;
    public static File create(Context context, Dialog pdUpload, String batchName, List<EntityBatchObject> batchObjects,
                              DatabaseBuilder db){
        try {
            pageNumber = 1;
            pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),batchName + " - MOD2548 - Conteggio Fibre.pdf");
            pdfDocument = new PdfDocument();
            batchObjects.forEach(bo->{
                EntitySampleMoe sampleMoe = db.SqlSampleMoe().selectSingleSampleMoe(bo.getSampleNUmber());
                EntitySample sample = db.SqlSample().selectSingleSample(bo.getSampleNUmber());
                List<EntityFibre> fibers = db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber());
                if(fibers.size()>0)
                    addPages(context,sampleMoe,fibers,sample.getTextId());
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
    private static void addPages(Context context,EntitySampleMoe sampleMoe,List<EntityFibre> fibers,String textId){
        IntStream.rangeClosed(0,fibers.size()/201).forEach(pageNumInt->{
            page = PdfDraw.createPage(pdfDocument,pageNumber);
            canvas = page.getCanvas();
            setIntestazione(context,sampleMoe,textId);
            setTabellaFibre(context,fibers,pageNumInt);
            setTabellaContatoreCampi(context,sampleMoe,fibers);
            setNoteOperatore(context,sampleMoe);
            pdfDocument.finishPage(page);
            pageNumber++;
        });
    }
    private static void setIntestazione(Context context,EntitySampleMoe sample,String textId){
        PdfDraw.drawBitMap(context, canvas, R.drawable.logo_merieux_color, 270, 100, 50, 50);
        PdfDraw.drawText(context, canvas, 32, R.color.black, Typeface.BOLD, "Conteggio fibre", 450, 70);
        PdfDraw.drawText(context, canvas, 32, R.color.black, Typeface.BOLD, "totali in MOCF", 450, 110);
        PdfDraw.drawText(context, canvas, 16, R.color.black, Typeface.NORMAL, "Mod. 2548/SQ Rev.3", 900, 100);
        PdfDraw.drawLine(context, canvas, R.color.black, 50, 1080,170,  170);
        PdfDraw.drawLine(context, canvas, R.color.black, 50, 1080,1680,  1680);
        PdfDraw.drawLine(context, canvas, R.color.black, 50, 50,170,  1680);
        PdfDraw.drawLine(context, canvas, R.color.black, 1080, 1080,170,  1680);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"ID Campione",65,230);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, textId,210,230);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,200,200);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,240,240);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,200,240);
        PdfDraw.drawLine(context,canvas,R.color.black,500,500,200,240);
        PdfDraw.drawLine(context,canvas,R.color.black,205,205,200,240);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Monitor lot. n°",65,290);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, "1",210,290);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,260,260);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,300,300);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,260,300);
        PdfDraw.drawLine(context,canvas,R.color.black,500,500,260,300);
        PdfDraw.drawLine(context,canvas,R.color.black,205,205,260,300);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Matrice",65,350);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getMatrice(),210,350);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,60,500,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,500,500,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,205,205,320,360);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Metodo",685,230);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getMetodo(),830,230);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,200,200);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,240,240);
        PdfDraw.drawLine(context,canvas,R.color.black,680,680,200,240);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,200,240);
        PdfDraw.drawLine(context,canvas,R.color.black,825,825,200,240);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Strumento",685,290);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getStrumento(),830,290);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,260,260);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,300,300);
        PdfDraw.drawLine(context,canvas,R.color.black,680,680,260,300);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,260,300);
        PdfDraw.drawLine(context,canvas,R.color.black,825,825,260,300);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Operatore",685,350);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getAnalistaAnalisi(),830,350);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,680,680,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,825,825,320,360);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Verifica microscopio con vetrino HSE NLP",245,410);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, "5° banda visibile",645,410);
        PdfDraw.drawLine(context,canvas,R.color.black,230,860,380,380);
        PdfDraw.drawLine(context,canvas,R.color.black,230,860,420,420);
        PdfDraw.drawLine(context,canvas,R.color.black,230,230,380,420);
        PdfDraw.drawLine(context,canvas,R.color.black,860,860,380,420);
        PdfDraw.drawLine(context,canvas,R.color.black,635,635,380,420);
        PdfDraw.drawLine(context,canvas,R.color.black,775,775,380,420);
        PdfDraw.drawLine(context,canvas,R.color.black,817,817,380,420);
        if(sample.getBandaVisibile().equals("Si"))
            PdfDraw.drawRect(context,canvas,R.color.spinner_blue,776,381,816,419);
        else
            PdfDraw.drawRect(context,canvas,R.color.spinner_blue,818,381,859,419);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, "Si",790,410);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, "No",828,410);
    }
    private static void setTabellaFibre(Context context,List<EntityFibre> fibers,int pageNumber){
        PdfDraw.drawLine(context, canvas, R.color.black, 65, 1065,440,  440);
        PdfDraw.drawLine(context, canvas, R.color.black, 65, 1065,1280,  1280);
        PdfDraw.drawLine(context, canvas, R.color.black, 65, 65,440,  1280);
        PdfDraw.drawLine(context, canvas, R.color.black, 1065, 1065,440,  1280);
        PdfDraw.drawLine(context, canvas, R.color.black, 65, 1065,480,  480);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Conteggio Fibre",480,465);
        IntStream.rangeClosed(1,10).forEach(i->{
            int y=400;
            PdfDraw.drawLine(context, canvas, R.color.black, 65,1065, y+(80*i),y+(80*i));
            PdfDraw.drawLine(context, canvas, R.color.black, 65, 1065, y+(80*i) +30,y+(80*i) +30);
        });
        IntStream.rangeClosed(1,20).forEach(i->{
            int x=65;
            PdfDraw.drawLine(context, canvas, R.color.black,x+(50*i),x+(50*i),480,1280);
        });
        IntStream.rangeClosed(1,10).forEach(i->{
            int x = 30;
            int y = 420;
            IntStream.rangeClosed(1,20).forEach(j->{
                int idFibra = (j+((i-1)*20))+(200*pageNumber);
                PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD, String.valueOf(idFibra),x+(50*j),y+(80*i));
                fibers.stream().filter(f->f.getIdFibra()==idFibra).findFirst()
                        .ifPresent(f->PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, f.getQuantita(),x+(50*j),y+(80*i)+40));
            });
        });
    }
    private static void setTabellaContatoreCampi(Context context,EntitySampleMoe sample,List<EntityFibre> fibers) {
        double totFibers = fibers.stream().mapToDouble(f->Double.parseDouble(f.getQuantita())).sum();
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"N° Campi",65,1330);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(sample.getNumCampi()),210,1330);
        PdfDraw.drawLine(context,canvas,R.color.black,60,450,1300,1300);
        PdfDraw.drawLine(context,canvas,R.color.black,60,450,1340,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,1300,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,450,450,1300,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,205,205,1300,1340);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Fibre Totali",65,1390);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, String.valueOf(totFibers),210,1390);
        PdfDraw.drawLine(context,canvas,R.color.black,60,450,1360,1360);
        PdfDraw.drawLine(context,canvas,R.color.black,60,450,1400,1400);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,1360,1400);
        PdfDraw.drawLine(context,canvas,R.color.black,450,450,1360,1400);
        PdfDraw.drawLine(context,canvas,R.color.black,205,205,1360,1400);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Data Analisi",685,1330);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getDataAnalisi(),830,1330);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,1300,1300);
        PdfDraw.drawLine(context,canvas,R.color.black,680,1070,1340,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,680,680,1300,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,1300,1340);
        PdfDraw.drawLine(context,canvas,R.color.black,825,825,1300,1340);
    }

    private static void setNoteOperatore(Context context,EntitySampleMoe sampleMoe) {
        PdfDraw.drawLine(context,canvas,R.color.black,350,1070,1520,1520);
        PdfDraw.drawLine(context,canvas,R.color.black,350,1070,1620,1620);
        PdfDraw.drawLine(context,canvas,R.color.black,350,350,1520,1620);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,1520,1620);
        PdfDraw.drawLine(context,canvas,R.color.black,350,1070,1545,1545);
        PdfDraw.drawLine(context,canvas,R.color.black,350,1070,1570,1570);
        PdfDraw.drawLine(context,canvas,R.color.black,350,1070,1595,1595);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Note Operatore:",360,1540);
        writeNoteOrDescription(context,sampleMoe.getNoteAnalisi(),1565,1615);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Operatore",850,1650);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sampleMoe.getAnalistaAnalisi(),950,1650);
        PdfDraw.drawLine(context,canvas,R.color.black,950,1050,1660,1660);
    }
    private static void writeNoteOrDescription(Context context,String noteOrDescription, int heightPrint, int heightMax){
        String stringToPrint = "";
        List<String> splitNote = Arrays.asList(noteOrDescription.split(" "));
        for(String singleSplit:splitNote){
            int charNumber = stringToPrint.length()+singleSplit.length()+350;
            if(charNumber<450)
                if(stringToPrint.length()==0)
                    stringToPrint = singleSplit + " ";
                else
                    stringToPrint = stringToPrint + singleSplit + " ";
            else {
                if(heightPrint>heightMax)
                    break;
                else {
                    PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,stringToPrint,360,heightPrint);
                    heightPrint+=25;
                    stringToPrint="";
                }
            }
        }
        if(heightPrint<heightMax){
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,stringToPrint,360,heightPrint);
        }
    }
}
