package com.example.limsebatchmanagement.Adapter;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.Typeface;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.limsebatchmanagement.BatchManagement.*;
import com.example.limsebatchmanagement.CalcoliLab.MOE.*;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.Enum.*;
import com.example.limsebatchmanagement.ModulesMoe.MOD2372.MOD2372Management;
import com.example.limsebatchmanagement.ModulesMoe.MOD2548.MOD2548Management;
import com.example.limsebatchmanagement.ModulesMoe.MOD2805.MOD2805Management;
import com.example.limsebatchmanagement.R;
import com.example.limsebatchmanagement.Utility.*;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AdapterBatchManagement extends RecyclerView.Adapter<AdapterBatchManagement.ViewHolder> {
    Context context;
    List<EntityBatchObject> batchObjects;
    List<EntitySample> samples;
    List<EntityResult> results;
    Map<String,String> listEntries;
    List<EntityAnalysisComponent> analComps;
    List<EntityLimit> limits;
    List<EntityCustomer> customers;
    List<EntityModule> modules = new ArrayList<>();
    EntityBatch batch;
    int countResTypeSpinner;
    DatabaseBuilder db;
    List<Integer> listDistinctSampleNumber;
    GoogleSignInAccount driveAccount;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final int MAX_RESULT = 26;
        private final TextView sample;
        private final List<EditText> etResults = new ArrayList<>();
        private final List<Spinner> spResults = new ArrayList<>();
        public ViewHolder(View view) {
            super(view);
            sample = view.findViewById(R.id.batch_management_lista_sample);
            for(int i=1;i<MAX_RESULT;i++){
                int idEditText = itemView.getResources().getIdentifier("batch_man_lista_header_result_item"+i, "id", itemView.getContext().getPackageName());
                int idSpinner = itemView.getResources().getIdentifier("batch_man_lista_header_spinner_item"+i, "id", itemView.getContext().getPackageName());
                etResults.add(itemView.findViewById(idEditText));
                spResults.add(itemView.findViewById(idSpinner));
            }
        }
        public TextView getSample(){return sample;}
        public List<EditText> getEtResults(){return etResults;}
        public List<Spinner> getSpResults(){return spResults;}
    }
    private AdapterBatchManagement(Context context, int countResTypeSpinner, List<EntityBatchObject> batchObjects, List<EntitySample> samples, List<EntityResult> results,
                                   Map<String,String> listEntries, List<EntityAnalysisComponent> analComps, List<EntityLimit> limits, List<EntityCustomer> customers, EntityBatch batch,
                                   List<Integer> listDistinctSampleNumber, GoogleSignInAccount driveAccount)
    {
        this.context=context;
        this.batchObjects=batchObjects;
        this.samples=samples;
        this.results=results;
        this.listEntries=listEntries;
        this.analComps=analComps;
        this.limits=limits;
        this.customers=customers;
        this.countResTypeSpinner=countResTypeSpinner;
        this.batch=batch;
        this.listDistinctSampleNumber=listDistinctSampleNumber;
        db = DatabaseBuilder.getDbInstance(context);
        this.driveAccount = driveAccount;
    }
    public static AdapterBatchManagement createAdapter(Context context, int countResTypeSpinner, List<EntityBatchObject> batchObjects, List<EntitySample> samples,
                                                       List<EntityResult> results, Map<String,String> listEntries, List<EntityAnalysisComponent> analysisComponents,
                                                       List<EntityLimit> limits, List<EntityCustomer> customers, EntityBatch batch, List<Integer> listDistinctSampleNumber,
                                                       GoogleSignInAccount driveAccount){
        return new AdapterBatchManagement(context,countResTypeSpinner,batchObjects,samples,results,listEntries,analysisComponents,limits,customers,batch,
                listDistinctSampleNumber,driveAccount);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_man_lista_batch_management, parent, false));
    }
    @Override
    public int getItemCount() {
        return (Objects.isNull(listDistinctSampleNumber))? 0:listDistinctSampleNumber.size();
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EntitySample sample = EntitySample.getSampleFromSampleNumber.apply(new ArrayList<>(samples),listDistinctSampleNumber.get(holder.getAbsoluteAdapterPosition()));
        EntityBatchObject batchObject = EntityBatchObject.getBatchObjectFromSample.apply(batchObjects,sample.getSampleNumber());
        evidenceSampleByCustomer.accept(holder,sample);
        setGraphicReciclerViewElements(holder,batchObject,db);
        holder.getSample().setText(sample.getTextId());
        holder.getSample().setOnClickListener(view -> {
            if(!Laboratorio.getLab(this.driveAccount.getEmail()).equals(Laboratorio.MOE))
                sampleOnClickListenerStandard(sample);
            else{
                Set<String> setAnalysis = new HashSet<>();
                analComps.forEach(ac->{setAnalysis.add(ac.getAnalysis());});
                modules = db.SqlModule().selectModulesFromAnalysis(setAnalysis);
                if(modules.size()>0)
                    sampleOnClickListenerMoe(sample,holder);
                else
                    sampleOnClickListenerStandard(sample);
            }
        });
        analComps.forEach(ac->{
            EditText et = holder.getEtResults().get(analComps.indexOf(ac));
            Spinner sp = holder.getSpResults().get(analComps.indexOf(ac));
            int index = analComps.indexOf(ac);
            et.addTextChangedListener(TextWatcher.getTextWatchResult(index,listDistinctSampleNumber,analComps,results,samples,db,context,et,limits,holder,batch,
                    holder.getEtResults()));
            et.setOnLongClickListener(LongClick.getLongClick(index,listDistinctSampleNumber,analComps,results,samples,db,context,et,limits,holder,batch));
            sp.setOnItemSelectedListener(SelectedListener.getListenereRes(index,listDistinctSampleNumber,analComps,results,samples,db,context,et,limits,holder,batch));
        });
    }
    private final BiConsumer<ViewHolder, EntitySample> evidenceSampleByCustomer = (h, s)-> customers.forEach(cust->{
        h.getSample().setTypeface(null, Typeface.NORMAL);
        if(cust.descrizioneCliente.equals(s.getCustomerDescription()) && cust.attenzionare)
            h.getSample().setTypeface(null, Typeface.BOLD);
    });
    private void setGraphicReciclerViewElements(ViewHolder v, EntityBatchObject bo, DatabaseBuilder db){
        for(int countAc = 0;countAc<analComps.size();countAc++){
            String resulType = analComps.get(countAc).getResultType();
            String analysis = analComps.get(countAc).getAnalysis();
            String enComponentName = analComps.get(countAc).getEnComponentName();
            EditText currEditText = v.getEtResults().get(countAc);
            Spinner currSpinner = v.getSpResults().get(countAc);
            GraphicManagement.setAutomaticWidth(currEditText,resulType,countResTypeSpinner,analComps.size());
            GraphicManagement.setAutomaticWidth(currSpinner,resulType,countResTypeSpinner,analComps.size());
            currEditText.setText("");
            GraphicManagement.setDefaultGraphicElementRecyclerView(context,currEditText);
            GraphicManagement.setDefaultGraphicElementRecyclerView(context,currSpinner);
            EntityResult resToPrint = EntityResult.getResultFromSampleAnalysisComponent(new ArrayList<>(results),bo.getSampleNUmber(),analysis,enComponentName);
            List<String> listEntriesSpecific;
            if(Objects.nonNull(resToPrint)){
                listEntriesSpecific = db.SqlListEntry().selectEntryFromList(resToPrint.getListKey());
                GraphicManagement.setGraphicElementsReciclerViewForResultToPrint(context, currSpinner, currEditText, resToPrint,listEntriesSpecific, analComps.get(countAc));
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private void sampleOnClickListenerStandard(EntitySample sample){
        Dialog noteSample = DialogCustom.createDialog.apply(context,R.layout.batch_man_note_sample);
        EditText etNoteSample  = noteSample.findViewById(R.id.batch_man_note_sample_text);
        TextView tvSampleDesc = noteSample.findViewById(R.id.batch_man_note_sample_desc_text);
        TextView tvCustomerDesc = noteSample.findViewById(R.id.batch_man_note_sample_cust_desc_text);
        TextView tvMatrix = noteSample.findViewById(R.id.batch_man_note_sample_matrix);
        etNoteSample.setText(sample.getTextId() + ": ");
        tvSampleDesc.setText("Sample Description: "+sample.getDescription());
        tvCustomerDesc.setText("Customer Description: "+sample.getCustomerDescription());
        tvMatrix.setText("Sample Matrix: "+sample.getMatrix());
        noteSample.show();
//        final String[] notesToAdd = {""};
//        batch.setNoteLab(batch.getNoteLab() + '\n');
//        etNoteSample.addTextChangedListener(TextWatcher.getTextWatch(editable -> {
//            batch.setNoteLab(batch.getNoteLab() + editable.toString().replace(notesToAdd[0],""));
//            notesToAdd[0] = editable.toString();
//            batch.update(batch,db);
//        }));
    }
    private void sampleOnClickListenerMoe(EntitySample sample,@NonNull ViewHolder holder){
        String[] modulesToSelect = new String[modules.size()];
        modules.forEach(module->{modulesToSelect[modules.indexOf(module)] = module.getModule() + " - " + module.getDescription();});
        AlertDialog.Builder dialogMultiModuleMoe = new AlertDialog.Builder(context);
        dialogMultiModuleMoe.setTitle("Scegliere quale modulo compilare:");
        dialogMultiModuleMoe.setItems(modulesToSelect,managementModuleSelection(sample,modules,holder));
        dialogMultiModuleMoe.show();
    }
    private DialogInterface.OnClickListener managementModuleSelection(EntitySample sample, List<EntityModule> modules,@NonNull ViewHolder holder){
        return (d,ind)->{
            int iEt = 0;
            EntitySampleMoe sampleMoe = db.SqlSampleMoe().selectSingleSampleMoe(sample.getSampleNumber());
            ModulesMoe moduleSelected = ModulesMoe.valueOf(modules.get(ind).getModule());
            switch (moduleSelected){
                case MOD2805:
                    iEt = analComps.stream().map(EntityAnalysisComponent::getEnComponentName).collect(Collectors.toList()).indexOf(G_ASB_SEM_MASS.INITIAL_QTY_DESC);
                    MOD2805Management mod2805 = MOD2805Management.create(context,db,sampleMoe,sample.getTextId(),holder.getEtResults().get(iEt));
                    mod2805.getDialogMod2805().show();
                    break;
                case MOD2372:
                    iEt = analComps.stream().map(EntityAnalysisComponent::getEnComponentName).collect(Collectors.toList()).indexOf(G_ASB_SEM_MASS.ASBESTOS_DESC);
                    MOD2372Management mod2372 = MOD2372Management.create(context,db,sampleMoe,sample.getTextId(),holder.getEtResults().get(iEt));
                    mod2372.getDialogMod2372().show();
                    break;
                case MOD2548:
                    iEt = analComps.stream().map(EntityAnalysisComponent::getEnComponentName).collect(Collectors.toList()).indexOf(G_TOTALFIBRES_OM_A.TOTALFIBRES_DESC);
                    MOD2548Management mod2548 = MOD2548Management.create(context,db,sampleMoe,sample.getTextId(),holder.getEtResults().get(iEt));
                    mod2548.getDialogMod2548().show();
                    break;
                case MOD2922:
                    Toast.makeText(context,"MOD2922",Toast.LENGTH_LONG).show();
                    break;
            }
        };
    }
}
