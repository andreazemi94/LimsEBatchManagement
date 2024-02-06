package com.example.limsebatchmanagement.FirebaseManagement;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import java.util.*;

public final class FirebaseDatabaseMng {
    private final Map<String,Map<String,String>> documents = new HashMap<>();
    private enum DB_URL{
        WET("https://limse-batch-management-h-de6cb-default-rtdb.firebaseio.com"),
        INORG("https://limse-batch-management-h-b0329-default-rtdb.firebaseio.com"),
        MOE("https://limse-batch-management-hd-default-rtdb.firebaseio.com"),
        MICR("https://limse-batch-management-hd-default-rtdb.firebaseio.com"),
        ANDZEM("https://limse-batch-management-hd-default-rtdb.firebaseio.com");
        String url;
        DB_URL(String url){this.url=url;}
    }
    private DatabaseReference fireBaseDb;
    private final String lab;
    private final String entity;
    private String key;

    public FirebaseDatabaseMng(String lab, String entity){
        switch (lab){
            case "WET": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.WET.url).getReference(); break;
            case "INORG": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.INORG.url).getReference();break;
            case "MOE": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.MOE.url).getReference();break;
            case "MICR": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.MICR.url).getReference();break;
            case "ANDZEM": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.ANDZEM.url).getReference();break;
        }
        if(lab.equals("ANDZEM"))
            this.lab="WET";
        else{
            this.lab=lab;
        }
        this.entity=entity;
    }
    public FirebaseDatabaseMng(String lab, String entity,String key){
        switch (lab){
            case "WET": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.WET.url).getReference(); break;
            case "INORG": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.INORG.url).getReference();break;
            case "MOE": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.MOE.url).getReference();break;
            case "MICR": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.MICR.url).getReference();break;
            case "ANDZEM": this.fireBaseDb = FirebaseDatabase.getInstance(DB_URL.ANDZEM.url).getReference();break;
        }
        if(lab.equals("ANDZEM"))
            this.lab="WET";
        else{
            this.lab=lab;
        }
        this.entity=entity;
        this.key=key;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Map<String,String>> getDocumentsDb(){
        Task<DataSnapshot> tDocuments = fireBaseDb.child(lab).child(entity).get();
        while(!tDocuments.isComplete()) {}
        Map<String,Map<String,Object>> temp  = (Map<String,Map<String,Object>>) ((DataSnapshot)tDocuments.getResult()).getValue();
        if(!Objects.isNull(temp) && !temp.isEmpty()){
            temp.forEach((k,m)->{
                Map<String,String> tempMap = new HashMap<>();
                m.forEach((s,o)->{
                    if(!s.equals("LastDate")){
                        tempMap.put(s,String.valueOf(o));
                        documents.put(k,tempMap);
                    }
                });
            });
        }
        return new ArrayList<>(documents.values());
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean updateDocument(Map<Object,Map<Object,Object>> document){
        Object key = document.keySet().stream().findAny().get();
        Task<Void> tDocuments = fireBaseDb.
                child(lab)
                .child(entity)
                .child(String.valueOf(key))
                .setValue(document.get(key));
        while(!tDocuments.isComplete()) {}
        return tDocuments.isSuccessful();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String,String> getSingleDocument(){
        Task<DataSnapshot> tDocuments = fireBaseDb.child(lab).child(entity).child(key).get();
        while(!tDocuments.isComplete()) {}
        Map<String,Object> temp  = (Map<String,Object>) ((DataSnapshot)tDocuments.getResult()).getValue();
        Map<String,String> map  = new HashMap<>();
        temp.forEach((k,v)->{
            map.put(k,String.valueOf(v));
        });
        return map;
    }
}
