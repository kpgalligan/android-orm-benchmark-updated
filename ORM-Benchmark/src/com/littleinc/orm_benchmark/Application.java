package com.littleinc.orm_benchmark;

import com.littleinc.orm_benchmark.greendao.GreenDaoExecutor;
import com.littleinc.orm_benchmark.ormlite.ORMLiteExecutor;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ORMLiteExecutor.INSTANCE.init(this);
        GreenDaoExecutor.INSTANCE.init(this);
    }
}
