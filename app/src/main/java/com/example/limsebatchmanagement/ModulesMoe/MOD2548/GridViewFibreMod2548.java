package com.example.limsebatchmanagement.ModulesMoe.MOD2548;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.TextWatcher;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_TOTALFIBRES_OM_A;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityFibre;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityResult;
import com.example.limsebatchmanagement.R;
import java.util.*;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GridViewFibreMod2548 extends BaseAdapter {
    private Context context;
    private List<Integer> idFibers;
    private DatabaseBuilder db;
    private TextView tvContTotFibers,tvIdFiber;
    private EditText quantity;
    private int sampleNumber;
    private String project;
    private EditText etTotalFibers;
    private List<EntityFibre> fibers;
    private EntityFibre fiber;
    public GridViewFibreMod2548(Context context,DatabaseBuilder db,List<Integer> idFibers,TextView tvContTotFibers,int sampleNumber,String project,EditText etTotalFibers,List<EntityFibre> fibers){
            this.context=context;
            this.db=db;
            this.idFibers=idFibers;
            this.tvContTotFibers=tvContTotFibers;
            this.sampleNumber=sampleNumber;
            this.project=project;
            this.etTotalFibers=etTotalFibers;
            this.fibers=fibers;
        }
    public static GridViewFibreMod2548 generate(Context context,DatabaseBuilder db,List<Integer> idFibers,TextView tvContTotFibers,int sampleNumber,String project,EditText etTotalFibers,List<EntityFibre> fibers){
        return new GridViewFibreMod2548(context,db,idFibers,tvContTotFibers,sampleNumber,project,etTotalFibers,fibers);
    }
    @Override
    public int getCount() {
        return idFibers.size(); }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint({"UseCompatLoadingForColorStateLists", "ViewHolder"})
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.batch_man_moe_gridview_2548_item, parent, false);
        tvIdFiber = convertView.findViewById(R.id.mod2548_gridview_fibre_id);
        quantity = convertView.findViewById(R.id.mod2548_gridview_quantita);
        int idFiber = idFibers.get(position);
        tvIdFiber.setText(String.valueOf(idFiber));
        quantity.setText("");
        fibers.stream().filter(f->f.getIdFibra()==idFiber).findFirst().ifPresent(f->quantity.setText(f.getQuantita()));
        quantity.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(editable.toString(),idFiber)));
        viewAsbestosTotal();
        return convertView;
    }
    private void textWatcher ( String edit, int idFiber){
        fiber = db.SqlFibre().selectSingleFiber(sampleNumber,idFiber);
        if((edit.equals("") || edit.equals("0"))){
            if(Objects.nonNull(fiber)){
                db.SqlFibre().delete(fiber);
                fibers.remove(fiber);
                fiber=null;
            }
        }
        else{
            if(Objects.isNull(fiber)){
                EntityFibre f = new EntityFibre();
                f.setSampleNumber(sampleNumber);
                f.setProject(project);
                f.setIdFibra(idFiber);
                f.setTipoFibra("-");
                f.setLunghezza("0");
                f.setDiametro("0");
                f.setQuantita(edit);
                db.SqlFibre().insert(f);
                fibers.add(f);
            } else {
                fiber.setQuantita(edit);
                fiber.update(fiber,db);
                fibers.set(fibers.indexOf(fiber),fiber);
            }
        }
        calcAsbestosTotal();
    }
    private void viewAsbestosTotal(){
        double totalFibers = fibers.stream().mapToDouble(f-> Double.parseDouble(f.getQuantita())).sum();
        tvContTotFibers.setText(String.valueOf(totalFibers));
    }
    private void calcAsbestosTotal() {
        try{
            double totalFibers = fibers.stream().mapToDouble(f-> Double.parseDouble(f.getQuantita())).sum();
            tvContTotFibers.setText(String.valueOf(totalFibers));
            EntityResult resTotalFibers = db.SqlResult().selectResultFromSampleAndName(sampleNumber, G_TOTALFIBRES_OM_A.TOTALFIBRES);
            resTotalFibers.setEntry(String.valueOf(totalFibers));
            resTotalFibers.update(resTotalFibers,db);
            etTotalFibers.setText(String.valueOf(totalFibers));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
