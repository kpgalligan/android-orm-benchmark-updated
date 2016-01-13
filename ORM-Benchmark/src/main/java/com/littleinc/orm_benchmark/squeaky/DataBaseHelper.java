package com.littleinc.orm_benchmark.squeaky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.touchlab.squeaky.db.sqlcipher.PassphraseProvider;
import co.touchlab.squeaky.db.sqlcipher.SqueakyOpenHelper;

public class DataBaseHelper extends SqueakyOpenHelper
{

    public static final String PASSPHRSE  = "asdfqwert";
    // DB CONFIG
    private static      int    DB_VERSION = 1;

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
        super(
                context,
                (isInMemory
                      ? null
                      : DB_NAME),
                null,
                DB_VERSION,
                new PassphraseProvider()
              {
                  @Override
                  public String getPassphrase()
                  {
                      return PASSPHRSE;
                  }
              });
    }

    @Override
    public void onCreate(net.sqlcipher.database.SQLiteDatabase sqLiteDatabase)
    {

    }

    @Override
    public void onUpgrade(net.sqlcipher.database.SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
