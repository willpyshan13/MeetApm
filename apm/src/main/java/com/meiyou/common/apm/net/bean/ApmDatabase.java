package com.meiyou.common.apm.net.bean;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MetricsBean.class}, version = 1,exportSchema = false)
public abstract class ApmDatabase extends RoomDatabase {
    public abstract HttpDao httpDao();
}