package com.example.limsebatchmanagement.ModulesMoe.MOD2372;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.TextWatcher;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_ASB_SEM_MASS;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.Enum.CategoryFibers;
import com.example.limsebatchmanagement.R;
import java.util.List;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint({"UseCompatLoadingForColorStateLists", "ViewHolder"})
public class GridViewFibreMod2372 extends BaseAdapter {
    private static final String MODULE= "MOD2372",TIPO_FIBRA="TIPO_FIBRA";
    private final Context context;
    private final TextView tvContFibreAmianto,tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale;
    private final List<EntityFibre>fibers;
    private final DatabaseBuilder db;
    private final EditText etAsbestos;
    private GridViewFibreMod2372(Context context, List<EntityFibre>fibers, DatabaseBuilder db, TextView tvContFibreAmianto, TextView tvContFibreVetrose, TextView tvContLaneMinerali,
                                 TextView tvContFibreCeramiche, TextView tvAmiantoTotale,EditText etAsbestos){
            this.context = context;
            this.fibers=fibers;
            this.db=db;
            this.tvContFibreAmianto=tvContFibreAmianto;
            this.tvContFibreVetrose=tvContFibreVetrose;
            this.tvContLaneMinerali=tvContLaneMinerali;
            this.tvContFibreCeramiche=tvContFibreCeramiche;
            this.tvAmiantoTotale=tvAmiantoTotale;
            this.etAsbestos=etAsbestos;
    }
    @Override
    public int getCount() {
        return fibers.size(); }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public static GridViewFibreMod2372 generate(Context c, List<EntityFibre>fibers, DatabaseBuilder db, TextView tvContFibreAmianto, TextView tvContFibreVetrose,
                                                TextView tvContLaneMinerali, TextView tvContFibreCeramiche, TextView tvAmiantoTotale,EditText etAsbestos){
        return new GridViewFibreMod2372(c,fibers,db,tvContFibreAmianto,tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale,etAsbestos);
    }
    public View getView(int position,View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.batch_man_moe_gridview_2372_item, parent, false);
        TextView tvIdFibra = convertView.findViewById(R.id.mod2372_gridview_fibre_id);
        EditText etLunghezza = convertView.findViewById(R.id.mod2372_gridview_fibre_lunghezza);
        EditText etDiametro = convertView.findViewById(R.id.mod2372_gridview_fibre_diametro);
        Spinner spTipoFibra = convertView.findViewById(R.id.mod2372_gridview_tipofibra);
        tvIdFibra.setText(String.valueOf(fibers.get(position).getIdFibra()));
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,TIPO_FIBRA),true,spTipoFibra,fibers.get(position).getTipoFibra());
        etLunghezza.setText(fibers.get(position).getLunghezza());
        etDiametro.setText(fibers.get(position).getDiametro());
        etLunghezza.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(s -> fibers.get(position).setLunghezza(s),editable.toString(),fibers.get(position))));
        etDiametro.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(s -> fibers.get(position).setDiametro(s),editable.toString(),fibers.get(position))));
        spTipoFibra.setOnItemSelectedListener(spinnerSelection(s->fibers.get(position).setTipoFibra(s),spTipoFibra,fibers.get(position)));
        return convertView;
    }
    private void setSpinnerDefault(List<String> spList, boolean visible, Spinner sp, String entry) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.batch_man_moe_spinner_disable,spList);
        if(visible)
            adapter = new ArrayAdapter<>(context, R.layout.batch_man_moe_spinner_default,spList);
        adapter.setDropDownViewResource(R.layout.batch_man_moe_spinner_dropdown);
        sp.setAdapter(adapter);
        sp.setSelection(0);
        if(!entry.equals(EntitySampleMoe.EMPTY))
            spList.forEach(s->{if(s.equals(entry)) sp.setSelection(spList.indexOf(s));});
        countFibers();
        tvAmiantoTotale.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
        etAsbestos.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
    }
    private AdapterView.OnItemSelectedListener spinnerSelection (Consumer<String> consumer,Spinner sp,EntityFibre fiber){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                consumer.accept(sp.getSelectedItem().toString());
                fiber.update(fiber,db);
                countFibers();
                tvAmiantoTotale.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
                etAsbestos.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
    }
    private void textWatcher (Consumer<String> consumer, String edit,EntityFibre fiber){
        consumer.accept(edit);
        fiber.update(fiber,db);
        tvAmiantoTotale.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
        etAsbestos.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
    }
    public void countFibers(){
        long asbestosFiber = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.AMIANTO)).count();
        long vitreousFiber  = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.LANE) || CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.FCR)).count();
        long mineralWool = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.LANE)).count();
        long ceramicFiber = fibers.stream().filter(f->CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.FCR)).count();
        tvContFibreAmianto.setText(String.valueOf(asbestosFiber));
        tvContFibreVetrose.setText(String.valueOf(vitreousFiber));
        tvContLaneMinerali.setText(String.valueOf(mineralWool));
        tvContFibreCeramiche.setText(String.valueOf(ceramicFiber));
    }
}
