package com.littleinc.orm_benchmark.squeaky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.touchlab.squeaky.dao.SqueakyOpenHelper;

public class DataBaseHelper extends SqueakyOpenHelper
{

    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String DB_NAME = "squeaky_db";

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

    private DataBaseHelper(Context context, boolean isInMemory) {
        super(context, (isInMemory ? null : DB_NAME), null, DB_VERSION, User.class, Message.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
            int oldVersion, int newVersion) {
    }
}
