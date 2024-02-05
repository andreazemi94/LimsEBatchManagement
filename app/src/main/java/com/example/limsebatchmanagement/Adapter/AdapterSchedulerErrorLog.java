package com.example.limsebatchmanagement.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLogDownloadSched;
import com.example.limsebatchmanagement.R;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AdapterSchedulerErrorLog extends BaseAdapter {
    private final Context context;
    private final List<EntityLogDownloadSched> logs;

    private AdapterSchedulerErrorLog(Context context,List<EntityLogDownloadSched> logs){
            this.context = context;
            this.logs = logs;
        }
    @Override
    public int getCount() {
        return logs.size(); }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public static AdapterSchedulerErrorLog getInstance (Context context,List<EntityLogDownloadSched> logs) {return new AdapterSchedulerErrorLog(context,logs);}
    @SuppressLint({"UseCompatLoadingForColorStateLists", "ViewHolder", "UseCompatLoadingForDrawables"})
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_scheduler_error_log_item, parent, false);
        TextView tvProcessName = convertView.findViewById(R.id.scheduler_error_log_process);
        TextView tvMessage = convertView.findViewById(R.id.scheduler_error_log_message);
        tvProcessName.setText(logs.get(position).getProcess());
        tvMessage.setText(logs.get(position).getError());
        return convertView;
    }
}
