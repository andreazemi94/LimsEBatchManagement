package com.example.limsebatchmanagement.DatabaseLocal.Entity.Standard;

import com.example.limsebatchmanagement.DatabaseLocal.DatabaseBuilder;
import java.util.List;
import java.util.Map;

public abstract class EntityGeneric {
    public abstract EntityGeneric create(Map<String,String> map);
    public abstract void insert(DatabaseBuilder db);
    public abstract void update(EntityGeneric entityGeneric, DatabaseBuilder db);
    public abstract void delete(DatabaseBuilder db);
    public abstract String getNameEntity();
    public abstract Map<Object,Map<Object,Object>> createDocumentFb();
}
