package com.example.limsebatchmanagement.DatabaseLocal.SQL.Standard;

import androidx.room.*;
import com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard.EntityCustomer;
import java.util.List;

@Dao
public interface SqlCustomer {

    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Query("SELECT * FROM EntityCustomer")
    List<EntityCustomer> selectAllClienti();

    @Query("SELECT * FROM EntityCustomer WHERE DESCRIZIONE_CLIENTE IN " +
            "(SELECT CUSTOMER_DESCRIPTION FROM EntitySample WHERE SAMPLE_NUMBER IN(SELECT SAMPLE_NUMBER FROM EntityBatchObject WHERE BATCH = :batch))")
    List<EntityCustomer> selectCustomersFromBatch(String batch);
    //==============================================
    //============== QUERY SELECT ==================
    //==============================================

    @Insert
    long insert(EntityCustomer entityCustomer);

    //==============================================
    //============== QUERY UPDATE ==================
    //==============================================

    @Update
    int update(EntityCustomer entityCustomer);

    //==============================================
    //============== QUERY DELETE ==================
    //==============================================

    @Delete
    int delete(EntityCustomer entityCustomer);

}
