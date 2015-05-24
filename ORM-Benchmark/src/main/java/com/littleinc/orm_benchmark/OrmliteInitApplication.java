package com.littleinc.orm_benchmark;
import android.app.Application;

import com.koenv.ormlite.processor.OrmLiteProcessor;

/**
 * Created by kgalligan on 5/24/15.
 */
public class OrmliteInitApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        OrmLiteProcessor.init();
    }
}
