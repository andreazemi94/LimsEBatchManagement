package com.example.limsebatchmanagement.DatabaseFirebase.Download.Manual;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.os.*;
import android.webkit.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.CustomerException.DownloadManualLabException;
import com.example.limsebatchmanagement.DriveManagement.*;
import com.example.limsebatchmanagement.*;
import com.example.limsebatchmanagement.Utility.*;
import com.google.api.services.drive.Drive;

@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("SetJavaScriptEnabled")
public class DownloadLabManual {
    private static final String MANUAL_LAB_NAME = "Manuale Lab";
    public static final String ID_EMPTY = "";
    public static Runnable download (Activity activity,Context context, Drive drive,Dialog dialog){
        Handler handler = new Handler();
        return () -> {
            try {
                String idFileLabManual = GoogleDoc.getId.apply(drive,MANUAL_LAB_NAME);
                if (idFileLabManual.equals(ID_EMPTY))
                    throw new DownloadManualLabException();
                handler.post(()->{
                    dialog.dismiss();
                    Dialog dialogManuale = DialogCustom.createWebDialog.apply(context,R.layout.batch_man_manuale);
                    WebView webViewManuale = dialogManuale.findViewById(R.id.batch_man_manuale_webview);
                    WebSettings webSettings = webViewManuale.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    CustomWebViewClient webViewClient = new CustomWebViewClient(activity);
                    webViewManuale.setWebViewClient(webViewClient);
                    webViewManuale.loadUrl("https://docs.google.com/document/d/"+idFileLabManual+"/view");
                    dialogManuale.show();
                });
            } catch (Exception e) {
                handler.post(()->{
                    dialog.dismiss();
                    DialogCustom.createErrorDialog.apply(context,e.getMessage()).show();
                });
            }
        };
    }
}

