package com.littleinc.orm_benchmark.dbflow;
import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by kgalligan on 10/12/15.
 */
@Database(name = DatabaseModule.NAME, version = DatabaseModule.VERSION)
public class DatabaseModule {

    public static final String NAME = "dbflow";
    public static final int VERSION = 1;
}

