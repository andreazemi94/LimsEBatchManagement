package com.example.limsebatchmanagement.Utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

public class CustomWebViewClient extends android.webkit.WebViewClient {
    private Activity activity;
    public CustomWebViewClient(Activity activity) {
        this.activity = activity;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if(url.contains("docs.google")) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
        return true;
    }
}
