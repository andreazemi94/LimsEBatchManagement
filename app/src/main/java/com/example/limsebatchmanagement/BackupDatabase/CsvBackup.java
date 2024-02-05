package com.example.limsebatchmanagement.BackupDatabase;

import android.content.Context;
import android.database.Cursor;
import android.os.*;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.Archiviazione.DirectoryTree;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DriveManagement.FileDrive;
import com.google.api.services.drive.Drive;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CsvBackup {
    private static String analysisDate = "",labelID = "";
    private static final String ID_NULL = "";
    public static File createSingleBackup(String entityName, DatabaseBuilder db,Context context){
        String deviceName = Settings.Global.getString(context.getContentResolver(), "device_name");
        Cursor c = db.query("SELECT * FROM " + entityName, null);
        StringBuilder firstRow = new StringBuilder();
        StringBuilder row = new StringBuilder();
        Arrays.asList(c.getColumnNames()).forEach(s-> firstRow.append(s).append(";"));
        File csvFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),deviceName + "_" + entityName + "_BACKUP.csv");
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(csvFile))){
            dos.write((firstRow.append("\n")).toString().getBytes(StandardCharsets.UTF_8));
            while(c.moveToNext()){
                row.delete(0,row.length());
                IntStream.range(0,c.getColumnCount()).forEach(i->row.append(c.getString(i)).append(";"));
                dos.write((row.append("\n")).toString().getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFile;
    }
    public static void upload(File csvFile, Drive drive) throws IOException {
        csvUpload(csvFile,drive, DirectoryTree.idDevice);
    }
    private static void csvUpload(File csvFile, Drive drive, String parents) throws IOException {
        String idCsvDrive = FileDrive.getId(drive,FileDrive.APPLICATION_CSV,csvFile.getName(),parents);
        if(idCsvDrive.equals(ID_NULL))
            FileDrive.create(drive,csvFile,FileDrive.APPLICATION_CSV,FileDrive.setParameters(csvFile.getName(),FileDrive.APPLICATION_CSV,parents));
        else
            FileDrive.update(drive,FileDrive.APPLICATION_CSV,csvFile);
    }
}
