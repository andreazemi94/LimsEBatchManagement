package com.example.limsebatchmanagement.Archiviazione;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatch;
import com.example.limsebatchmanagement.DriveManagement.FolderDrive;
import com.google.api.services.drive.Drive;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DirectoryTree {
    private static final String ID_NULL = "";
    public static String idArchivio,idGiorno,idBatch,idBatchSequence,idInstrument,idEsportazione,idArchivioBacth,idBackup,idDevice;

    public static void createDriveDirectoryTree(Drive drive, EntityBatch batch) throws Exception {
        idArchivio = null;
        idGiorno = null;
        idEsportazione = null;
        idArchivioBacth = null;
        idBatch = null;
        idInstrument = null;
        idBatchSequence = null;
        idArchivio = getId(drive,"Tablet - Archivio","");
        if(Objects.isNull(idArchivio))
            throw new Exception("Errore durante la ricerca dell'id della directory Tablet - Archivio");
        idGiorno = getIdSubFolder(drive,(batch.getForecastDate().substring(6,10)+batch.getForecastDate().substring(3,5)+batch.getForecastDate().substring(0,2)),idArchivio);
        if(Objects.isNull(idGiorno))
            throw new Exception("Errore durante la ricerca dell'id della directory Giorno");
        idEsportazione = getIdSubFolder(drive,"00 - Esportazione",idGiorno);
        if(Objects.isNull(idEsportazione))
            throw new Exception("Errore durante la ricerca dell'id della directory Esportazione");
        idArchivioBacth = getIdSubFolder(drive,"01 - Archivio",idGiorno);
        if(Objects.isNull(idArchivioBacth))
            throw new Exception("Errore durante la ricerca dell'id della directory Archivio");
        idBatch = getIdSubFolder(drive,batch.getBatchName(),idArchivioBacth);
        if(Objects.isNull(idBatch))
            throw new Exception("Errore durante la ricerca dell'id della directory Batch");
        idInstrument = getIdSubFolder(drive,batch.getInstrument(),idEsportazione);
        if(Objects.isNull(idInstrument))
            throw new Exception("Errore durante la ricerca dell'id della directory Instrument");
        idBatchSequence = getIdSubFolder(drive,batch.getBatchSequence(),idInstrument);
        if(Objects.isNull(idBatchSequence))
            throw new Exception("Errore durante la ricerca dell'id della directory Batch Sequence");
    }
    public static void createDriveDirectoryBackup(Drive drive, Context context){
        idArchivio = getId(drive,"Tablet - Archivio","");
        idBackup = getIdSubFolder(drive,"Backup Tablet",idArchivio);
        idDevice = getIdSubFolder(drive, Settings.Global.getString(context.getContentResolver(), "device_name"),idBackup);
    }
    public static String getId (Drive drive, String folderName, String parents) {
        String tempId;
        try {
            tempId = FolderDrive.getId(drive,folderName);
            if(tempId.equals(ID_NULL))
                tempId = FolderDrive.create.apply(drive,FolderDrive.setParameters.apply(folderName,parents));
        } catch (Exception e) {
            tempId = ID_NULL;
        }
        return tempId;
    }
    public static String getIdSubFolder (Drive drive, String folderName, String parents) {
        String tempId;
        try {
            tempId = FolderDrive.getIdSubFolder(drive,folderName,parents);
            if(tempId.equals(ID_NULL))
                tempId = FolderDrive.create.apply(drive,FolderDrive.setParameters.apply(folderName,parents));
        } catch (Exception e) {
            tempId = ID_NULL;
        }
        return tempId;
    }
}
