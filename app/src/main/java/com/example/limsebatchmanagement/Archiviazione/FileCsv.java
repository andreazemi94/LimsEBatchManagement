package com.example.limsebatchmanagement.Archiviazione;

import android.app.*;
import android.content.Context;
import android.os.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.DriveManagement.FileDrive;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import com.google.api.services.drive.Drive;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FileCsv {
    private static String analysisDate = "",labelID = "";
    private static final String ID_NULL = "";
    public static File create(EntityBatch batch, List<EntityBatchObject> batchObjects, List<EntityResult> results, Context context, Dialog pdUpload){
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),batch.getBatchSequence() + "_EXPORTED.csv");
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(csvFile))){
            dos.write(("AnalysisDate;Analysis;PosSample-LabelSample;Component;Entry" + "\n").getBytes(StandardCharsets.UTF_8));
            for(EntityBatchObject bo:batchObjects){
                List<EntityResult> resultsBo = new ArrayList<>(results);
                resultsBo.removeIf(res->res.getTestNumber()!=bo.getObjectId() || res.getName().equals(EntityResult.COMPONENT_COD_TEMP));
                for(EntityResult res:resultsBo){
                    analysisDate = (res.getEnComponentName().equals(EntityResult.COMPONENT_ANALYSIS_DATE))? inverterAnalysisDate(res.getEntry().replace("/","-")):analysisDate;
                    labelID = (bo.getOrderNumber()>=10)? "0"+bo.getOrderNumber()+"-"+bo.getLabelId():"00"+bo.getOrderNumber()+"-"+bo.getLabelId();
                    dos.write((analysisDate+";"+ res.getAnalysis() +";"+labelID+";"+ res.getName() +";"+ res.getEntry() +"\n").getBytes(StandardCharsets.UTF_8));
                }
            }
        } catch (IOException e) {
            pdUpload.dismiss();
            AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context, e.getMessage());
            dialogError.show();
        }
        return csvFile;
    }
    public static void upload(File csvFile, Drive drive) throws IOException {
        csvUpload(csvFile,drive,DirectoryTree.idBatchSequence);
        csvUpload(csvFile,drive,DirectoryTree.idBatch);
    }
    private static void csvUpload(File csvFile,Drive drive,String parents) throws IOException {
        String idCsvDrive = FileDrive.getId(drive,FileDrive.APPLICATION_CSV,csvFile.getName(),parents);
        if(idCsvDrive.equals(ID_NULL))
            FileDrive.create(drive,csvFile,FileDrive.APPLICATION_CSV,FileDrive.setParameters(csvFile.getName(),FileDrive.APPLICATION_CSV,parents));
        else
            FileDrive.update(drive,FileDrive.APPLICATION_CSV,csvFile);
    }
    private static String inverterAnalysisDate (String origAnalysisDate){
        String date = origAnalysisDate.substring(0,2);
        String month = origAnalysisDate.substring(3,5);
        String year = origAnalysisDate.substring(6,10);
        String hours = origAnalysisDate.substring(10);
        return year + "-" + month + "-" + date +hours;
    }
}
