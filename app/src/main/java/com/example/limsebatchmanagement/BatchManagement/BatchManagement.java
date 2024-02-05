package com.example.limsebatchmanagement.BatchManagement;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.*;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.limsebatchmanagement.Adapter.*;
import com.example.limsebatchmanagement.Archiviazione.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityModule;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.DriveManagement.*;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.Database.DownloadAndForceResult;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.Manual.DownloadLabManual;
import com.example.limsebatchmanagement.*;
import com.example.limsebatchmanagement.DatabaseFirebase.Upload.UploadProcess;
import com.example.limsebatchmanagement.Enum.*;
import com.example.limsebatchmanagement.ModulesMoe.MOD2372.MOD2372Pdf;
import com.example.limsebatchmanagement.ModulesMoe.MOD2548.MOD2548Pdf;
import com.example.limsebatchmanagement.ModulesMoe.MOD2805.MOD2805Pdf;
import com.example.limsebatchmanagement.Utility.*;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.Drive;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BatchManagement extends AppCompatActivity  {
    public static final int MAX_ANALYSIS_COMPONENT = 26;
    EntityBatch batch;
    List<EntityBatchObject> batchObjects = new ArrayList<>();
    List<EntitySample> samples = new ArrayList<>();
    List<EntityTest> tests = new ArrayList<>();
    List<EntityResult> results = new ArrayList<>();
    List<EntityAnalysisComponent> analysisComponent = new ArrayList<>();
    List<EntityLimit> limits = new ArrayList<>();
    List<EntityCustomer> customers = new ArrayList<>();
    Map<String,String> listEntries = new HashMap<>();
    List<TextView> tvAnalysisList = new ArrayList<>(),tvComponentList = new ArrayList<>();
    List<ImageButton> ibPropagationResultList = new ArrayList<>();
    List<Integer> listDistinctSampleNumber = new ArrayList<>();
    DatabaseBuilder db;
    String batchSelected;
    TextView tvBatchName,tvInstrument,tvTotObj,tvForecastDate, tvForecastFinDateStr,tvMinDueDateStr,
            tvForecastUser,tvBatchQualityValue, tvBatchDueDateTypeValue,tvBatchPhase;
    ImageButton ibManualeLab,ibNote,ibSalva,ibGeneraFile,ibLimiti,ibRecoveryData;
    RecyclerView rvResult;
    GoogleSignInAccount driveAccount;
    GoogleAccountCredential driveCredential;
    Drive driveService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_management);
        tvBatchName = findViewById(R.id.batch_man_textviex_nomebatch);
        tvInstrument = findViewById(R.id.batch_man_textviex_instrument);
        tvTotObj = findViewById(R.id.batch_man_textviex_ntest);
        tvForecastDate = findViewById(R.id.batch_man_textviex_fcststart);
        tvForecastFinDateStr = findViewById(R.id.batch_man_textviex_fcstend);
        tvMinDueDateStr = findViewById(R.id.batch_man_textviex_duedate);
        tvForecastUser = findViewById(R.id.batch_man_textviex_fcstuser);
        tvBatchQualityValue = findViewById(R.id.batch_man_textviex_quallvl);
        tvBatchDueDateTypeValue = findViewById(R.id.batch_man_textviex_pri);
        tvBatchPhase = findViewById(R.id.batch_man_textviex_phase);
        ibManualeLab = findViewById(R.id.batch_man_button_manuale);
        ibNote = findViewById(R.id.batch_man_button_note);
        ibSalva = findViewById(R.id.batch_man_button_salva);
        ibGeneraFile = findViewById(R.id.batch_man_button_creazione_file);
        ibLimiti  = findViewById(R.id.batch_man_button_limiti);
        ibRecoveryData  = findViewById(R.id.batch_man_button_recovery);
        rvResult = findViewById(R.id.batch_man_reciclerview_title);
        IntStream.range(1,MAX_ANALYSIS_COMPONENT).forEach(i->{
            tvAnalysisList.add(findViewById(getResources().getIdentifier("batch_man_lista_header_analysis_item"+i, "id", getPackageName())));
            tvComponentList.add(findViewById(getResources().getIdentifier("batch_man_lista_header_component_item"+i, "id", getPackageName())));
            ibPropagationResultList.add(findViewById(getResources().getIdentifier("batch_man_lista_header_propagazione_button"+i, "id", getPackageName())));
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        batchSelected = getIntent().getStringExtra("mainBatchSelected");
        driveAccount = getIntent().getParcelableExtra("driveAccount");
        driveCredential = GoogleDrive.getDriveCredential.apply(this, driveAccount);
        driveService = GoogleDrive.getDriveService.apply(MainActivity.APP_NAME, driveCredential);
        db = DatabaseBuilder.getDbInstance(this);
        batch = db.SqlBatch().selectSingleBatch(batchSelected);
        batchObjects = db.SqlBatchObject().selectBatchObjectsFromBatch(batchSelected);
        samples = db.SqlSample().selectSamplesFromBatch(batchSelected);
        tests = db.SqlTest().selectTestsFromBatch(batchSelected);
        results = db.SqlResult().selectResultsFromBatch(batchSelected);
        analysisComponent = db.SqlResult().selectAnalysisNameDistinctFromBatch(batchSelected);
        db.SqlListEntry().selectEntryFromBatch(batchSelected).forEach(name->{
            listEntries.compute(name,(k,v)-> (v!=null)? v:db.SqlListEntry().selectValueFromName(name));
        });
        listDistinctSampleNumber = db.SqlSample().selectDistinctSampleNumber(batchSelected);
        limits = db.SqlLimit().selectLimitsFromBatch(batchSelected);
        customers = db.SqlCustomer().selectCustomersFromBatch(batchSelected);
        tvBatchName.setText(batch.getBatchName());
        tvInstrument.setText(batch.getInstrument());
        tvTotObj.setText(batch.getTotObj());
        tvForecastDate.setText(batch.getForecastDate());
        tvForecastFinDateStr.setText(batch.getForecastFinDate());
        tvMinDueDateStr.setText(batch.getMinDueDate());
        tvForecastUser.setText(batch.getForecastUser());
        tvBatchQualityValue.setText(batch.getBatchQualityLevel());
        tvBatchDueDateTypeValue.setText(batch.getBatchDueDateTypeValue());
        tvBatchPhase.setText(batch.getBatchPhase());
        ibGeneraFile.setEnabled((batch.getStatus().equals(EntityBatch.COMPLETE)));
        ibLimiti.setEnabled(limits.size()>0);
        int countResultTypeSpinner = db.SqlResult().countResultTypeSpinner(batchSelected)/tests.size();
        analysisComponent.forEach(ac->{
            int indexAnalysisComp = analysisComponent.indexOf(ac);
            tvAnalysisList.get(indexAnalysisComp).setVisibility(View.VISIBLE);
            tvComponentList.get(indexAnalysisComp).setVisibility(View.VISIBLE);
            ibPropagationResultList.get(indexAnalysisComp).setVisibility(View.VISIBLE);
            tvAnalysisList.get(indexAnalysisComp).setText(ac.getAnalysis());
            tvComponentList.get(indexAnalysisComp).setText(ac.getEnComponentName());
            GraphicManagement.setAutomaticWidth(tvAnalysisList.get(indexAnalysisComp),ac.getResultType(),countResultTypeSpinner,analysisComponent.size());
            GraphicManagement.setAutomaticWidth(tvComponentList.get(indexAnalysisComp),ac.getResultType(),countResultTypeSpinner,analysisComponent.size());
            GraphicManagement.setAutomaticWidth(ibPropagationResultList.get(indexAnalysisComp),ac.getResultType(),countResultTypeSpinner,analysisComponent.size());
        });
        rvResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvResult.setAdapter(AdapterBatchManagement.createAdapter(this,countResultTypeSpinner,batchObjects,samples,results,listEntries,analysisComponent,
                limits,customers,batch,listDistinctSampleNumber,driveAccount));
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onPostResume() {
        super.onPostResume();
        ibNote.setOnClickListener(view -> {
            Dialog note = DialogCustom.createDialog.apply(this,R.layout.batch_man_note);
            EditText nLab = note.findViewById(R.id.batch_man_note_operative);
            EditText nUff = note.findViewById(R.id.batch_man_note_ufficiali);
            EditText listCampioni = note.findViewById(R.id.batch_man_note_lista_campioni);
            String defaultNote = samples.stream()
                    .map(sample->new StringBuilder(sample.getTextId()+":\n"))
                    .reduce(StringBuilder::append)
                    .orElse(new StringBuilder("")).toString();
            listCampioni.setText(defaultNote);
            if(batch.getNoteLab().equals(EntityBatch.NOTE_VUOTE)) {
                batch.setNoteLab(defaultNote);
                batch.update(batch,db);
            }
            nLab.setText(batch.getNoteLab());
            nUff.setText(batch.getNoteUfficiali());
            note.show();
            nLab.addTextChangedListener(TextWatcher.getTextWatch(editable -> {
                batch.setNoteLab(editable.toString());
                batch.update(batch,db);
            }));
            nUff.addTextChangedListener(TextWatcher.getTextWatch(editable -> {
                batch.setNoteUfficiali(editable.toString());
                batch.update(batch,db);
            }));
        });
        ibLimiti.setOnClickListener(view->{
            Dialog limiti = DialogCustom.createDialog.apply(this,R.layout.batch_man_limiti);
            ListView lvLimiti = limiti.findViewById(R.id.batch_man_limiti_lista);
            AdapterLimits adapterLimits = new AdapterLimits(this, limits);
            lvLimiti.setAdapter(adapterLimits);
            limiti.show();
        });
        ibManualeLab.setOnClickListener(view->{
            ProgressDialog pdLabManual = DialogCustom.setOptionProgressDialog(this,"Search Lab Manual","Searching Lab Manual...");
            pdLabManual.show();
            GoogleAccountCredential driveCredential = GoogleDrive.getDriveCredential.apply(this, driveAccount);
            Drive driveService = GoogleDrive.getDriveService.apply(MainActivity.APP_NAME, driveCredential);
            Executors.newSingleThreadExecutor().execute(DownloadLabManual.download(this,this,driveService,pdLabManual));
        });
        ibSalva.setOnClickListener(view->{
            ProgressDialog pdSave = DialogCustom.setOptionProgressDialog(this,"Save","Saving data...");
            pdSave.show();
            Executors.newSingleThreadExecutor().execute(UploadProcess.uploadResultAndInfoBatch(this,driveAccount,db,batch,results,pdSave,this));
        });
        ibGeneraFile.setOnClickListener(view -> {
            ProgressDialog pdUpload = DialogCustom.setOptionProgressDialog(this,"Upload File","Create and upload file csv and pdf.");
            pdUpload.show();
            List<java.io.File> filesToUpload = new ArrayList<>();
            filesToUpload.add(FileCsv.create(batch,batchObjects,results,this,pdUpload));
            filesToUpload.add(FilePdf.create(this,pdUpload,true,batch,batchObjects,results,samples,analysisComponent,listEntries));
            if(Laboratorio.getLab(driveAccount.getEmail()).equals(Laboratorio.MOE) && db.SqlFibre().selectFibersFromBatch(batchSelected).size()>0)
                choseModFibersToPrint(filesToUpload,pdUpload);
            if(!batch.getNoteLab().equals(EntityBatch.NOTE_VUOTE))
                filesToUpload.add(FilePdf.create(this,pdUpload,false,batch,batchObjects,results,samples,analysisComponent,listEntries));
            FileDrive.upload(filesToUpload,driveService,pdUpload,this,batch,driveAccount,db,this);
        });
        ibRecoveryData.setOnClickListener(view->DownloadAndForceResult.run(driveAccount,results,db,this,this));
        analysisComponent.forEach(ac->ibPropagationResultList.get(analysisComponent.indexOf(ac)).setOnClickListener(
                PropagationButton.clickOnPropagationButton(ac,batchObjects,db,this,this,batch.getForecastDate(),batch)));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        batch.calculateBatchLocStatus(db);
        finish();
    }
    private void choseModFibersToPrint(List<File> filesToUpload, ProgressDialog pdUpload){
        List<EntityModule> modules = db.SqlModule().selectModulesFromSingleAnalysis(analysisComponent.get(0).getAnalysis());
        modules.forEach(m->{
            ModulesMoe moduleSelected = ModulesMoe.valueOf(m.getModule());
            switch (moduleSelected){
                case MOD2372:
                    filesToUpload.add(MOD2372Pdf.create(this,pdUpload,batchSelected,batchObjects,db));
                    break;
                case MOD2548:
                    filesToUpload.add(MOD2548Pdf.create(this,pdUpload,batchSelected,batchObjects,db));
                    break;
                case MOD2805:
                    filesToUpload.add(MOD2805Pdf.create(this,pdUpload,batchSelected,batchObjects,db));
                    break;
            }
        });
    }
}