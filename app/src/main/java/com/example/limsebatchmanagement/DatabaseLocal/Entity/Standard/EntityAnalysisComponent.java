package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.room.*;

@RequiresApi(api = Build.VERSION_CODES.N)
@Entity
public class EntityAnalysisComponent {
    @ColumnInfo(name = "EN_COMPONENT_NAME")
    private String enComponentName;
    @ColumnInfo(name = "ANALYSIS")
    private String analysis;
    @ColumnInfo(name = "RESULT_TYPE")
    private String resultType;

    public String getEnComponentName() {return enComponentName;}
    public String getAnalysis() {return analysis;}
    public String getResultType() {return resultType;}
    public void setEnComponentName(String enComponentName) {this.enComponentName = enComponentName;}
    public void setAnalysis(String analysis) {this.analysis = analysis;}
    public void setResultType(String resultType) {this.resultType = resultType;}
}
