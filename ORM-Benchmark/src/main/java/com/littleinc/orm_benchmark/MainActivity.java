package com.littleinc.orm_benchmark;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.littleinc.orm_benchmark.BenchmarkExecutable.Task;
import com.littleinc.orm_benchmark.cupboard.CupboardExecutor;
import com.littleinc.orm_benchmark.dbflow.DBFlowExecutor;
import com.littleinc.orm_benchmark.greendao.GreenDaoExecutor;
import com.littleinc.orm_benchmark.ormlite.ORMLiteExecutor;
import com.littleinc.orm_benchmark.realm.Message;
import com.littleinc.orm_benchmark.realm.RealmExecutor;
import com.littleinc.orm_benchmark.sqlite.SQLiteExecutor;
import com.littleinc.orm_benchmark.sqliteoptimized.OptimizedSQLiteExecutor;
import com.littleinc.orm_benchmark.squeaky.SqueakyExecutor;
import com.littleinc.orm_benchmark.squidb.SquidbExecutor;
import com.littleinc.orm_benchmark.sugarorm.SugarOrmExecutor;
import com.littleinc.orm_benchmark.tasks.OrmBenchmarksTask;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.TaskQueue;
import co.touchlab.android.threading.tasks.utils.TaskQueueHelper;
import co.touchlab.android.threading.utils.UiThreadContext;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.CREATE_DB;
import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.DROP_DB;
import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.READ_DATA;
import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.READ_INDEXED;
import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.READ_SEARCH;
import static com.littleinc.orm_benchmark.BenchmarkExecutable.Task.WRITE_DATA;

public class MainActivity extends FragmentActivity {

    public static final String BENCHMARK_RESULTS = "BENCHMARK_RESULTS";
    private Button mShowResultsBtn;

    private boolean mWasInitialized = false;

    private Map<String, Map<Task, List<Long>>> mGlobalResults;

    private String results;
    private Button runBenchmark;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            results = savedInstanceState.getString(BENCHMARK_RESULTS);
        }

        mGlobalResults = new HashMap<>();
        runBenchmark = (Button) findViewById(R.id.runBenchmark);
        runBenchmark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                runBenchmark();
            }
        });
        mShowResultsBtn = (Button) findViewById(R.id.show_results_btn);
        mShowResultsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showGlobalResults();
            }
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        refreshUi();

        EventBusExt.getDefault().register(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBusExt.getDefault().unregister(this);
    }

    void refreshUi()
    {
        if(benchmarkRuning())
        {
            mShowResultsBtn.setEnabled(false);
            runBenchmark.setEnabled(false);
        }
        else
        {
            runBenchmark.setEnabled(true);
            mShowResultsBtn.setEnabled(results != null);
        }
    }

    public void runBenchmark()
    {
        if(! benchmarkRuning())
            TaskQueue.loadQueueDefault(this).execute(new OrmBenchmarksTask());

        refreshUi();
    }

    private boolean benchmarkRuning()
    {
        return TaskQueueHelper
                .hasTasksOfType(TaskQueue.loadQueueDefault(this), OrmBenchmarksTask.class);
    }

    public void showGlobalResults()
    {
        ResultDialog dialog = ResultDialog.newInstance(R.string.results_title, results);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(dialog, ResultDialog.class.getSimpleName());
        tx.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(BENCHMARK_RESULTS, results);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(OrmBenchmarksTask task)
    {
        results = task.resultString;
        refreshUi();
    }

    public static class ResultDialog extends DialogFragment
    {

        private static String TITLE_RES_ID = "title_res_id";

        private static String MESSAGE = "message";

        public static ResultDialog newInstance(int titleResId, String message)
        {
            ResultDialog dialog = new ResultDialog();

            Bundle args = new Bundle();
            args.putString(MESSAGE, message);
            args.putInt(TITLE_RES_ID, titleResId);
            dialog.setArguments(args);

            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            return builder.setTitle(getArguments().getInt(TITLE_RES_ID)).setMessage(Html.fromHtml(getArguments().getString(MESSAGE)))
                    .create();
        }
    }
}
