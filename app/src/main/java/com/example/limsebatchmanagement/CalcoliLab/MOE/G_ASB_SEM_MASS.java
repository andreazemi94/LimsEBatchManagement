package com.example.limsebatchmanagement.CalcoliLab.MOE;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.EntityFibre;
import com.example.limsebatchmanagement.Enum.CategoryFibers;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class G_ASB_SEM_MASS {
    private static final DecimalFormat doubleFormat = new DecimalFormat("##.###");
    public static final String INITIAL_QTY_DESC = "Initial quantity";
    public static final String ASBESTOS_DESC = "Asbestos-r";
    public static String calcAsbestosTotal(List<EntityFibre> fibers){
        List<Double> weightFibers = new ArrayList<>();
        fibers.stream().filter(f-> CategoryFibers.getCategory(f.getTipoFibra()).equals(CategoryFibers.AMIANTO))
                .filter(f->!f.getTipoFibra().equals("-") && !f.getDiametro().equals("0") && !f.getLunghezza().equals("0"))
                .forEach(entityFibre -> {
                    try{
                        double diameter = Double.parseDouble(entityFibre.getDiametro());
                        double lenght = Double.parseDouble(entityFibre.getLunghezza());
                        double volume = ((Math.pow(diameter/2,2))*3.14*lenght)/1000000000;
                        double density = (entityFibre.getTipoFibra().equals("Crisotilo"))? 2.6:3;
                        double weight = volume*density*1000000;
                        weightFibers.add(weight);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
        return doubleFormat.format(weightFibers.stream().mapToDouble(Double::doubleValue).sum()).replace(",",".");
    }

}
