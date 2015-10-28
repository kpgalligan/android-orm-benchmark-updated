package com.littleinc.orm_benchmark.cupboard;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by kgalligan on 10/27/15.
 */
public class DataBaseHelper  extends SQLiteOpenHelper
{

    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String DB_NAME = "cupboard_db";

    static {
        cupboard().register(Message.class);
        cupboard().register(User.class);
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

