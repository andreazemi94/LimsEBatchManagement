package com.example.limsebatchmanagement.ModulesMoe.MOD2805;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.limsebatchmanagement.BatchManagement.TextWatcher;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntitySampleMoe;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityResult;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.DialogCustom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.*;

@RequiresApi(api = Build.VERSION_CODES.O)
@SuppressLint("UseCompatLoadingForDrawables")
public class MOD2805Management {
    private static final String MODULE= "MOD2805", MATERIALE = "MATERIALE", TRATTAMENTO = "TRATTAMENTO", SCREENING = "SCREENING", ESITO = "ESITO_QUALITATIVO",MORFOLOGIA = "MORFOLOGIA", ASPECT_RATIO = "ASPECT_RATIO",
            TIPO_FIBRA = "TIPO_FIBRA";
    private final EntitySampleMoe sampleMoe;
    private final DatabaseBuilder db;
    private final Context context;
    private Spinner spMateriale,spScreening,spTrattamento,spTipologia1Morfologia,spTipologia2Morfologia,spTipologia3Morfologia,spTipologia1AspectRatio,spTipologia2AspectRatio,spTipologia3AspectRatio,
            spTipologia1TipoFibra,spTipologia2TipoFibra,spTipologia3TipoFibra,spEsitoAnalisi;
    private TextView tvDescrizioneCampione,tvPreparativaData,tvAnalisiData;
    private EditText etPreparativaAnalista,etPreparativaAltroMateriale,etPreparativaNote,etPreparativaPeso,etPreparativaDescrizioneDettagliata,etPreparativaPesoPrimaCalci,etPreparativaPesoDopoCalci,
            etAnalisiAnalista,etAnalisiCampiOsservati, etAnalisiIngrandimenti,etAnalisiNote,etAnalisiTipologia1NumFibreIdentificate, etAnalisiTipologia2NumFibreIdentificate, etAnalisiTipologia3NumFibreIdentificate,
            etInitialQuantity;
    private Switch swAnalisiFibreRilevate,swAnalisiTipologia1,swAnalisiTipologia2,swAnalisiTipologia3,swAnalisiTipologia1ChimicaEds,swAnalisiTipologia2ChimicaEds,swAnalisiTipologia3ChimicaEds;
    private ImageButton prepCalendar,analisiCalendar;
    private ConstraintLayout ltTipologia1,ltTipologia2,ltTipologia3,ltPesoCalcinazione;
    private Dialog dialogMod2805;
    private final String textIdSample;
    private MOD2805Management(Context context,DatabaseBuilder db,EntitySampleMoe sampleMoe,String textIdSample,EditText etInitialQuantity) {
        this.sampleMoe=sampleMoe;
        this.db=db;
        this.context=context;
        this.textIdSample=textIdSample;
        this.etInitialQuantity=etInitialQuantity;
    }

