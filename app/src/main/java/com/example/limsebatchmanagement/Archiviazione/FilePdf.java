package com.example.limsebatchmanagement.Archiviazione;

import android.app.*;
import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.*;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.DriveManagement.FileDrive;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.*;
import com.google.api.services.drive.Drive;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FilePdf {
    private static File pdfFile;
    private static PdfDocument pdfDocument;
    private static PdfDocument.Page page;
    private static Canvas canvas;
    private static int pageNumber = 1;
    private static final String ID_NULL = "";
    @RequiresApi(api = Build.VERSION_CODES.R)
    public static File create(Context context, Dialog pdUpload, boolean pdfUff, EntityBatch batch, List<EntityBatchObject> batchObjects, List<EntityResult> results,
                              List<EntitySample> samples, List<EntityAnalysisComponent> analysisComponents, Map<String,String> mapListEntries){
        String lab = (pdfUff)? "":" LABORATORIO";
        pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),batch.getBatchName() + lab + ".pdf");
        pdfDocument = new PdfDocument();
        try {
            page = PdfDraw.createPage(pdfDocument,pageNumber);
            canvas = page.getCanvas();
            printLogoMerieux(context);
            printTitle(context,batch,pdfUff);
            printInfoBatch(context,batch,pdfUff);
            printBatchDetails(batchObjects,results,analysisComponents,samples,mapListEntries,context);
            printNote(context,batch, pdfUff);
            pdfDocument.finishPage(page);
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

    private static void printLogoMerieux(Context context){
        PdfDraw.drawBitMap(context,canvas, R.drawable.logo_merieux_color,350,150,50,50);
    }
    private static void printTitle(Context context, EntityBatch batch, boolean pdfUff){
        int coordinataXCentrale = 460-batch.getBatchName().length();
        if(!pdfUff)
            PdfDraw.drawText(context,canvas,35,R.color.red,Typeface.BOLD,"USO INTERNO LABORATORIO",320,250);
        PdfDraw.drawText(context,canvas,28,R.color.black, Typeface.BOLD,batch.getBatchName(),coordinataXCentrale,300);
        String deviceName = Settings.Global.getString(context.getContentResolver(), "device_name");
        PdfDraw.drawText(context,canvas,14,R.color.black, Typeface.NORMAL,"Mod. 3202/SQ rev. 0",950,50);
        PdfDraw.drawText(context,canvas,14,R.color.black, Typeface.BOLD,deviceName,1000,100);
    }
    private static void printInfoBatch(Context context,EntityBatch batch,boolean pdfUff){
        PdfDraw.drawText(context,canvas,20,R.color.black,Typeface.BOLD,"Informazioni generiche del batch:",50,330);
        PdfDraw.drawRect(context,canvas,R.color.grey,50,340,1080,380);
        PdfDraw.drawRect(context,canvas,R.color.light_grey,50,381,1080,421);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Instrument",60,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getInstrument(),60,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"N°. Test",173,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getTotObj(),173,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Fcst Start",276,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getForecastDate(),276,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Fcst End",439,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getForecastFinDate(),439,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Due Date",552,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getMinDueDate(),552,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Fcst User",665,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getForecastUser(),665,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Q. Lvl",778,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getBatchQualityLevel(),778,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Pri",930,365);
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,batch.getBatchDueDateTypeValue(),930,406);
        PdfDraw.drawText(context,canvas,18,R.color.white,Typeface.BOLD,"Phase",1004,365);
        String phase = Objects.isNull(batch.getBatchPhase())? "":batch.getBatchPhase();
        if(pdfUff)
            phase = "";
        PdfDraw.drawText(context,canvas,18,R.color.text_grey,Typeface.BOLD,phase,1004,406);
    }
    private static void printBatchDetails(List<EntityBatchObject> batchObjects, List<EntityResult> results,List<EntityAnalysisComponent> analysisComponents,
                                          List<EntitySample> samples,Map<String,String> mapListEntries, Context context){
        AtomicInteger y = new AtomicInteger(480);
        verifyPage(analysisComponents,y,context);
        PdfDraw.drawText(context, canvas,20,R.color.black,Typeface.BOLD,"Dettaglio batch:",50,460);
        batchObjects.forEach(bo->{
            verifyPage(analysisComponents,y,context);
            printBatchObject(bo,samples,y,context);
            analysisComponents.forEach(ac->{
                printResult(ac,bo,results,analysisComponents,mapListEntries,context,y);
            });
            y.addAndGet(20);
        });
    }
    private static void printBatchObject(EntityBatchObject bo, List<EntitySample> samples, AtomicInteger y, Context context){
        String sampleName = ((bo.getOrderNumber()<9)? "00":"0") + bo.getOrderNumber() + " - " +
                EntitySample.getSampleFromSampleNumber.apply(samples,bo.getSampleNUmber()).getTextId();
        PdfDraw.drawRect(context, canvas,R.color.grey,50, y.get(), 1080,y.get()+40);
        PdfDraw.drawText(context, canvas,18,R.color.white,Typeface.BOLD,sampleName,60,y.get()+25);
        y.addAndGet(40);
    }
    private static void printResult(EntityAnalysisComponent ac,EntityBatchObject bo,List<EntityResult> results,List<EntityAnalysisComponent> analysisComponents,
                                    Map<String,String> mapListEntries,Context context,AtomicInteger y){
        String acToPrint = ac.getAnalysis() + " - " + ac.getEnComponentName();
        List<EntityResult> resultsToPrint = new ArrayList<>(results);
        resultsToPrint.removeIf(res->!res.getDisplayed().equals(EntityResult.DISPLAYED));
        resultsToPrint.removeIf(res->!res.getAnalysis().equals(ac.getAnalysis()));
        resultsToPrint.removeIf(res->!res.getEnComponentName().equals(ac.getEnComponentName()));
        resultsToPrint.removeIf(res->res.getSampleNumber()!=bo.getSampleNUmber());
        resultsToPrint.removeIf(res->res.getName().equals(EntityResult.COMPONENT_COD_TEMP));
        if(resultsToPrint.size()>0){
            if(analysisComponents.indexOf(ac)%2==0)
                PdfDraw.drawRect(context,canvas,R.color.light_grey,50,y.get(),1080,y.get()+40);
            else
                PdfDraw.drawRect(context,canvas,R.color.white,50,y.get(),1080,y.get()+40);
            PdfDraw.drawText(context,canvas,16,R.color.text_grey,Typeface.BOLD,acToPrint,60,y.get()+20);
            String entry = resultsToPrint.get(0).getEntry();
            if(resultsToPrint.get(0).getResultType().equals(EntityResult.COMPONENT_SPINNER))
                entry = mapListEntries.get(resultsToPrint.get(0).getEntry());
            PdfDraw.drawText(context,canvas,16,R.color.text_grey,Typeface.BOLD,entry,700,y.get()+20);
            y.addAndGet(40);
        }
    }
    private static void verifyPage(List<EntityAnalysisComponent> analysisComponents, AtomicInteger y, Context context){
        if(PdfDraw.checkChangePage(analysisComponents.size() * 40,y)){
            page = PdfDraw.setNewPage(pdfDocument,page,pageNumber);
            canvas = page.getCanvas();
            y.set(290);
            PdfDraw.drawBitMap(context,canvas, R.drawable.logo_merieux_color,350,150,50,50);
            pageNumber++;
        }
    }
    private static void printNote(Context context,EntityBatch batch,boolean pdfUff){
        View view = LayoutInflater.from(context).inflate(R.layout.officialpdf, null);
        TextView tvNoteUff = view.findViewById(R.id.officialpdf_note);
        view.measure(View.MeasureSpec.makeMeasureSpec(1130, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(1730, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, 1130, 1730);
        if(pdfUff)
            tvNoteUff.setText(batch.getNoteUfficiali());
        else
            tvNoteUff.setText(batch.getNoteLab());
        page = PdfDraw.setNewPage(pdfDocument,page,++pageNumber);
        canvas = page.getCanvas();
        view.draw(canvas);
    }
    public static void upload(File pdfFile, Drive drive) throws IOException {
        if(pdfFile.getName().contains("LABORATORIO")) {
            String idNote = DirectoryTree.getId(drive,"Note",DirectoryTree.idArchivio);
            pdfUpload(pdfFile, drive, idNote);
        }
        else
            pdfUpload(pdfFile,drive,DirectoryTree.idBatch);
    }
    private static void pdfUpload(File pdfFile, Drive drive, String parents) throws IOException {
        String idPdfDrive = FileDrive.getId(drive,FileDrive.APPLICATION_PDF,pdfFile.getName(),parents);
        if(idPdfDrive.equals(ID_NULL))
            FileDrive.create(drive,pdfFile,FileDrive.APPLICATION_PDF,FileDrive.setParameters(pdfFile.getName(),FileDrive.APPLICATION_PDF,parents));
        else
            FileDrive.update(drive,FileDrive.APPLICATION_PDF,pdfFile);
    }
    private static List<String> setNoteLabToPrint (final String noteOrig,List<EntitySample> samples){
       return Arrays.asList(noteOrig.split("\n"));
    }
}
