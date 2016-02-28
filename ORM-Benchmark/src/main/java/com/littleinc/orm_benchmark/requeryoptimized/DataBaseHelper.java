package com.littleinc.orm_benchmark.requeryoptimized;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.littleinc.orm_benchmark.requery.Models;

import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.SchemaModifier;

public class DataBaseHelper extends DatabaseSource
{
    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String DB_NAME = "requeryoptimized_db";

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

    private DataBaseHelper(Context context, boolean isInMemory)
    {
        super(context, Models.DEFAULT, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void createTables(SQLiteDatabase db)
    {
        super.onCreate(db);
    }

    public void dropTables()
    {
        new SchemaModifier(getConfiguration()).dropTables();
    }
}
