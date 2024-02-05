package com.example.limsebatchmanagement;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.View;
import android.widget.*;
import androidx.annotation.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.limsebatchmanagement.Adapter.AdapterBatch;
import com.example.limsebatchmanagement.Adapter.AdapterSchedulerErrorLog;
import com.example.limsebatchmanagement.Archiviazione.FileCsv;
import com.example.limsebatchmanagement.Archiviazione.FilePdf;
import com.example.limsebatchmanagement.BackupDatabase.BackupManagement;
import com.example.limsebatchmanagement.BatchManagement.BatchManagement;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.DriveManagement.FileDrive;
import com.example.limsebatchmanagement.DriveManagement.GoogleDrive;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.Manual.DownloadAppManual;
import com.example.limsebatchmanagement.DatabaseFirebase.Download.SchedulerDownload;
import com.example.limsebatchmanagement.Utility.*;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.Drive;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;




@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends Activity {
    final int DRIVE_CONNECTION_REQUEST = 1,LOG_OUT_REQUESTE = 2;
    private static int logOutReq=0;
    public static final String APP_NAME = "LimsE Batch Management";
    TextView email, version, lastDownload;
    ImageButton ibMassiveExport,ibManual,ibScheduler,ibUpdateDataFromDrive;
    ListView lvBatch;
    SearchView svFilter;
    ConstraintLayout layoutHeader,layoutMenu,layoutListBatch;
    GoogleSignInAccount driveAccount;
    GoogleAccountCredential driveCredential;
    Drive driveService;
    GoogleSignInClient signInClient;
    List<EntityBatch> batches = new ArrayList<>();
    DatabaseBuilder db;
    String appVersion, lastCompletedDownloaded;
    EntityDateTimeDownloadCompleted EntityDTDownloadedCompleted;
    DatabaseReference fireBaseDb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutHeader = findViewById(R.id.layout_header);
        layoutMenu = findViewById(R.id.layout_menu);
        layoutListBatch = findViewById(R.id.layout_list_batch);
        email = findViewById(R.id.textviex_email);
        version = findViewById(R.id.main_versione);
        lastDownload = findViewById(R.id.main_last_download);
        lvBatch = findViewById(R.id.lista_batch);
        svFilter = findViewById(R.id.batch_filter);
        ibMassiveExport=findViewById(R.id.button_export_csv_massivo);
        ibManual = findViewById(R.id.button_manuale_app);
        ibScheduler = findViewById(R.id.button_scheduler);
        ibUpdateDataFromDrive = findViewById(R.id.button_update_data_drive);

    }

    @Override
    public void onStart() {
        super.onStart();
        db = DatabaseBuilder.getDbInstance(this);
        if(Objects.isNull(db.SqlDateTimeDownloadCompleted().select())){
            DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            EntityDateTimeDownloadCompleted entityDateTimeDownloadCompleted = EntityDateTimeDownloadCompleted.getDateTime(LocalDateTime.MIN.format(f));
            db.SqlDateTimeDownloadCompleted().insert(entityDateTimeDownloadCompleted);
        } else
            EntityDTDownloadedCompleted = db.SqlDateTimeDownloadCompleted().select();
        signInClient = GoogleDrive.signInIntent.apply(this);
        Task<GoogleSignInAccount> task = signInClient.silentSignIn();
        if (task.isSuccessful()){
            driveAccount = task.getResult();
            driveCredential = GoogleDrive.getDriveCredential.apply(this, driveAccount);
            driveService = GoogleDrive.getDriveService.apply(APP_NAME, driveCredential);
        }
        if(Objects.isNull(driveAccount)){
            Intent signInIntentDrive = signInClient.getSignInIntent();
            startActivityForResult(signInIntentDrive,DRIVE_CONNECTION_REQUEST);
        } else {
            batches = db.SqlBatch().selectAllBatches();
            email.setVisibility(View.VISIBLE);
            email.setText(driveAccount.getEmail());
            version.setVisibility(View.VISIBLE);
            version.setText(appVersion);
            lastDownload.setVisibility(View.VISIBLE);
            lastDownload.setText(lastCompletedDownloaded);
            batches.sort(Comparator.comparing(EntityBatch::getBatchName));
            BackupManagement.startExecutors(driveService,driveAccount,db,this);
            if(svFilter.getQuery().length()>0){
                List<EntityBatch> batchesFiltered = new ArrayList<>(batches);
                batchesFiltered.removeIf(entityBatch -> !entityBatch.getBatchName().toLowerCase().contains(svFilter.getQuery()));
                lvBatch.setAdapter(AdapterBatch.getInstance(this, batchesFiltered));
            } else
                lvBatch.setAdapter(AdapterBatch.getInstance(this, batches));
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DRIVE_CONNECTION_REQUEST) {
            driveAccount = GoogleDrive.getDriveAccount.apply(data);
            driveCredential = GoogleDrive.getDriveCredential.apply(this, driveAccount);
            driveService = GoogleDrive.getDriveService.apply(APP_NAME, driveCredential);
            SdCard.writePermissionRequest.accept(this);
            onStart();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        lvBatch.setOnItemClickListener((adapterView, view, i, l) -> {
            AdapterBatch adapterBatch = (AdapterBatch) adapterView.getAdapter();
            EntityBatch batchSelected = adapterBatch.getBatch(i);
            Intent batchManagement = new Intent(this, BatchManagement.class);
            batchManagement.putExtra("mainBatchSelected",batchSelected.getBatchName());
            batchManagement.putExtra("driveAccount",driveAccount);
            startActivity(batchManagement);
            onStop();
        });
        svFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {return false;}
            @Override
            public boolean onQueryTextChange(String s) {
                List<EntityBatch> filterBatches = new ArrayList<>(batches);
                if(s.length()>0)
                    filterBatches.removeIf(batch -> !batch.getBatchName().toLowerCase().contains(s.toLowerCase()));
                filterBatches.sort(Comparator.comparing(EntityBatch::getBatchName));
                lvBatch.setAdapter(AdapterBatch.getInstance(MainActivity.this, filterBatches));
                return false;
            }
        });
        ibUpdateDataFromDrive.setOnClickListener(view->{
            ProgressDialog pd = DialogCustom.createProgressDialogDownloadFromDrive(this);
            SchedulerDownload.startExecutors(driveAccount,db,pd,this);
        });
        ibScheduler.setOnClickListener(view->{
            Dialog dialogScheduler = DialogCustom.createDialog.apply(this,R.layout.activity_main_scheduler);
            ListView lvErrorLog = dialogScheduler.findViewById(R.id.lista_error_log);
            ImageButton ibUpdateSched = dialogScheduler.findViewById(R.id.button_aggiorna_scheduler);
            AdapterSchedulerErrorLog adapterSchedulerErrorLog = AdapterSchedulerErrorLog.getInstance(this,db.SqlLogDownloadSched().selectAllLogs());
            ibUpdateSched.setOnClickListener(update->adapterSchedulerErrorLog.notifyDataSetChanged());
            lvErrorLog.setAdapter(adapterSchedulerErrorLog);
            dialogScheduler.show();
        });
        ibManual.setOnClickListener(view -> {
            ProgressDialog pdAppManual = DialogCustom.setOptionProgressDialog(this,"Search App Manual","Searching App Manual...");
            pdAppManual.show();
            GoogleAccountCredential driveCredential = GoogleDrive.getDriveCredential.apply(this, driveAccount);
            Drive driveService = GoogleDrive.getDriveService.apply(MainActivity.APP_NAME, driveCredential);
            Executors.newSingleThreadExecutor().execute(DownloadAppManual.download(this,this,driveService,pdAppManual));
        });
        ibMassiveExport.setOnClickListener(view -> {
            List<EntityBatch> completeBatches = db.SqlBatch().selectCompleteBatch();
            ProgressDialog pd = DialogCustom.createProgressDialogExportMassivo(this,completeBatches);
            pd.setMessage("Creazione massiva dei file csv e pdf.");
            pd.show();
            Map<File,EntityBatch> mapFileBatch = new HashMap<>();
            if(completeBatches.size()>0){
                completeBatches.forEach(batch -> {
                    List<File> files = new ArrayList<>();
                    List<EntityBatchObject> batchObjects = db.SqlBatchObject().selectBatchObjectsFromBatch(batch.getBatchName());
                    List<EntityResult> results = db.SqlResult().selectResultsFromBatch(batch.getBatchName());
                    List<EntitySample> samples = db.SqlSample().selectSamplesFromBatch(batch.getBatchName());
                    List<EntityAnalysisComponent> analysisComponents = db.SqlResult().selectAnalysisNameDistinctFromBatch(batch.getBatchName());
                    Map<String,String> mapListEntries = new HashMap<>();
                    db.SqlListEntry().selectEntryFromBatch(batch.getBatchName()).forEach(name->mapListEntries.compute(name,(k,v)-> (v!=null)? v:db.SqlListEntry().selectValueFromName(name)));
                    files.add(FileCsv.create(batch,batchObjects,results,this,pd));
                    files.add(FilePdf.create(this,pd,true,batch,batchObjects,results,samples,analysisComponents,mapListEntries));
                    if(!batch.getNoteLab().equals(EntityBatch.NOTE_VUOTE))
                        files.add(FilePdf.create(this,pd,false,batch,batchObjects,results,samples,analysisComponents,mapListEntries));
                    files.forEach(file -> mapFileBatch.compute(file,(k,v)-> (v!=null)? v:batch));
                });
                FileDrive.uploadMassivo(mapFileBatch,driveService,this,driveAccount,db,this,pd);
            } else
                pd.dismiss();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(logOutReq==LOG_OUT_REQUESTE) {
            BackupManagement.shutDownBackup();
            GoogleDrive.logOut.accept(signInClient);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logOutReq = LOG_OUT_REQUESTE;
        finish();
    }
}