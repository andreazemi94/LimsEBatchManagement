package com.example.limsebatchmanagement.ModulesMoe.MOD2548;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.BatchManagement.TextWatcher;
import com.example.limsebatchmanagement.CalcoliLab.MOE.G_TOTALFIBRES_OM_A;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityResult;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MOD2548Management {
    private static final String MODULE= "MOD2548",METODO="METODO",MATRICE="MATRICE",STRUMENTO="STRUMENTO";
    private EntitySampleMoe sampleMoe;
    private DatabaseBuilder db;
    private Context context;
    private Spinner spMetodo,spMatrice,spStrumento;
    private TextView tvDescrizioneCampione,tvData,tvContFibreTotali;
    private EditText etCampiOsservati,etNote,etAnalista;
    private Switch swBandaVisibile;
    private ImageButton calendar;
    private GridViewFibreMod2548 adapter;
    private GridView gridViewFibre;
    private Dialog dialogMod2548;
    private String textIdSample;
    private EditText etTotalFibers;
    private EntityResult nFields;
    private EntityResult analysisDate;
    private MOD2548Management(Context context,DatabaseBuilder db,EntitySampleMoe sampleMoe,String textIdSample, EditText etTotalFibers) {
        this.sampleMoe=sampleMoe;
        this.db=db;
        this.context=context;
        this.textIdSample=textIdSample;
        this.etTotalFibers=etTotalFibers;
    }

    public static MOD2548Management create(Context context, DatabaseBuilder db, EntitySampleMoe sampleMoe, String textIdSample, EditText etTotalFibers){
        return new MOD2548Management(context,db,sampleMoe,textIdSample,etTotalFibers);
    }
    public Dialog getDialogMod2548(){
        dialogMod2548 = DialogCustom.createDialog.apply(context, R.layout.batch_man_mod2548);
        onCreate();
        onStart();
        onPostResume();
        return dialogMod2548;
    }
    private void onCreate(){
        tvDescrizioneCampione = dialogMod2548.findViewById(R.id.mod2548_descrizionecampione);
        tvData = dialogMod2548.findViewById(R.id.mod2548_data);
        tvContFibreTotali = dialogMod2548.findViewById(R.id.mod2548_fibretotali);
        etAnalista = dialogMod2548.findViewById(R.id.mod2548_analista);
        etCampiOsservati = dialogMod2548.findViewById(R.id.mod2548_numerocampi);
        etNote = dialogMod2548.findViewById(R.id.mod2548_note);
        spMetodo = dialogMod2548.findViewById(R.id.mod2548_metodo);
        spMatrice = dialogMod2548.findViewById(R.id.mod2548_matrice);
        spStrumento = dialogMod2548.findViewById(R.id.mod2548_strumento);
        swBandaVisibile = dialogMod2548.findViewById(R.id.mod2548_bandavisibile);
        calendar = dialogMod2548.findViewById(R.id.mod2548_calendar);
        gridViewFibre = dialogMod2548.findViewById(R.id.mod2548_fibregridview);
    }
    private void onStart(){
        nFields = db.SqlResult().selectResultFromSampleAndName(sampleMoe.getSampleNumber(), G_TOTALFIBRES_OM_A.MICROSCOPIC_FIELDS);
        analysisDate = db.SqlResult().selectResultFromSampleAndName(sampleMoe.getSampleNumber(), EntityResult.ANALYSIS_DATE);
        sampleMoe.setNumCampi(nFields.getEntry());
        sampleMoe.setDataAnalisi(analysisDate.getEntry());
        sampleMoe.update(sampleMoe,db);
        int fields = (nFields.getEntry().equals(EntityResult.ENTRY_VUOTA))? 200:Integer.parseInt(nFields.getEntry());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,METODO),true,spMetodo,sampleMoe.getStub());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,MATRICE),true,spMatrice,sampleMoe.getMatrice());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,STRUMENTO),true,spStrumento,sampleMoe.getStrumento());
        tvDescrizioneCampione.setText((sampleMoe.getDescription().equals(EntitySampleMoe.EMPTY))? "":textIdSample + " " + sampleMoe.getDescription());
        tvData.setText((sampleMoe.getDataAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getDataAnalisi());
        etAnalista.setText((sampleMoe.getAnalistaAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getAnalistaAnalisi());
        etCampiOsservati.setText((sampleMoe.getNumCampi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNumCampi());
        etNote.setText((sampleMoe.getNoteAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNoteAnalisi());
        if(sampleMoe.getBandaVisibile().equals("Si"))
            swBandaVisibile.setChecked(true);
        else
            swBandaVisibile.setChecked(false);
        List<Integer> idFibers = new ArrayList<>();
        IntStream.rangeClosed(1,fields).forEach(idFibers::add);
        List<EntityFibre> fibers = db.SqlFibre().selectFibersFromSample(sampleMoe.getSampleNumber());
        adapter = GridViewFibreMod2548.generate(context,db,idFibers,tvContFibreTotali,sampleMoe.getSampleNumber(),sampleMoe.getProject(),etTotalFibers,fibers);
        gridViewFibre.setAdapter(adapter);
    }
    private void onPostResume() {
        etAnalista.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(s -> sampleMoe.setAnalistaAnalisi(s),editable.toString())));
        etCampiOsservati.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(s -> sampleMoe.setNumCampi(s),editable.toString())));
        etNote.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(s -> sampleMoe.setNoteAnalisi(s),editable.toString())));
        spMetodo.setOnItemSelectedListener(spinnerSelection(s->sampleMoe.setMetodo(s),spMetodo));
        spMatrice.setOnItemSelectedListener(spinnerSelection(s->sampleMoe.setMatrice(s),spMatrice));
        spStrumento.setOnItemSelectedListener(spinnerSelection(s->sampleMoe.setStrumento(s),spStrumento));
        calendar.setOnClickListener((view -> {dateTimePickerManagement(s -> sampleMoe.setDataAnalisi(s),tvData);}));
        swBandaVisibile.setOnCheckedChangeListener(switchMoe(s->sampleMoe.setBandaVisibile(s)));
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
    private void textWatcher (Consumer<String> consumer, String edit){
        consumer.accept(edit);
        sampleMoe.update(sampleMoe,db);
    }
    private AdapterView.OnItemSelectedListener spinnerSelection (Consumer<String> consumer, Spinner sp){
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
    private CompoundButton.OnCheckedChangeListener switchMoe (Consumer<String> consumer){
        return ((compoundButton, b) -> {
            if(b)
                consumer.accept("Si");
            else
                consumer.accept("No");
            sampleMoe.update(sampleMoe,db);
            onStart();
        });
    }
}
