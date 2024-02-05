package com.example.limsebatchmanagement.Utility;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatch;
import com.example.limsebatchmanagement.R;
import java.text.*;
import java.util.*;
import java.util.function.BiFunction;

public class DialogCustom {
    @SuppressLint("UseCompatLoadingForDrawables")
    public static BiFunction<Context, Integer, Dialog> createDialog = (c, i)->{
        Dialog dialog = new Dialog(c);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(i);
        dialog.getWindow().setBackgroundDrawable(c.getDrawable(R.drawable.unlock_batch_background));
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        dialog.getWindow().setAttributes(layoutParams);
        return dialog;
    };
    public static ProgressDialog setOptionProgressDialog(Context context, String title, String message){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setIcon(R.mipmap.ic_launcher_logo_mxns);
        pd.setCancelable(false);
        return pd;
    }
    public static BiFunction<Context,String,AlertDialog.Builder> createErrorDialog = (c,m)->{
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(c);
        errorDialog.setTitle("ERROR");
        errorDialog.setIcon(android.R.drawable.ic_dialog_alert);
        errorDialog.setMessage(m);
        errorDialog.setCancelable(true);
        return errorDialog;
    };
    public static BiFunction<Context, Integer, Dialog> createWebDialog = (c,i)->{
        Dialog webDialog = new Dialog(c);
        webDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webDialog.setCancelable(true);
        webDialog.setCanceledOnTouchOutside(true);
        webDialog.setContentView(R.layout.batch_man_manuale);
        WindowManager.LayoutParams layoutParams = webDialog.getWindow().getAttributes();
        layoutParams.width = (int)(Resources.getSystem().getDisplayMetrics().widthPixels/1.65);
        layoutParams.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        webDialog.getWindow().setAttributes(layoutParams);
        webDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return webDialog;
    };
    public static BiFunction<Context,String,Dialog> createDateTimePicker  = (c,s)->{
        Dialog dialogDateTimePicker = new Dialog(c);
        dialogDateTimePicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDateTimePicker.setCancelable(true);
        dialogDateTimePicker.setContentView(R.layout.batch_man_datetime_picker);
        dialogDateTimePicker.getWindow().setBackgroundDrawable(c.getDrawable(R.drawable.unlock_batch_background));
        WindowManager.LayoutParams layoutParams = dialogDateTimePicker.getWindow().getAttributes();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        dialogDateTimePicker.getWindow().setAttributes(layoutParams);
        DatePicker datePicker = dialogDateTimePicker.findViewById(R.id.date_picker);
        TimePicker timePicker = dialogDateTimePicker.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        try {
            Date dateprep = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY).parse(s);
            Objects.requireNonNull(dateprep).setTime(dateprep.getTime()+60000);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateprep);
            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dialogDateTimePicker;
    };
    public static AlertDialog.Builder confirmDialog (Context context,String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
    public static ProgressDialog createProgressDialogExportMassivo(Context context, List<EntityBatch> completeBatches){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setIcon(R.mipmap.ic_launcher_logo_mxns);
        pd.setMax(completeBatches.size());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Archiviazione massiva");
        pd.setCancelable(false);
        return pd;
    }
    public static ProgressDialog createProgressDialogDownloadFromDrive(Context context){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setIcon(R.mipmap.ic_launcher_logo_mxns);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("Download data from Google Drive");
        pd.setCancelable(false);
        return pd;
    }
}
