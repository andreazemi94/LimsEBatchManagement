package com.example.limsebatchmanagement.CalcoliLab.MOE;

import android.os.Build;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class G_TOTALFIBRES_OM_A {
    private static final String ANALYSIS_AIR = "G_TOTALFIBRES_OM_A";
    private static final String ANALYSIS_WATER = "G_TOTALFIBRES_OM_A";
    private static final String FILTER_AREA_ANALIZED = "FILTER_AREA_ANALIZED";
    private static final String FILTER_AREA_ANALIZED_DESC = "filter analized area";
    private static final String LFI = "LFI_TOTALFIBRES-R";
    private static final String LFI_DESCRIPTION = "LFI total fibers-r";
    private static final String LFS = "LFS_TOTALFIBRES-R";
    private static final String LFS_DESCRIPTION = "LFS total fibers-r";
    private static final String FILTER_AREA_TOTAL = "FILTER_AREA_TOTAL";
    private static final String FILTER_AREA_TOTAL_DESC = "filter total area";
    public static final String MICROSCOPIC_FIELDS = "MICROSCOPIC_FIELDS";
    public static final String TOTALFIBRES = "TOTALFIBRES-R";
    public static final String TOTALFIBRES_DESC = "Total fibers-r";
    private static final String FILTER_DIAMETER = "FILTER_DIAMETER";
    private static final double RETICOLO_W_B = 0.00785398163397448;
    private static final DecimalFormat doubleFormat = new DecimalFormat("##.###");
    private static final Predicate<String> checkAnalysisAir = s-> s.equals(ANALYSIS_AIR);
    private static final Predicate<String> checkAnalysisWater = s-> s.equals(ANALYSIS_WATER);
    public static void calcSingleFilterAnalizedArea (List<EntityResult> results,EntityResult resToUpdate, DatabaseBuilder db,
                                               List<EntityAnalysisComponent> ac,List<EditText> editTexts){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(MICROSCOPIC_FIELDS)){
            results.stream()
                    .filter(res->res.getName().equals(FILTER_AREA_ANALIZED) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(filterAreaAnalized ->{
                        try{
                            double newValueFilterAnalizedArea = Double.parseDouble(resToUpdate.getEntry())*RETICOLO_W_B;
                            filterAreaAnalized.setEntry(doubleFormat.format(newValueFilterAnalizedArea).replace(",","."));
                            filterAreaAnalized.update(filterAreaAnalized,db);
                            editTexts.get(IntStream.range(0,ac.size())
                                    .filter(i->(checkAnalysisAir.or(checkAnalysisWater).test(ac.get(i).getAnalysis()))
                                            && ac.get(i).getEnComponentName().equals(FILTER_AREA_ANALIZED_DESC))
                                    .findFirst().orElse(-1)).setText(filterAreaAnalized.getEntry());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcMultiFilterAnalizedArea (String batch,EntityResult resToUpdate, DatabaseBuilder db){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(MICROSCOPIC_FIELDS)){
            List<EntityResult> results = db.SqlResult().selectResultsFromBatch(batch);
            results.stream()
                    .filter(res->res.getName().equals(FILTER_AREA_ANALIZED) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(filterAreaAnalized ->{
                        try{
                            double newValueFilterAnalizedArea = Double.parseDouble(resToUpdate.getEntry())*RETICOLO_W_B;
                            filterAreaAnalized.setEntry(doubleFormat.format(newValueFilterAnalizedArea).replace(",","."));
                            filterAreaAnalized.update(filterAreaAnalized,db);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcSingleLFI(List<EntityResult> results,EntityResult resToUpdate, DatabaseBuilder db,
                                                     List<EntityAnalysisComponent> ac,List<EditText> editTexts){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(TOTALFIBRES)){
            results.stream()
                    .filter(res->res.getName().equals(LFI) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(lfi ->{
                        try{
                            if(Double.parseDouble(resToUpdate.getEntry())<2)
                                lfi.setEntry("0");
                            else{
                                double totalFibers = Double.parseDouble(resToUpdate.getEntry());
                                double cv = Math.sqrt(Math.pow(0.2*totalFibers,2)+totalFibers)/totalFibers;
                                double newFli = totalFibers-(1.3*cv*totalFibers);
                                lfi.setEntry(doubleFormat.format(newFli).replace(",","."));
                            }
                            lfi.update(lfi,db);
                            editTexts.get(IntStream.range(0,ac.size())
                                    .filter(i->(checkAnalysisAir.or(checkAnalysisWater).test(ac.get(i).getAnalysis()))
                                            && ac.get(i).getEnComponentName().equals(LFI_DESCRIPTION))
                                    .findFirst().orElse(-1)).setText(lfi.getEntry());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcMultiLFI(String batch,EntityResult resToUpdate, DatabaseBuilder db){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(TOTALFIBRES)){
            List<EntityResult> results = db.SqlResult().selectResultsFromBatch(batch);
            results.stream()
                    .filter(res->res.getName().equals(LFI) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(lfi ->{
                        try{
                            if(Double.parseDouble(resToUpdate.getEntry())<2)
                                lfi.setEntry("0");
                            else{
                                double totalFibers = Double.parseDouble(resToUpdate.getEntry());
                                double cv = Math.sqrt(Math.pow(0.2*totalFibers,2)+totalFibers)/totalFibers;
                                double newFli = totalFibers-(1.3*cv*totalFibers);
                                lfi.setEntry(doubleFormat.format(newFli).replace(",","."));
                            }
                            lfi.update(lfi,db);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcSingleLFS(List<EntityResult> results,EntityResult resToUpdate, DatabaseBuilder db,
                                     List<EntityAnalysisComponent> ac,List<EditText> editTexts){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(TOTALFIBRES)){
            results.stream()
                    .filter(res->res.getName().equals(LFS) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(lfs ->{
                        try{
                            if(Double.parseDouble(resToUpdate.getEntry())==0)
                                lfs.setEntry("2.99");
                            else{
                                double totalFibers = Double.parseDouble(resToUpdate.getEntry());
                                double cv = Math.sqrt(Math.pow(0.2*totalFibers,2)+totalFibers)/totalFibers;
                                double newFls = totalFibers+(2.3*cv*totalFibers);
                                lfs.setEntry(doubleFormat.format(newFls).replace(",","."));
                            }
                            lfs.update(lfs,db);
                            editTexts.get(IntStream.range(0,ac.size())
                                    .filter(i->(checkAnalysisAir.or(checkAnalysisWater).test(ac.get(i).getAnalysis()))
                                            && ac.get(i).getEnComponentName().equals(LFS_DESCRIPTION))
                                    .findFirst().orElse(-1)).setText(lfs.getEntry());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcMultiLFS(String batch,EntityResult resToUpdate, DatabaseBuilder db){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(TOTALFIBRES)){
            List<EntityResult> results = db.SqlResult().selectResultsFromBatch(batch);
            results.stream()
                    .filter(res->res.getName().equals(LFS) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(lfs ->{
                        try{
                            if(Double.parseDouble(resToUpdate.getEntry())==0)
                                lfs.setEntry("2.99");
                            else{
                                double totalFibers = Double.parseDouble(resToUpdate.getEntry());
                                double cv = Math.sqrt(Math.pow(0.2*totalFibers,2)+totalFibers)/totalFibers;
                                double newFls = totalFibers+(2.3*cv*totalFibers);
                                lfs.setEntry(doubleFormat.format(newFls).replace(",","."));
                            }
                            lfs.update(lfs,db);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcSingleFiltTotArea(List<EntityResult> results,EntityResult resToUpdate, DatabaseBuilder db,
                                     List<EntityAnalysisComponent> ac,List<EditText> editTexts){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(FILTER_DIAMETER)){
            results.stream()
                    .filter(res->res.getName().equals(FILTER_AREA_TOTAL) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(filtTotArea ->{
                        try{
                            double filterDiam = Double.parseDouble(resToUpdate.getEntry());
                            double filterAreaTot = Math.pow((filterDiam/2),2)*3.14159265358979;
                            filtTotArea.setEntry(doubleFormat.format(filterAreaTot).replace(",","."));
                            filtTotArea.update(filtTotArea,db);
                            editTexts.get(IntStream.range(0,ac.size())
                                    .filter(i->(checkAnalysisAir.or(checkAnalysisWater).test(ac.get(i).getAnalysis()))
                                            && ac.get(i).getEnComponentName().equals(FILTER_AREA_TOTAL_DESC))
                                    .findFirst().orElse(-1)).setText(filtTotArea.getEntry());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
    public static void calcMultiFiltTotArea(String batch,EntityResult resToUpdate, DatabaseBuilder db){
        if((checkAnalysisAir.or(checkAnalysisWater).test(resToUpdate.getAnalysis())) && resToUpdate.getName().equals(FILTER_DIAMETER)){
            List<EntityResult> results = db.SqlResult().selectResultsFromBatch(batch);
            results.stream()
                    .filter(res->res.getName().equals(FILTER_AREA_TOTAL) && res.getTestNumber()==resToUpdate.getTestNumber())
                    .findFirst()
                    .ifPresent(filtTotArea ->{
                        try{
                            double filterDiam = Double.parseDouble(resToUpdate.getEntry());
                            double filterAreaTot = Math.pow((filterDiam/2),2)*3.14159265358979;
                            filtTotArea.setEntry(doubleFormat.format(filterAreaTot).replace(",","."));
                            filtTotArea.update(filtTotArea,db);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
        }
    }
}
