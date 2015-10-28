package com.littleinc.orm_benchmark.tasks;
import android.content.Context;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.cupboard.CupboardExecutor;
import com.littleinc.orm_benchmark.dbflow.DBFlowExecutor;
import com.littleinc.orm_benchmark.greendao.GreenDaoExecutor;
import com.littleinc.orm_benchmark.ormlite.ORMLiteExecutor;
import com.littleinc.orm_benchmark.realm.RealmExecutor;
import com.littleinc.orm_benchmark.sqlite.SQLiteExecutor;
import com.littleinc.orm_benchmark.sqliteoptimized.OptimizedSQLiteExecutor;
import com.littleinc.orm_benchmark.squeaky.SqueakyExecutor;
import com.littleinc.orm_benchmark.squidb.SquidbExecutor;
import com.littleinc.orm_benchmark.sugarorm.SugarOrmExecutor;

import java.util.Map;
import java.util.TreeMap;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.Task;

/**
 * Created by kgalligan on 10/28/15.
 */
public class OrmBenchmarksTask extends Task
{
    public static final String TAG = "OrmBenchmarksTask";
    private static final boolean USE_IN_MEMORY_DB = false;
    private static final int NUM_ITERATIONS = 5;

    private BenchmarkExecutable[] mOrms = new BenchmarkExecutable[]
            {
                    new SquidbExecutor(),
                    new SugarOrmExecutor(),
                    new SQLiteExecutor(),
                    new DBFlowExecutor(),
                    new SqueakyExecutor(),
                    new CupboardExecutor(),
                    new com.littleinc.orm_benchmark.squeakyfinal.SqueakyExecutor(),
                    new RealmExecutor(),
                    new OptimizedSQLiteExecutor(),
                    new ORMLiteExecutor(),
                    new GreenDaoExecutor()
            };

    public String resultString;

    Map<String, Map<String, Long>> benchmarkResults = new TreeMap<>();

    enum BenchmarkTask {
        CREATE_DB, WRITE_DATA, READ_DATA, DROP_DB;
    }

    @Override
    protected void run(Context context) throws Throwable
    {
        for(BenchmarkExecutable orm : mOrms)
        {
            orm.init(context, USE_IN_MEMORY_DB);
            Log.w(TAG, orm.getOrmName() + " init");
        }

        for(int i=0; i<NUM_ITERATIONS; i++)
        {
            for(BenchmarkExecutable item : mOrms)
            {
                for(BenchmarkTask task : BenchmarkTask.values())
                {
                    long result = 0;

                    Log.w(TAG, item.getOrmName() + "-" + task.name() +" start");
                    switch(task)
                    {
                        case CREATE_DB:
                            result = item.createDbStructure();
                            break;
                        case DROP_DB:
                            result = item.dropDb();
                            break;
                        case READ_DATA:
                            result = item.readWholeData();
                            break;
                        case WRITE_DATA:
                            result = item.writeWholeData();
                            break;
                    }
                    Log.w(TAG, item.getOrmName() + "-" + task.name() +" end");
                    addProfilerResult(item.getOrmName(), task, result);
                }
            }
        }

        buildResultString();
    }

    private void buildResultString()
    {
        StringBuilder sb = new StringBuilder();

        BenchmarkTask[] bTasks = new BenchmarkTask[]{BenchmarkTask.WRITE_DATA, BenchmarkTask.READ_DATA};
        for(BenchmarkTask bTask : bTasks)
        {
            sb.append("<b>").append(bTask.name()).append("</b>").append("<br/>");
            Map<String, Long> stringLongMap = benchmarkResults.get(bTask.name());
            for(String ormName : stringLongMap.keySet())
            {
                long result = stringLongMap.get(ormName);
                double printResult = ((double) result / (double) NUM_ITERATIONS) / ((double) 1000000);
                sb.append(ormName).append(" - ").append(Math.round(printResult)).append("ms")
                  .append("<br/>");
            }
        }

        resultString = sb.toString();
    }

    private void addProfilerResult(String ormName, BenchmarkTask task, long result)
    {
        Map<String, Long> taskMap = benchmarkResults.get(task.name());
        if(taskMap == null)
        {
            taskMap = new TreeMap<>();
            benchmarkResults.put(task.name(), taskMap);
        }
        Long storedResult = taskMap.get(ormName);
        if(storedResult == null)
        {
            storedResult = 0l;
        }
        long value = result + storedResult;
        taskMap.put(ormName, value);
        Log.w(TAG, "adding "+ task.name() +"-"+ ormName+"-"+value);
    }

    @Override
    protected boolean handleError(Context context, Throwable e)
    {
        return false;
    }

    @Override
    protected void onComplete(Context context)
    {
        EventBusExt.getDefault().post(this);
    }
}
