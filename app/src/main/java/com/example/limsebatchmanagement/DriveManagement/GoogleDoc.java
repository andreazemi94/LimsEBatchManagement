package com.example.limsebatchmanagement.DriveManagement;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.CustomInterface.BiFunctionException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class GoogleDoc {
    private static final String APPLICATION = "vnd.google-apps.document";
    public static BiFunctionException<Drive,String,String> getId = (driveService, fileName) -> {
        String query = "mimeType='application/"+ APPLICATION +"' and trashed=false and name ='" + fileName + "'";
        FileList searchFile = driveService.files().list().setQ(query).execute();
        return (searchFile.getFiles().size()>0)? searchFile.getFiles().get(0).getId():"";
    };
}
