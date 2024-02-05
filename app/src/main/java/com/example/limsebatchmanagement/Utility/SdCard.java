package com.example.limsebatchmanagement.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import java.util.function.Consumer;
@RequiresApi(api = Build.VERSION_CODES.M)
public class SdCard {
    private static final int WRITE_EXTERNAL_STORAGE = 100;
    public static Consumer<Activity> writePermissionRequest = activity -> {
        int writePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if( writePermission != PackageManager.PERMISSION_GRANTED)
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
    };
}
