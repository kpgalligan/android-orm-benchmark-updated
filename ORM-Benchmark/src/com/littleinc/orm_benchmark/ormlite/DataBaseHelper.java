package com.littleinc.orm_benchmark.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.littleinc.orm_benchmark.R;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String DB_NAME = "ormlite_db";

    private static DataBaseHelper sInstance;

    public static void init(Context context, boolean isInMemory) {
        sInstance = new DataBaseHelper(context, isInMemory);
    }

    public static DataBaseHelper getInstance() {
        if (sInstance == null) {
            throw new InstantiationError();
        }
        return sInstance;
    }

    private DataBaseHelper(Context context, boolean isInMemory) {
        super(context, (isInMemory ? null : DB_NAME), null, DB_VERSION,
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
            int oldVersion, int newVersion) {
    }
}
