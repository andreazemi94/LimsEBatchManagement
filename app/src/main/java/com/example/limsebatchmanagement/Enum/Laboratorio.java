package com.example.limsebatchmanagement.Enum;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public enum Laboratorio {
    WET("labwet.to@mxns.com"),
    INORG("labinorg.to@mxns.com"),
    MOE("labamianto.to@mxns.com"),
    MICR("labbio.to@mxns.com"),
    ANDZEM("andrea.zemignani@mxns.com");
    String email;
    Laboratorio(String email){
        this.email=email;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Laboratorio getLab(String email){
        return Arrays.stream(Laboratorio.values())
                .filter(lab->lab.email.equals(email))
                .findAny()
                .orElse(null);
    }
}
