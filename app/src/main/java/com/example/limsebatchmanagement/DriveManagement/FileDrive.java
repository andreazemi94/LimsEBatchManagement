package com.example.limsebatchmanagement.DriveManagement;

import android.app.*;
import android.content.Context;
import android.os.*;
import android.webkit.MimeTypeMap;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.Archiviazione.*;
import com.example.limsebatchmanagement.BackupDatabase.CsvBackup;
import com.example.limsebatchmanagement.BatchManagement.BatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatch;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLogDownloadSched;
import com.example.limsebatchmanagement.Enum.Laboratorio;
import com.example.limsebatchmanagement.FirebaseManagement.FirebaseDatabaseMng;
import com.example.limsebatchmanagement.MainActivity;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FileDrive {
    public static final String APPLICATION_CSV = "application/csv",APPLICATION_PDF = "application/pdf";
    private static final String PARENTS_NULL = "", CSV_EXT = "csv", PDF_EXT = "pdf";
    public static String getId (Drive drive,String application,String name,String parents) throws IOException {
        String query = "mimeType='"+ application +"' and trashed=false and name ='" + name + "' ";
        if(!parents.equals(PARENTS_NULL))
            query = query + "and '"+parents+"' in parents";
        FileList searchFile = drive.files().list().setQ(query).execute();
        return (searchFile.getFiles().size()>0)? searchFile.getFiles().get(0).getId():"";
    }
    public static File setParameters(String fileName,String application,String parents){
        File file = new File();
        file.setName(fileName).setMimeType(application);
        if(!parents.equals(PARENTS_NULL))
            file.setParents(Collections.singletonList(parents));
        return file;
    };
    public static void create(Drive drive, java.io.File fileInput, String application,File file) throws IOException {
        FileContent mediaContent = new FileContent(application, fileInput);
        drive.files().create(file,mediaContent).setFields("id").execute();
    };
    public static void update(Drive drive, String application, java.io.File fileInput) throws IOException {
        String query = "mimeType='"+ application +"' and trashed=false and name ='" + fileInput.getName() + "' ";
        FileList searchFile = drive.files().list().setQ(query).execute();
        for (int updateFile=0;updateFile<searchFile.getFiles().size();updateFile++) {
            com.google.api.services.drive.model.File fileExisted = new com.google.api.services.drive.model.File();
            fileExisted.setName(searchFile.getFiles().get(updateFile).getName());
            FileContent mediaContent = new FileContent(application, fileInput);
            drive.files().update(searchFile.getFiles().get(updateFile).getId(),fileExisted, mediaContent).execute();
        }
    }
    public static void upload (List<java.io.File> filesToUpload, Drive drive, Dialog pdUpload, Context context, EntityBatch batch,
                               GoogleSignInAccount account, DatabaseBuilder db, BatchManagement batchManagement){
        Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(()-> {
            try {
                DirectoryTree.createDriveDirectoryTree(drive, batch);
                for(java.io.File file:filesToUpload){
                    String idFileUploaded = "";
                    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                    if (ext.equals(CSV_EXT)){
                        FileCsv.upload(file, drive);
                        idFileUploaded = FileDrive.getId(drive,FileDrive.APPLICATION_CSV,file.getName(),DirectoryTree.idBatch);
                    }
                    else {
                        FilePdf.upload(file, drive);
                        if(!file.getName().contains("LABORATORIO"))
                            idFileUploaded = FileDrive.getId(drive,FileDrive.APPLICATION_PDF,file.getName(),DirectoryTree.idBatch);
                    }
                    file.delete();
                    if(idFileUploaded.equals("") && !file.getName().contains("LABORATORIO"))
                        throw new FileNotFoundException();
                }
                batch.setExportFile(EntityBatch.EXPORTED);
                new FirebaseDatabaseMng(Laboratorio.getLab(account.getEmail()).name(),"Batch").updateDocument(batch.createDocumentFb());
                handler.post(() -> {
                    batch.update(batch,db);
                    pdUpload.dismiss();
                    AlertDialog.Builder confirmForce = DialogCustom.confirmDialog(context,"Generazione File","Gneerazione ed esportazione avvenuta con successo!");
                    confirmForce.setPositiveButton("Ok", (dialog, which) -> batchManagement.finish());
                    confirmForce.show();
                });
            } catch (UnknownHostException netException){
                handler.post(()->{
                    batch.setExportFile(EntityBatch.NOT_EXPORTED);
                    batch.update(batch,db);
                    pdUpload.dismiss();
                    AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context,"Impossibile salvare i dati in Google Drive, connessione a internet assente!");
                    dialogError.show();
                });
            } catch (Exception e) {
                handler.post(() -> {
                    batch.setExportFile(EntityBatch.NOT_EXPORTED);
                    batch.update(batch,db);
                    pdUpload.dismiss();
                    AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context, "Si è verificato un errore nella generazione e salvtaggio del file in Google Drive, ripetere l'operazione!");
                    dialogError.show();
                });
            }
        });
    }
    public static void uploadMassivo (Map<java.io.File,EntityBatch> mapFiles, Drive drive, Context context,
                                      GoogleSignInAccount account, DatabaseBuilder db, MainActivity mainActivity, ProgressDialog pd){
        Handler handler = new Handler();
        Executors.newSingleThreadExecutor().execute(()-> {
            AtomicInteger progressDialog = new AtomicInteger(1);
            mapFiles.forEach((file,batch)->{
                try{
                    DirectoryTree.createDriveDirectoryTree(drive,batch);
                    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                    if (ext.equals(CSV_EXT))
                        FileCsv.upload(file, drive);
                    else
                        FilePdf.upload(file,drive);
                    file.delete();
                    batch.setExportFile(EntityBatch.EXPORTED);
                    new FirebaseDatabaseMng(Laboratorio.getLab(account.getEmail()).name(),"Batch").updateDocument(batch.createDocumentFb());
                    batch.update(batch,db);
                    pd.setProgress(progressDialog.getAndIncrement());
                } catch (Exception e) {
                    handler.post(() -> {
                        pd.dismiss();
                        AlertDialog.Builder dialogError = DialogCustom.createErrorDialog.apply(context, e.getMessage());
                        dialogError.show();
                    });
                }
            });
            handler.post(() -> {
                pd.dismiss();
                mainActivity.onStart();
            });
        });
    }
    public static void uploadBackup (List<java.io.File> filesToUpload, Drive drive, Context context, DatabaseBuilder db){
        Executors.newSingleThreadExecutor().execute(()-> {
                DirectoryTree.createDriveDirectoryBackup(drive,context);
                filesToUpload.forEach(f->{
                    try {
                        CsvBackup.upload(f,drive);
                        f.delete();
                        db.SqlLogDownloadSched().deleteLogFromProcess(f.getName());
                    } catch (UnknownHostException netException) {
                        LocalDateTime now = LocalDateTime.now();
                        EntityLogDownloadSched log = new EntityLogDownloadSched();
                        log.setProcess(f.getName());
                        log.setError(now.toString()+": Caricamento del file di backup (" + f.getName() + ") in Google Drive fallito per mancanza di connessione.");
                        db.SqlLogDownloadSched().insert(log);
                    } catch (Exception e){
                        LocalDateTime now = LocalDateTime.now();
                        EntityLogDownloadSched log = new EntityLogDownloadSched();
                        log.setProcess(f.getName());
                        log.setError(now.toString()+": Caricamento del file di backup (" + f.getName() + ") in Google Drive fallito per il seguente errore: " + e.getMessage());
                        db.SqlLogDownloadSched().insert(log);
                    }
                });
        });
    }
}
