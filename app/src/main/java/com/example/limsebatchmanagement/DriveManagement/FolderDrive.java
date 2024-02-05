package com.example.limsebatchmanagement.DriveManagement;

import com.example.limsebatchmanagement.CustomInterface.BiFunctionException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.IOException;
import java.util.Collections;
import java.util.function.BiFunction;

public class FolderDrive {
    private static final String APPLICATION = "application/vnd.google-apps.folder";
    private static final String PARENTS_NULL = "";
    public static String getId (Drive drive,String name) throws IOException {
        String query = "mimeType='"+ APPLICATION +"' and trashed=false and name ='" + name + "' ";
        FileList searchFile = drive.files().list().setQ(query).execute();
        return (searchFile.getFiles().size()>0)? searchFile.getFiles().get(0).getId():"";
    }
    public static String getIdSubFolder (Drive drive,String name,String parents) throws IOException {
        String query = "mimeType='"+ APPLICATION +"' and trashed=false and name ='" + name + "' and '"+parents+"' in parents";
        FileList searchFile = drive.files().list().setQ(query).execute();
        return (searchFile.getFiles().size()>0)? searchFile.getFiles().get(0).getId():"";
    }
    public static BiFunction<String,String,File> setParameters = (name, parents)->{
        File folder = new File();
        folder.setName(name).setMimeType(APPLICATION);
        if(!parents.equals(PARENTS_NULL))
            folder.setParents(Collections.singletonList(parents));
        return folder;
    };
    public static BiFunctionException<Drive,File,String> create = (drive, file) -> {
        File driveFolder;
        driveFolder = drive.files().create(file).setFields("id").execute();
        return driveFolder.getId();
    };
}
