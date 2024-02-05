package com.example.limsebatchmanagement.Enum;

public enum CategoryFibers {
    AMIANTO,FAV,LANE,FCR;
    public static CategoryFibers getCategory(String tipoFibra){
        switch (tipoFibra){
            case "Lane Minerali":
                return LANE;
            case "Fibre Ceramiche Refrattarie":
                return FCR;
            default:
                return AMIANTO;
        }
    }
}
