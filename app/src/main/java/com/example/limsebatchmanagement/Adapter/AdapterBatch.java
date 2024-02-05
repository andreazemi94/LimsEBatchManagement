package com.example.limsebatchmanagement.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityBatch;
import com.example.limsebatchmanagement.R;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdapterBatch extends BaseAdapter {
    private final Context context;
    private final List<EntityBatch> batches;
    private AdapterBatch(Context context, List<EntityBatch>batches){
            this.context = context;
            this.batches = batches;
        }
    @Override
    public int getCount() {
        return batches.size(); }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public static AdapterBatch getInstance (Context context, List<EntityBatch>batches) {return new AdapterBatch(context,batches);}
    public EntityBatch getBatch(int position) {return batches.get(position);}

    @SuppressLint({"UseCompatLoadingForColorStateLists", "ViewHolder", "UseCompatLoadingForDrawables"})
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_lista_batch, parent, false);
        ConstraintLayout layoutListaBatch = convertView.findViewById(R.id.list_batch_layout);
        TextView instrument = convertView.findViewById(R.id.instrument);
        TextView batch = convertView.findViewById(R.id.batch);
        TextView tests = convertView.findViewById(R.id.tests);
        TextView fcstStart = convertView.findViewById(R.id.fcst_start);
        TextView fcstEnd = convertView.findViewById(R.id.fcst_end);
        TextView labDueDate = convertView.findViewById(R.id.lab_due_date);
        TextView fcstUser = convertView.findViewById(R.id.fcst_user);
        TextView qualLvl = convertView.findViewById(R.id.qual_lvl);
        TextView pri = convertView.findViewById(R.id.pri);
        TextView phase = convertView.findViewById(R.id.phase);
        ImageView imageStatus = convertView.findViewById(R.id.status);
        ImageView imageExportFile = convertView.findViewById(R.id.exp_file);
        ImageView imageLocStatus = convertView.findViewById(R.id.loc_status);
        layoutListaBatch.setBackground(context.getDrawable(R.drawable.ic_lista_pari_selector));
        if(position%2==0)
            layoutListaBatch.setBackground(context.getDrawable(R.drawable.ic_lista_dispari_selector));
        EntityBatch b = batches.get(position);
        batch.setText(b.getBatchName());
        instrument.setText(b.getInstrument());
        tests.setText(b.getTotObj());
        fcstStart.setText(b.getForecastDate());
        fcstEnd.setText(b.getForecastFinDate());
        labDueDate.setText(b.getMinDueDate());
        fcstUser.setText(b.getForecastUser());
        qualLvl.setText(b.getBatchQualityLevel());
        pri.setText(b.getBatchDueDateTypeValue());
        phase.setText(b.getBatchPhase());
        switch (b.getStatus()){
            case EntityBatch.NEW:
                imageStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.grey));
                break;
            case EntityBatch.IN_PROGRESS:
                imageStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.orange));
                break;
            case EntityBatch.COMPLETE:
                imageStatus.setBackground(context.getDrawable(R.drawable.ic_ampolla_piena));
                imageStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
                break;
        }
        switch (b.getLocStatus()){
            case EntityBatch.NEW:
                imageLocStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.grey));
                break;
            case EntityBatch.IN_PROGRESS:
                imageLocStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.orange));
                break;
            case EntityBatch.COMPLETE:
                imageLocStatus.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
                break;
        }
        imageExportFile.setBackgroundTintList(context.getResources().getColorStateList(R.color.grey));
        if(b.getExportFile().equals(EntityBatch.EXPORTED))
            imageExportFile.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));

        return convertView;
    }
}
