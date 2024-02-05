package com.example.limsebatchmanagement.CustomerException;

public class VersionException extends Exception{
    public VersionException(){
        super("La versione dell'applicazione è obsoleta, effettuare l'aggiornamento");
    }
}
