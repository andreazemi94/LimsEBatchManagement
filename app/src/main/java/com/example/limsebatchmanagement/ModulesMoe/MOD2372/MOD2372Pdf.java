package com.example.limsebatchmanagement.ModulesMoe.MOD2372;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.Enum.CategoryFibers;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MOD2372Pdf {
    private static File pdfFile;
    private static PdfDocument pdfDocument;
    private static PdfDocument.Page page;
    private static Canvas canvas;
    private static int pageNumber = 1;
    public static File create(Context context, Dialog pdUpload, String batchName, List<EntityBatchObject> batchObjects,
                              DatabaseBuilder db){
        try {
            pageNumber = 1;
            pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),batchName + " - MOD2372 - Conteggio Fibre.pdf");
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
        IntStream.rangeClosed(0,fibers.size()/101).forEach(pageNumInt->{
            page = PdfDraw.createPage(pdfDocument,pageNumber);
            canvas = page.getCanvas();
            setIntestazione(context,sampleMoe,textId);
            setTabellaFibre(context,fibers,pageNumInt);
            setTabellaContatoreCampi(context,sampleMoe);
            setTotaliFibre(context,fibers);
            setNoteOperatore(context,sampleMoe);
            pdfDocument.finishPage(page);
            pageNumber++;
        });
    }
    private static void setIntestazione(Context context,EntitySampleMoe sample,String textId){
        PdfDraw.drawBitMap(context,canvas, R.drawable.logo_merieux_color,270,100,50,50);
        PdfDraw.drawText(context,canvas,32,R.color.black, Typeface.BOLD,"Conteggio fibre",450,70);
        PdfDraw.drawText(context,canvas,32,R.color.black,Typeface.BOLD,"inorganiche in SEM",440,110);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"Mod. 2372/SQ Rev.2",900,100);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,170,170);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1680,1680);
        PdfDraw.drawLine(context,canvas,R.color.black,50,50,170,1680);
        PdfDraw.drawLine(context,canvas,R.color.black,1080,1080,170,1680);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"ID Campione",60,210);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, textId,65,260);
        PdfDraw.drawLine(context,canvas,R.color.black,60,320,230,230);
        PdfDraw.drawLine(context,canvas,R.color.black,60,320,270,270);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,230,270);
        PdfDraw.drawLine(context,canvas,R.color.black,320,320,230,270);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Posizione Stub",340,210);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getStub(),350,260);
        PdfDraw.drawLine(context,canvas,R.color.black,340,570,230,230);
        PdfDraw.drawLine(context,canvas,R.color.black,340,570,270,270);
        PdfDraw.drawLine(context,canvas,R.color.black,340,340,230,270);
        PdfDraw.drawLine(context,canvas,R.color.black,570,570,230,270);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Data",590,210);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getDataAnalisi(),600,260);
        PdfDraw.drawLine(context,canvas,R.color.black,590,820,230,230);
        PdfDraw.drawLine(context,canvas,R.color.black,590,820,270,270);
        PdfDraw.drawLine(context,canvas,R.color.black,590,590,230,270);
        PdfDraw.drawLine(context,canvas,R.color.black,820,820,230,270);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Matrice",840,210);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getMatrice(),850,260);
        PdfDraw.drawLine(context,canvas,R.color.black,840,1070,230,230);
        PdfDraw.drawLine(context,canvas,R.color.black,840,1070,270,270);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,230,270);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,230,270);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Strumento",60,300);
        if(sample.getStrumento().equals("SEM01")) {
            PdfDraw.drawText(context, canvas, 16, R.color.black, Typeface.NORMAL, "SEM ZEISS EVO MA 10 - EDS", 70, 337);
            PdfDraw.drawText(context, canvas, 16, R.color.black, Typeface.NORMAL, "Bruker QUANTAX 200", 70, 355);
        } else if (sample.getStrumento().equals("SEM02")){
            PdfDraw.drawText(context, canvas, 16, R.color.black, Typeface.NORMAL, "SEM ZEISS EVO 10 - EDS", 70, 337);
            PdfDraw.drawText(context, canvas, 16, R.color.black, Typeface.NORMAL, "Bruker QUANTAX 200", 70, 355);
        }
        PdfDraw.drawLine(context,canvas,R.color.black,60,320,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,60,320,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,60,60,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,320,320,320,360);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Mag.",340,300);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getIngrandimenti(),350,350);
        PdfDraw.drawLine(context,canvas,R.color.black,340,570,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,340,570,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,340,340,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,570,570,320,360);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"WD",590,300);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,"10",600,350);
        PdfDraw.drawLine(context,canvas,R.color.black,590,820,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,590,820,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,590,590,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,820,820,320,360);
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"N° campi osservati",840,300);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL, sample.getNumCampi(),850,350);
        PdfDraw.drawLine(context,canvas,R.color.black,840,1070,320,320);
        PdfDraw.drawLine(context,canvas,R.color.black,840,1070,360,360);
        PdfDraw.drawLine(context,canvas,R.color.black,840,840,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,1070,1070,320,360);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,400,400);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1334,1334);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Conteggio",500,430);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,450,450);
        IntStream.range(0,25).forEach(i->PdfDraw.drawLine(context,canvas,R.color.black,50,1080,484+(i*34),484+(i*34)));
        AtomicInteger xToPrint = new AtomicInteger(50);
        IntStream.range(1,5).forEach(i->{
            xToPrint.addAndGet(35);
            PdfDraw.drawLine(context,canvas,R.color.black, xToPrint.get(), xToPrint.get(),450,1334);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"N°", xToPrint.get() -25,475);
            xToPrint.addAndGet(120);
            PdfDraw.drawLine(context,canvas,R.color.black, xToPrint.get(),xToPrint.get(),450, 1334);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Tipo Fibra", xToPrint.get() -100,475);
            xToPrint.addAndGet(51);
            PdfDraw.drawLine(context,canvas,R.color.black, xToPrint.get(), xToPrint.get(),450,1334);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Lungh.", xToPrint.get() -50,475);
            xToPrint.addAndGet(51);
            if(i!=4){
                PdfDraw.drawLine(context,canvas,R.color.black, xToPrint.get(),xToPrint.get(),450, 1334);
                PdfDraw.drawLine(context,canvas,R.color.black, xToPrint.get() +1,xToPrint.get() +1,450, 1334);
            }
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Diam.", xToPrint.get() -45,475);
        });
    }
    private static void setTabellaFibre(Context context,List<EntityFibre> fibers,int pageNumber){
        final int xStart = 55;
        final int yStart = 508;
        IntStream.range(0,4).forEach(i-> IntStream.range(0,25).forEach(j->{
            int x = xStart+(i*257);
            int y = yStart+(j*34);
            int idFibra = (i*25)+j+1+(100*pageNumber);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(idFibra),x,y);
            fibers.stream()
                    .filter(f->f.getIdFibra()==idFibra)
                    .findFirst()
                    .ifPresent(f->{
                        int endSub = Math.min(f.getTipoFibra().length(), 15);
                        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,f.getTipoFibra().substring(0,endSub),x+32,y);
                        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,f.getLunghezza(),x+155,y);
                        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,f.getDiametro(),x+207,y);
                    });
        }));
    }
    private static void setTabellaContatoreCampi(Context context,EntitySampleMoe sample) {
        int x = 55;
        int y = 1375;
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1345,1345);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1500,1500);
        PdfDraw.drawText(context,canvas,22,R.color.black,Typeface.BOLD,"Contatore Campi",475,1370);
        PdfDraw.drawLine(context,canvas,R.color.black,50,1080,1375,1375);
        IntStream.range(1,5).forEach(i->PdfDraw.drawLine(context,canvas,R.color.black,50,1080,y+(25*i),y+(25*i)));
        IntStream.range(1,20).forEach(i->PdfDraw.drawLine(context,canvas,R.color.black,x+(51*i),x+(51*i),1375,1500));
        IntStream.range(1,21).forEach(i-> IntStream.range(1,6).forEach(j->{
            int contatoreCampi =(j==1)? 4*i:(4*(20*(j-1)))+4*i;
            int xc = (x-45)+(i*51);
            int yc = (y-5)+(j*25);
            if(sample.getContatoreCampi().equals(String.valueOf(contatoreCampi)))
                setSelectedContatoreCampi(context,contatoreCampi,xc,yc);
            PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(contatoreCampi),xc,yc);
        }));
    }
    private static void setSelectedContatoreCampi(Context context, int contatoreCampi, int x, int y) {
        switch (contatoreCampi){
            case 4: case 80: case 84: case 160: case 164: case 240: case 244: case 320: case 324: case 400:
                PdfDraw.drawRect(context,canvas,R.color.spinner_blue,x-5,y-19,x+50,y+4);
                break;
            case 8: case 12: case 88: case 92: case 168: case 172: case 248: case 252: case 328: case 332:
                PdfDraw.drawRect(context,canvas,R.color.spinner_blue,x,y-19,x+48,y+4);
                break;
            default:
                PdfDraw.drawRect(context,canvas,R.color.spinner_blue,x-5,y-19,x+45,y+4);
                break;
        }
    }
    private static void setTotaliFibre(Context context,List<EntityFibre> fibers) {
        PdfDraw.drawLine(context,canvas,R.color.black,50,270,1520,1520);
        PdfDraw.drawLine(context,canvas,R.color.black,50,270,1550,1550);
        PdfDraw.drawLine(context,canvas,R.color.black,50,270,1595,1595);
        PdfDraw.drawLine(context,canvas,R.color.black,50,270,1630,1630);
        PdfDraw.drawLine(context,canvas,R.color.black,50,270,1670,1670);
        PdfDraw.drawLine(context,canvas,R.color.black,270,270,1520,1670);
        PdfDraw.drawLine(context,canvas,R.color.black,210,210,1520,1670);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Fibre Amianto",60,1540);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Fibre Artificiali",60,1570);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Vetrose",60,1590);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Lane Minerali",60,1615);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Fibre Ceramiche",60,1645);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.BOLD,"Refrattarie",60,1665);
        long asbestosFiber = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.AMIANTO)).count();
        long vitreousFiber  = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.LANE) || CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.FCR)).count();
        long mineralWool = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.LANE)).count();
        long ceramicFiber = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.FCR)).count();
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(asbestosFiber),220,1540);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(vitreousFiber),220,1570);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(mineralWool),220,1615);
        PdfDraw.drawText(context,canvas,16,R.color.black,Typeface.NORMAL,String.valueOf(ceramicFiber),220,1650);
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
