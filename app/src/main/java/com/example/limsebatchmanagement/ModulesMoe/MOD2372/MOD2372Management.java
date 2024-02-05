package com.example.limsebatchmanagement.ModulesMoe.MOD2372;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.TextWatcher;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_ASB_SEM_MASS;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MOD2372Management {
    private static final String MODULE= "MOD2372",STUB="STUB",MATRICE="MATRICE",STRUMENTO="STRUMENTO",CONTATORE_CAMPI="CONTATORE_CAMPI";
    private final EntitySampleMoe sampleMoe;
    private final DatabaseBuilder db;
    private final Context context;
    private Spinner spStub,spMatrice,spStrumento,spContatoreCampi;
    private TextView tvDescrizioneCampione,tvData,tvContFibreAmianto,tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale;
    private EditText etCampiOsservati, etIngrandimenti,etNote,etAnalista,etAsbestos;
    private ImageButton addFiber,removeFiber,calendar;
    private GridViewFibreMod2372 adapter;
    private GridView gridViewFibre;
    private Dialog dialogMod2372;
    private final String textIdSample;
    private MOD2372Management(Context context,DatabaseBuilder db,EntitySampleMoe sampleMoe,String textIdSample,EditText etAsbestos) {
        this.sampleMoe=sampleMoe;
        this.db=db;
        this.context=context;
        this.textIdSample=textIdSample;
        this.etAsbestos=etAsbestos;
    }

    public static MOD2372Management create (Context context, DatabaseBuilder db, EntitySampleMoe sampleMoe,String textIdSample,EditText etAsbestos){
        return new MOD2372Management(context,db,sampleMoe,textIdSample,etAsbestos);
    }
    public Dialog getDialogMod2372(){
        dialogMod2372 = DialogCustom.createDialog.apply(context, R.layout.batch_man_mod2372);
        onCreate();
        onStart();
        onPostResume();
        return dialogMod2372;
    }
    private void onCreate(){
        tvDescrizioneCampione = dialogMod2372.findViewById(R.id.mod2372_descrizionecampione);
        tvData = dialogMod2372.findViewById(R.id.mod2372_data);
        tvContFibreAmianto = dialogMod2372.findViewById(R.id.mod2372_contfibreamianto);
        tvContFibreVetrose = dialogMod2372.findViewById(R.id.mod2372_contfibrevetrose);
        tvContLaneMinerali = dialogMod2372.findViewById(R.id.mod2372_contlaneminerali);
        tvContFibreCeramiche = dialogMod2372.findViewById(R.id.mod2372_contfibreceramiche);
        tvAmiantoTotale = dialogMod2372.findViewById(R.id.mod2372_amiantototale);
        etAnalista = dialogMod2372.findViewById(R.id.mod2372_analista);
        etCampiOsservati = dialogMod2372.findViewById(R.id.mod2372_campiosservati);
        etIngrandimenti = dialogMod2372.findViewById(R.id.mod2372_ingrandimenti);
        etNote = dialogMod2372.findViewById(R.id.mod2372_note);
        spStub = dialogMod2372.findViewById(R.id.mod2372_stub);
        spMatrice = dialogMod2372.findViewById(R.id.mod2372_matrice);
        spStrumento = dialogMod2372.findViewById(R.id.mod2372_strumento);
        spContatoreCampi = dialogMod2372.findViewById(R.id.mod2372_contatorecampi);
        calendar = dialogMod2372.findViewById(R.id.mod2372_calendar);
        addFiber = dialogMod2372.findViewById(R.id.mod2372_add_fiber);
        removeFiber = dialogMod2372.findViewById(R.id.mod2372_remove_fiber);
        gridViewFibre = dialogMod2372.findViewById(R.id.mod2372_fibregridview);
    }
    private void onStart(){
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,STUB),true,spStub,sampleMoe.getStub());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,MATRICE),true,spMatrice,sampleMoe.getMatrice());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,STRUMENTO),true,spStrumento,sampleMoe.getStrumento());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,CONTATORE_CAMPI),true,spContatoreCampi,sampleMoe.getContatoreCampi());
        tvDescrizioneCampione.setText((sampleMoe.getDescription().equals(EntitySampleMoe.EMPTY))? "":textIdSample + " " + sampleMoe.getDescription());
        tvData.setText((sampleMoe.getDataAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getDataAnalisi());
        etAnalista.setText((sampleMoe.getAnalistaAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getAnalistaAnalisi());
        etCampiOsservati.setText((sampleMoe.getNumCampi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNumCampi());
        etIngrandimenti.setText((sampleMoe.getIngrandimenti().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getIngrandimenti());
        etNote.setText((sampleMoe.getNoteAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNoteAnalisi());
        adapter = GridViewFibreMod2372.generate(context,db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber()),db,tvContFibreAmianto,
                tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale,etAsbestos);
        gridViewFibre.setAdapter(adapter);
    }
    private void onPostResume() {
        etAnalista.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setAnalistaAnalisi,editable.toString())));
        etCampiOsservati.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setNumCampi,editable.toString())));
        etIngrandimenti.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setIngrandimenti,editable.toString())));
        etNote.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setNoteAnalisi,editable.toString())));
        spStub.setOnItemSelectedListener(spinnerSelection(sampleMoe::setStub,spStub));
        spMatrice.setOnItemSelectedListener(spinnerSelection(sampleMoe::setMatrice,spMatrice));
        spStrumento.setOnItemSelectedListener(spinnerSelection(sampleMoe::setStrumento,spStrumento));
        spContatoreCampi.setOnItemSelectedListener(spinnerSelection(sampleMoe::setContatoreCampi,spContatoreCampi));
        calendar.setOnClickListener((view -> {dateTimePickerManagement(sampleMoe::setDataAnalisi,tvData);}));
        addFiber.setOnClickListener((view -> {addFiber();}));
        removeFiber.setOnClickListener((view)->{removeFiber();});
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
    }
    private void textWatcher (Consumer<String> consumer,String edit){
        consumer.accept(edit);
        sampleMoe.update(sampleMoe,db);
    }
    private AdapterView.OnItemSelectedListener spinnerSelection (Consumer<String> consumer,Spinner sp){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                consumer.accept(sp.getSelectedItem().toString());
                sampleMoe.update(sampleMoe,db);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
    }
    private void dateTimePickerManagement(Consumer<String> consumer,TextView tvData){
        Dialog dialog = DialogCustom.createDateTimePicker.apply(context, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        dialog.show();
        Button buttonSetDateTime = dialog.findViewById(R.id.button_setdatetime);
        DatePicker datePicker = dialog.findViewById(R.id.date_picker);
        TimePicker timePicker = dialog.findViewById(R.id.time_picker);
        buttonSetDateTime.setOnClickListener(v -> {
            String giorno = String.valueOf((datePicker.getDayOfMonth() > 9) ? datePicker.getDayOfMonth() : "0" + datePicker.getDayOfMonth());
            String mese = String.valueOf((datePicker.getMonth() + 1 > 9) ? datePicker.getMonth() + 1 : "0" + (datePicker.getMonth() + 1));
            String anno = String.valueOf((datePicker.getYear() > 9) ? datePicker.getYear() : "0" + datePicker.getYear());
            String ora = String.valueOf((timePicker.getCurrentHour() > 9) ? timePicker.getCurrentHour() : "0" + timePicker.getCurrentHour());
            String minuti = String.valueOf((timePicker.getCurrentMinute() > 9) ? timePicker.getCurrentMinute() : "0" + timePicker.getCurrentMinute());
            String data = giorno + "/" + mese + "/" + anno + " " + ora + ":" + minuti;
            tvData.setText(data);
            consumer.accept(data);
            sampleMoe.update(sampleMoe,db);
            dialog.dismiss();
        });
    }
    public void addFiber(){
        EntityFibre fiberToAdd = new EntityFibre();
        fiberToAdd.setSampleNumber(sampleMoe.getSampleNumber());
        fiberToAdd.setProject(sampleMoe.getProject());
        fiberToAdd.setIdFibra(db.SqlFibre().selectMaxIdFiberFromSample(sampleMoe.getSampleNumber())+1);
        fiberToAdd.setTipoFibra("-");
        fiberToAdd.setLunghezza("");
        fiberToAdd.setDiametro("");
        fiberToAdd.setQuantita("");
        db.SqlFibre().insert(fiberToAdd);
        List<EntityFibre> fibers = db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber());
        adapter = GridViewFibreMod2372.generate(context,fibers,db,tvContFibreAmianto,tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale,etAsbestos);
        adapter.countFibers();
        tvAmiantoTotale.setText(G_ASB_SEM_MASS.calcAsbestosTotal(fibers));
        gridViewFibre.setAdapter(adapter);
    }
    public void removeFiber(){
        int maxIdFiber = db.SqlFibre().selectMaxIdFiberFromSample(sampleMoe.getSampleNumber());
        if(maxIdFiber>0){
            EntityFibre fiberToRemove = db.SqlFibre().selectFiberToRemove(sampleMoe.getSampleNumber(),maxIdFiber);
            db.SqlFibre().delete(fiberToRemove);
            adapter = GridViewFibreMod2372.generate(context,db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber()),db,tvContFibreAmianto,
                    tvContFibreVetrose,tvContLaneMinerali,tvContFibreCeramiche,tvAmiantoTotale,etAsbestos);
            adapter.countFibers();
            tvAmiantoTotale.setText(G_ASB_SEM_MASS.calcAsbestosTotal(db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber())));
            gridViewFibre.setAdapter(adapter);
        }
    }
}
