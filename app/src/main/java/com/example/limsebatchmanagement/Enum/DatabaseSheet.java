package com.example.limsebatchmanagement.Enum;

public enum DatabaseSheet {
    GS_GENERAL("General"),
    GS_BATCH("Batch"),
    GS_BATCH_OBJECTS("Batch Objects"),
    GS_SAMPLE("Sample"),
    GS_TEST("Test"),
    GS_RESULT("Result"),
    GS_LIST_ENTRY("ListEntry"),
    GS_LIMIT("Limiti"),
    GS_CUSTOMER("Clienti Attenzionati"),
    GS_SAMPLE_MOE("SampleMoe"),
    GS_FIBRE("Fibre"),
    GS_MODULI("Moduli"),
    GS_LIST_ENTRY_MOE("ListEntryMoe");
    private final String name;
    DatabaseSheet(String name){
        this.name=name;
    }
    public String getSheetName(){return name;}
}
