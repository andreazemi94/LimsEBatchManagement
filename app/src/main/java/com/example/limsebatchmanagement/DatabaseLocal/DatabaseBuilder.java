package com.example.limsebatchmanagement.DatabaseLocal;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.*;
import com.example.limsebatchmanagement.DatabaseLocal.SQL.Moe.*;
import com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard.*;

@RequiresApi(api = Build.VERSION_CODES.N)
@Database(entities = {
        EntityBatch.class,
        EntityBatchObject.class,
        EntitySample.class,
        EntityTest.class,
        EntityResult.class,
        EntityListEntry.class,
        EntityLimit.class,
        EntityCustomer.class,
        EntityDateTimeDownloadCompleted.class,
        EntityLogDownloadSched.class,
        EntitySampleMoe.class,
        EntityFibre.class,
        EntityModule.class,
        EntityListEntryMoe.class,},
        version = 1,
        exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract SqlBatch SqlBatch();
    public abstract SqlBatchObject SqlBatchObject();
    public abstract SqlSample SqlSample();
    public abstract SqlTest SqlTest();
    public abstract SqlResult SqlResult();
    public abstract SqlListEntry SqlListEntry();
    public abstract SqlLimit SqlLimit();
    public abstract SqlCustomer SqlCustomer();
    public abstract SqlLogDownloadSched SqlLogDownloadSched();
    public abstract SqlDateTimeDownloadCompleted SqlDateTimeDownloadCompleted();
    public abstract SqlSampleMoe SqlSampleMoe();
    public abstract SqlFibre SqlFibre();
    public abstract SqlModule SqlModule();
    public abstract SqlListEntryMoe SqlListEntryMoe();
    public static DatabaseBuilder INSTANCE;
    public static DatabaseBuilder getDbInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DatabaseBuilder.class, "DBLimsBatchManagement").allowMainThreadQueries().build();
        return INSTANCE;
    }
}