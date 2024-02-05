package com.example.limsebatchmanagement.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityLimit;
import com.example.limsebatchmanagement.R;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AdapterLimits extends BaseAdapter {
    private Context context;
    private List<EntityLimit> limits;
    private ConstraintLayout layout;
    private TextView tvMatrix,tvComponent,tvDescComponent,tvUm,tvValMin,tvValMax;
    public AdapterLimits(Context context, List<EntityLimit>limits){
            this.context = context;
            this.limits = limits;
        }
    @Override
    public int getCount() {
        return limits.size(); }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint({"UseCompatLoadingForColorStateLists", "ViewHolder", "UseCompatLoadingForDrawables"})
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.batch_man_limiti_lista_lim, parent, false);
        layout = convertView.findViewById(R.id.list_limiti_layout);
        tvMatrix = convertView.findViewById(R.id.limiti_matrix);
        tvComponent = convertView.findViewById(R.id.limiti_component);
        tvDescComponent = convertView.findViewById(R.id.limiti_desc_component);
        tvUm = convertView.findViewById(R.id.limiti_unita_mis);
        tvValMin = convertView.findViewById(R.id.limiti_val_min);
        tvValMax = convertView.findViewById(R.id.limiti_val_max);
        layout.setBackground(context.getDrawable(R.drawable.ic_lista_pari_selector));
        if(position%2!=0)
            layout.setBackground(context.getDrawable(R.drawable.ic_lista_dispari_selector));
        tvMatrix.setText(limits.get(position).getMatrice());
        tvComponent.setText(limits.get(position).getComponent());
        tvDescComponent.setText(limits.get(position).getDescrizioneComponent());
        tvUm.setText(limits.get(position).getUm());
        tvValMin.setText(String.valueOf(limits.get(position).getLimiteMin()));
        tvValMax.setText(String.valueOf(limits.get(position).getLimiteMax()));
        return convertView;
    }
}