    public static MOD2805Management create (Context context,DatabaseBuilder db,EntitySampleMoe sampleMoe,String textIdSample,EditText etInitialQuantity){
        return new MOD2805Management(context,db,sampleMoe,textIdSample,etInitialQuantity);
    }
    public Dialog getDialogMod2805(){
        dialogMod2805 = DialogCustom.createDialog.apply(context,R.layout.batch_man_mod2805);
        onCreate();
        onStart();
        onPostResume();
        return dialogMod2805;
    }
    private void onCreate(){
        tvDescrizioneCampione = dialogMod2805.findViewById(R.id.mod2805_descrizionecampione);
        tvPreparativaData = dialogMod2805.findViewById(R.id.mod2805_preparativadata);
        tvAnalisiData = dialogMod2805.findViewById(R.id.mod2805_analisidata);
        etPreparativaAnalista = dialogMod2805.findViewById(R.id.mod2805_preparativaanalista);
        etPreparativaAltroMateriale = dialogMod2805.findViewById(R.id.mod2805_preparativamaterialetext);
        etPreparativaNote = dialogMod2805.findViewById(R.id.mod2805_preparativanote);
        etPreparativaPeso = dialogMod2805.findViewById(R.id.mod2805_preparativapeso);
        etPreparativaDescrizioneDettagliata = dialogMod2805.findViewById(R.id.mod2805_preparativadescdettagliata);
        etPreparativaPesoPrimaCalci = dialogMod2805.findViewById(R.id.mod2805_preparativapesoprimacalci);
        etPreparativaPesoDopoCalci = dialogMod2805.findViewById(R.id.mod2805_preparativapesodopocalci);
        etAnalisiAnalista = dialogMod2805.findViewById(R.id.mod2805_analisianalista);
        etAnalisiCampiOsservati = dialogMod2805.findViewById(R.id.mod2805_analisinumerocampi);
        etAnalisiIngrandimenti = dialogMod2805.findViewById(R.id.mod2805_analisiingrandimenti);
        etAnalisiNote = dialogMod2805.findViewById(R.id.mod2805_analisinote);
        etAnalisiTipologia1NumFibreIdentificate = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1fibreidentificate);
        etAnalisiTipologia2NumFibreIdentificate = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2fibreidentificate);
        etAnalisiTipologia3NumFibreIdentificate = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3fibreidentificate);
        spMateriale = dialogMod2805.findViewById(R.id.mod2805_preparativamaterialespinner);
        spScreening = dialogMod2805.findViewById(R.id.mod2805_preparativascreening);
        spTrattamento = dialogMod2805.findViewById(R.id.mod2805_preparativatrattamento);
        spTipologia1Morfologia = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1morfologia);
        spTipologia2Morfologia = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2morfologia);
        spTipologia3Morfologia = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3morfologia);
        spTipologia1AspectRatio = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1aspectratio);
        spTipologia2AspectRatio = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2aspectratio);
        spTipologia3AspectRatio = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3aspectratio);
        spTipologia1TipoFibra = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1tipofibra);
        spTipologia2TipoFibra = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2tipofibra);
        spTipologia3TipoFibra = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3tipofibra);
        spEsitoAnalisi = dialogMod2805.findViewById(R.id.mod2805_analisiesitoanalisiqual);
        swAnalisiFibreRilevate = dialogMod2805.findViewById(R.id.mod2805_analisifibrerilevate);
        swAnalisiTipologia1 = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1);
        swAnalisiTipologia2 = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2);
        swAnalisiTipologia3 = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3);
        swAnalisiTipologia1ChimicaEds = dialogMod2805.findViewById(R.id.mod2805_analisitipologia1chimicaeds);
        swAnalisiTipologia2ChimicaEds = dialogMod2805.findViewById(R.id.mod2805_analisitipologia2chimicaeds);
        swAnalisiTipologia3ChimicaEds = dialogMod2805.findViewById(R.id.mod2805_analisitipologia3chimicaeds);
        prepCalendar = dialogMod2805.findViewById(R.id.mod2805_preparativacalendar);
        analisiCalendar = dialogMod2805.findViewById(R.id.mod2805_analisicalendar);
        ltTipologia1 = dialogMod2805.findViewById(R.id.mod2805_layouttipologia1);
        ltTipologia2 = dialogMod2805.findViewById(R.id.mod2805_layouttipologia2);
        ltTipologia3 = dialogMod2805.findViewById(R.id.mod2805_layouttipologia3);
        ltPesoCalcinazione = dialogMod2805.findViewById(R.id.mod2805_layoutpesocalci);
    }
    private void onStart(){
        EntityResult analysisDate = db.SqlResult().selectResultFromSampleAndName(sampleMoe.getSampleNumber(), EntityResult.ANALYSIS_DATE);
        sampleMoe.setDataAnalisi(analysisDate.getEntry());
        sampleMoe.setDataPrep(analysisDate.getEntry());
        sampleMoe.update(sampleMoe,db);
        tvDescrizioneCampione.setText((sampleMoe.getDescription().equals(EntitySampleMoe.EMPTY))? "":textIdSample + " " + sampleMoe.getDescription());
        tvPreparativaData.setText((sampleMoe.getDataPrep().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getDataPrep());
        tvAnalisiData.setText((sampleMoe.getDataAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getDataAnalisi());
        etPreparativaAnalista.setText((sampleMoe.getAnalistaPrep().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getAnalistaPrep());
        etPreparativaNote.setText((sampleMoe.getNotePrep().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNotePrep());
        etPreparativaPeso.setText((sampleMoe.getPeso().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getPeso());
        etPreparativaDescrizioneDettagliata.setText((sampleMoe.getDescrizioneDettagliata().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getDescrizioneDettagliata());
        etAnalisiAnalista.setText((sampleMoe.getAnalistaAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getAnalistaAnalisi());
        etAnalisiCampiOsservati.setText((sampleMoe.getNumCampi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNumCampi());
        etAnalisiIngrandimenti.setText((sampleMoe.getIngrandimenti().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getIngrandimenti());
        etAnalisiNote.setText((sampleMoe.getNoteAnalisi().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getNoteAnalisi());
        etPreparativaPesoPrimaCalci.setText((sampleMoe.getPesoAnteCalcinazione().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getPesoAnteCalcinazione());
        etPreparativaPesoDopoCalci.setText((sampleMoe.getPesoPostCalcinazione().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getPesoPostCalcinazione());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,MATERIALE),true,spMateriale,sampleMoe.getMateriale());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,TRATTAMENTO),true,spTrattamento,sampleMoe.getTrattamento());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,SCREENING),true,spScreening,sampleMoe.getScreening());
        setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,ESITO),true,spEsitoAnalisi,sampleMoe.getEsitoAnalisiQualitativa());
        if(spMateriale.getSelectedItem().toString().equals("-")){
            etPreparativaAltroMateriale.setEnabled(true);
            etPreparativaAltroMateriale.setBackground(context.getDrawable(R.drawable.background_edittext));
            etPreparativaAltroMateriale.setText((sampleMoe.getMateriale().equals(EntitySampleMoe.EMPTY))? "":sampleMoe.getMateriale());
        } else {
            etPreparativaAltroMateriale.setEnabled(false);
            etPreparativaAltroMateriale.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
        }
        if(sampleMoe.getFibreRilevate().equals("Si")){
            swAnalisiFibreRilevate.setChecked(true);
            ltTipologia1.setVisibility(View.VISIBLE);
            ltTipologia2.setVisibility(View.VISIBLE);
            ltTipologia3.setVisibility(View.VISIBLE);
        } else {
            swAnalisiFibreRilevate.setChecked(false);
            ltTipologia1.setVisibility(View.INVISIBLE);
            ltTipologia2.setVisibility(View.INVISIBLE);
            ltTipologia3.setVisibility(View.INVISIBLE);
        }
        tipologiaManagement(sampleMoe.getTipologia1(),swAnalisiTipologia1,swAnalisiTipologia1ChimicaEds,spTipologia1Morfologia,spTipologia1AspectRatio,spTipologia1TipoFibra,
                sampleMoe::getTipologia1Morfologia, sampleMoe::getTipologia1AspectRatio, sampleMoe::getTipologia1TipoFibra, sampleMoe::getTipologia1NFibre,
                etAnalisiTipologia1NumFibreIdentificate);
        tipologiaManagement(sampleMoe.getTipologia2(),swAnalisiTipologia2,swAnalisiTipologia2ChimicaEds,spTipologia2Morfologia,spTipologia2AspectRatio,spTipologia2TipoFibra,
                sampleMoe::getTipologia2Morfologia, sampleMoe::getTipologia2AspectRatio, sampleMoe::getTipologia2TipoFibra, sampleMoe::getTipologia2NFibre,
                etAnalisiTipologia2NumFibreIdentificate);
        tipologiaManagement(sampleMoe.getTipologia3(),swAnalisiTipologia3,swAnalisiTipologia3ChimicaEds,spTipologia3Morfologia,spTipologia3AspectRatio,spTipologia3TipoFibra,
                sampleMoe::getTipologia3Morfologia, sampleMoe::getTipologia3AspectRatio, sampleMoe::getTipologia3TipoFibra, sampleMoe::getTipologia3NFibre,
                etAnalisiTipologia3NumFibreIdentificate);
        if(sampleMoe.getTipologia1AnalisiChimicaEds().equals("Si"))
            swAnalisiTipologia1ChimicaEds.setChecked(true);
        else
            swAnalisiTipologia1ChimicaEds.setChecked(false);
        if(sampleMoe.getTipologia2AnalisiChimicaEds().equals("Si"))
           swAnalisiTipologia2ChimicaEds.setChecked(true);
        else
            swAnalisiTipologia2ChimicaEds.setChecked(false);
        if(sampleMoe.getTipologia3AnalisiChimicaEds().equals("Si"))
            swAnalisiTipologia3ChimicaEds.setChecked(true);
        else
            swAnalisiTipologia3ChimicaEds.setChecked(false);
    }
    private void onPostResume(){
        etPreparativaAnalista.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setAnalistaPrep,editable.toString())));
        etPreparativaAltroMateriale.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setMateriale,editable.toString())));
        etPreparativaNote.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setNotePrep,editable.toString())));
        etPreparativaPeso.addTextChangedListener(TextWatcher.getTextWatch(editable -> {
            textWatcher(sampleMoe::setPeso,editable.toString());
            etInitialQuantity.setText(sampleMoe.getPeso());
        }));
        etPreparativaDescrizioneDettagliata.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setDescrizioneDettagliata,editable.toString())));
        etPreparativaPesoPrimaCalci.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setPesoAnteCalcinazione,editable.toString())));
        etPreparativaPesoDopoCalci.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setPesoPostCalcinazione,editable.toString())));
        etAnalisiAnalista.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setAnalistaAnalisi,editable.toString())));
        etAnalisiCampiOsservati.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setNumCampi,editable.toString())));
        etAnalisiIngrandimenti.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setIngrandimenti,editable.toString())));
        etAnalisiNote.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setNoteAnalisi,editable.toString())));
        etAnalisiTipologia1NumFibreIdentificate.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setTipologia1NFibre,editable.toString())));
        etAnalisiTipologia2NumFibreIdentificate.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setTipologia2NFibre,editable.toString())));
        etAnalisiTipologia3NumFibreIdentificate.addTextChangedListener(TextWatcher.getTextWatch(editable -> textWatcher(sampleMoe::setTipologia3NFibre,editable.toString())));
        swAnalisiFibreRilevate.setOnCheckedChangeListener(switchMoe(sampleMoe::setFibreRilevate));
        swAnalisiTipologia1.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia1));
        swAnalisiTipologia2.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia2));
        swAnalisiTipologia3.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia3));
        swAnalisiTipologia1ChimicaEds.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia1AnalisiChimicaEds));
        swAnalisiTipologia2ChimicaEds.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia2AnalisiChimicaEds));
        swAnalisiTipologia3ChimicaEds.setOnCheckedChangeListener(switchMoe(sampleMoe::setTipologia3AnalisiChimicaEds));
        spMateriale.setOnItemSelectedListener(spinnerSelection(sampleMoe::setMateriale,spMateriale));
        spScreening.setOnItemSelectedListener(spinnerSelection(sampleMoe::setScreening,spScreening));
        spTrattamento.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTrattamento,spTrattamento));
        spTipologia1Morfologia.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia1Morfologia,spTipologia1Morfologia));
        spTipologia2Morfologia.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia2Morfologia,spTipologia2Morfologia));
        spTipologia3Morfologia.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia3Morfologia,spTipologia3Morfologia));
        spTipologia1AspectRatio.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia1AspectRatio,spTipologia1AspectRatio));
        spTipologia2AspectRatio.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia2AspectRatio,spTipologia2AspectRatio));
        spTipologia3AspectRatio.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia3AspectRatio,spTipologia3AspectRatio));
        spTipologia1TipoFibra.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia1TipoFibra,spTipologia1TipoFibra));
        spTipologia2TipoFibra.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia2TipoFibra,spTipologia2TipoFibra));
        spTipologia3TipoFibra.setOnItemSelectedListener(spinnerSelection(sampleMoe::setTipologia3TipoFibra,spTipologia3TipoFibra));
        spEsitoAnalisi.setOnItemSelectedListener(spinnerSelection(sampleMoe::setEsitoAnalisiQualitativa,spEsitoAnalisi));
        prepCalendar.setOnClickListener((view -> {dateTimePickerManagement(sampleMoe::setDataPrep,tvPreparativaData);}));
        analisiCalendar.setOnClickListener((view -> {dateTimePickerManagement(sampleMoe::setDataAnalisi,tvAnalisiData);}));
    }
    private void tipologiaManagement(String tipologiaSiNo, Switch tipologia, Switch eds, Spinner morfologia, Spinner aspectRatio, Spinner tipoFibra,
                                     Supplier<String> getMorfologia,Supplier<String> getAspectRatio,Supplier<String> getTipoFibra,Supplier<String> getNFibre,
                                     EditText fibreIdentificate){
        if(tipologiaSiNo.equals("Si")){
            tipologia.setChecked(true);
            eds.setEnabled(true);
            morfologia.setEnabled(true);
            aspectRatio.setEnabled(true);
            tipoFibra.setEnabled(true);
            morfologia.setBackground(context.getDrawable(R.drawable.background_edittext));
            aspectRatio.setBackground(context.getDrawable(R.drawable.background_edittext));
            tipoFibra.setBackground(context.getDrawable(R.drawable.background_edittext));
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,MORFOLOGIA),true,morfologia,getMorfologia.get());
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,ASPECT_RATIO),true,aspectRatio,getAspectRatio.get());
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,TIPO_FIBRA),true,tipoFibra,getTipoFibra.get());
            fibreIdentificate.setEnabled(true);
            fibreIdentificate.setBackground(context.getDrawable(R.drawable.background_edittext));
            fibreIdentificate.setText(getNFibre.get());
        } else {
            tipologia.setChecked(false);
            eds.setEnabled(false);
            morfologia.setEnabled(false);
            aspectRatio.setEnabled(false);
            tipoFibra.setEnabled(false);
            morfologia.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
            aspectRatio.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
            tipoFibra.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,MORFOLOGIA),false,morfologia,getMorfologia.get());
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,ASPECT_RATIO),false,aspectRatio,getAspectRatio.get());
            setSpinnerDefault(db.SqlListEntryMoe().selectEntriesFromModuleAndKeys(MODULE,TIPO_FIBRA),false,tipoFibra,getTipoFibra.get());
            fibreIdentificate.setEnabled(false);
            fibreIdentificate.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
        }
    }
    private void setSpinnerDefault(List<String> spList, boolean visible,Spinner sp,String entry) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.batch_man_moe_spinner_disable,spList);
        if(visible)
            adapter = new ArrayAdapter<>(context, R.layout.batch_man_moe_spinner_default,spList);
        adapter.setDropDownViewResource(R.layout.batch_man_moe_spinner_dropdown);
        sp.setAdapter(adapter);
        sp.setSelection(0);
        if(!entry.equals(EntitySampleMoe.EMPTY))
            spList.forEach(s->{if(s.equals(entry)) sp.setSelection(spList.indexOf(s));});
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
                if(sp==spMateriale) {
                    if(spMateriale.getSelectedItem().toString().equals("-")){
                        etPreparativaAltroMateriale.setEnabled(true);
                        etPreparativaAltroMateriale.setBackground(context.getDrawable(R.drawable.background_edittext));
                    } else {
                        etPreparativaAltroMateriale.setEnabled(false);
                        etPreparativaAltroMateriale.setBackground(context.getDrawable(R.drawable.background_edittext_disable));
                    }
                }
                if(sp==spTrattamento){
                    if(spTrattamento.getSelectedItem().toString().equals("Calcinazione"))
                        ltPesoCalcinazione.setVisibility(View.VISIBLE);
                    else
                        ltPesoCalcinazione.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
    }
    private void dateTimePickerManagement(Consumer<String> consumer,TextView tvData){
        Dialog dialog = DialogCustom.createDateTimePicker.apply(context,LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
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
}
