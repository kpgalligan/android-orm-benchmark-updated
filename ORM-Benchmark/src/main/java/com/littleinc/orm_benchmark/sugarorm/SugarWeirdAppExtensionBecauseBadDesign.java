package com.littleinc.orm_benchmark.sugarorm;
import android.util.Log;

import com.orm.Database;
import com.orm.SugarApp;

import net.sqlcipher.database.SQLiteDatabase;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by kgalligan on 10/24/15.
 */
public class SugarWeirdAppExtensionBecauseBadDesign extends SugarApp
{

    public static RealmConfiguration realmConfiguration;

    @Override
    public void onCreate()
    {
        super.onCreate();
        SQLiteDatabase.loadLibs(this);

        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        realmConfiguration = new RealmConfiguration.Builder(this)
                .encryptionKey(key)
                .build();

        // Start with a clean slate every time
        try
        {
            Realm.deleteRealm(realmConfiguration);
        }
        catch(Exception e)
        {
            Log.e("whatever", "", e);
        }
    }

    public Database sneakyBreakEncapsulationBecause()
    {
        return getDatabase();
    }
}
