package com.example.limsebatchmanagement.DatabaseFirebase.Download.Manual;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.CustomerException.DownloadManualLabException;
import com.example.limsebatchmanagement.DriveManagement.GoogleDoc;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.CustomWebViewClient;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import com.google.api.services.drive.Drive;

@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("SetJavaScriptEnabled")
public class DownloadAppManual {
    private static final String MANUAL_APP_NAME = "Manuale App";
    public static final String ID_EMPTY = "";
    public static Runnable download (Activity activity,Context context, Drive drive,Dialog dialog){
        Handler handler = new Handler();
        return () -> {
            try {
                String idFileAppManual = GoogleDoc.getId.apply(drive,MANUAL_APP_NAME);
                if (idFileAppManual.equals(ID_EMPTY))
                    throw new DownloadManualLabException();
                handler.post(()->{
                    dialog.dismiss();
                    Dialog dialogManuale = DialogCustom.createWebDialog.apply(context,R.layout.batch_man_manuale);
                    WebView webViewManuale = dialogManuale.findViewById(R.id.batch_man_manuale_webview);
                    WebSettings webSettings = webViewManuale.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    CustomWebViewClient webViewClient = new CustomWebViewClient(activity);
                    webViewManuale.setWebViewClient(webViewClient);
                    webViewManuale.loadUrl("https://docs.google.com/document/d/"+idFileAppManual+"/view");
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

