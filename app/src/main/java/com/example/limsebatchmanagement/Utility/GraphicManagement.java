package com.example.limsebatchmanagement.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.R;
import java.util.*;
import java.util.function.BiPredicate;

@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("UseCompatLoadingForDrawables")
public class GraphicManagement {
    private static final int MAX_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels -270;

    public static <T extends View> void  setAutomaticWidth (T t, String resultType, Integer countSpinnerDate, Integer countAnalysisComponent) {
        ConstraintLayout.LayoutParams parTextView = (ConstraintLayout.LayoutParams) t.getLayoutParams();
        parTextView.width = getAutoWidth(countSpinnerDate,countAnalysisComponent);
        if(resultType.equals(EntityResult.COMPONENT_DATE) || resultType.equals(EntityResult.COMPONENT_SPINNER))
            parTextView.width = getWidthSpinnerDate(countAnalysisComponent,countSpinnerDate);
        t.setLayoutParams(parTextView);
    };
    private static int getAutoWidth(int countSpinner,int countAnalysisComponent){
        int autoWidth = (MAX_WIDTH - (MAX_WIDTH/8) * countSpinner) / (10 - countSpinner);
        if(countAnalysisComponent>10)
            autoWidth = (MAX_WIDTH - (MAX_WIDTH/10) * countSpinner) / (countAnalysisComponent - countSpinner);
        return autoWidth;
    }
    private static int getWidthSpinnerDate(int countAnalysisComponent,int countSpinner){
        int autoWidth = MAX_WIDTH/8;
        if(countAnalysisComponent>10 || countSpinner >= 6)
            autoWidth = MAX_WIDTH/10;
        if(countAnalysisComponent>12 || countSpinner >= 6)
            autoWidth = MAX_WIDTH/11;
        return autoWidth;
    }

    public static <T extends View> void setDefaultGraphicElementRecyclerView(Context c, T view){
        view.setVisibility(View.VISIBLE);
        view.setEnabled(false);
        view.setBackground(c.getDrawable(R.drawable.background_edittext_disable));
    }
    public static void setGraphicElementsReciclerViewForResultToPrint(Context c, Spinner sp, EditText et, EntityResult resToPrint, List<String> listEntries,
                                                                      EntityAnalysisComponent ac){
        if(ac.getResultType().equals(EntityResult.COMPONENT_SPINNER))
            GraphicManagement.setGraphicSpinnerReciclerView(c,sp,et,resToPrint,listEntries);
        else
            GraphicManagement.setGraphicEditTextReciclerView(c,et,resToPrint);
    }

    private static void setGraphicSpinnerReciclerView(Context c, Spinner sp, EditText et, EntityResult resToPrint, List<String> listEntries){
        if(resToPrint.getDisplayed().equals(EntityResult.DISPLAYED)){
            et.setVisibility(View.INVISIBLE);
            sp.setEnabled(true);
            sp.setBackground(c.getDrawable(R.drawable.background_titleresult));
            ArrayAdapter<String> adapterSpResult = new ArrayAdapter<>(c,R.layout.batch_man_lista_analysis_result_spinner_default,listEntries);
            adapterSpResult.setDropDownViewResource(R.layout.batch_man_lista_analysis_result_spinner_dropdown);
            sp.setAdapter(adapterSpResult);
            sp.setSelection(0);
            if(!resToPrint.getEntry().equals(EntityResult.ENTRY_VUOTA)){
                int resSpinnerSelected = 0;
                for(String listEntry:listEntries){
                    if(listEntry.equals(resToPrint.getEntry()))
                        sp.setSelection(resSpinnerSelected);
                    resSpinnerSelected++;
                }
            }
        }
    }
    private static void setGraphicEditTextReciclerView(Context c,EditText et, EntityResult resToPrint){
        et.setText("");
        et.setEnabled(false);
        et.setBackground(c.getDrawable(R.drawable.background_edittext_disable));
        if(resToPrint.getDisplayed().equals(EntityResult.DISPLAYED)){
            et.setEnabled(true);
            et.setBackground(c.getDrawable(R.drawable.background_titleresult));
            et.setText(resToPrint.getEntry());
            if(resToPrint.getTemporary().equals(EntityResult.ENTRY_TEMPORANEA))
                et.setBackground(c.getDrawable(R.drawable.background_edittext_temporary));
        }
    }
    public static void setGraphicEditTextCompliance(Context c, EditText et, EntityResult r, EntityLimit limit, boolean entryNotDouble){
        et.setBackground(c.getDrawable(R.drawable.background_titleresult));
        if(!entryNotDouble){
            if(r.getTemporary().equals(EntityResult.ENTRY_TEMPORANEA))
                et.setBackground(c.getDrawable(R.drawable.background_edittext_temporary));
            if(Objects.nonNull(limit) && checkLimits.test(limit,r.getEntry())) {
                et.setBackground(c.getDrawable(R.drawable.background_titleresult_error));
                Toast.makeText(c,"Il valore inserito non è conforme ai limiti configurati in Google Drive",Toast.LENGTH_SHORT).show();
            }
        } else {
            et.setBackground(c.getDrawable(R.drawable.background_titleresult_error));
            Toast.makeText(c,"Il valore inserito non è un numero valido",Toast.LENGTH_SHORT).show();
        }
    }
    private static BiPredicate<EntityLimit,String> checkLimits = (l, e) ->{
        if(!e.equals(EntityResult.ENTRY_VUOTA))
            return Double.parseDouble(e) < l.getLimiteMin() || Double.parseDouble(e) > l.getLimiteMax();
        return true;
    };
}