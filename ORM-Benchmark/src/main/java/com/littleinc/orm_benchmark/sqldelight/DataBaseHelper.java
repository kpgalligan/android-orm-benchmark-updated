package com.littleinc.orm_benchmark.sqldelight;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kgalligan on 10/27/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{

    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String DB_NAME = "sqldelight_db";

    private static DataBaseHelper sInstance;

    public static void init(Context context, boolean isInMemory)
    {
        sInstance = new DataBaseHelper(context, isInMemory);
    }

    public static DataBaseHelper getInstance()
    {
        if(sInstance == null)
        {
            throw new InstantiationError();
        }
        return sInstance;
    }

    public DataBaseHelper(Context context, boolean isInMemory) {
        super(context, (isInMemory ? null : DB_NAME), null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

