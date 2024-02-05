package com.example.limsebatchmanagement.CustomerException;

public class DownloadManualLabException extends Exception{
    public DownloadManualLabException(){
        super("Impossibile scaricare il manuale del laboratorio, verificarne la presenza in Google Drive!");
    }
}
