package com.littleinc.orm_benchmark.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.greendao.MessageDao.Properties;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


import static com.littleinc.orm_benchmark.util.Util.getRandomString;

public class GreenDaoExecutor implements BenchmarkExecutable {

    private static final String TAG = "GreenDaoExecutor";

    private static String DB_NAME = "greendao_db";

    private DataBaseHelper mHelper;

    private DaoSession mDaoSession;

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        Log.d(TAG, "Creating DataBaseHelper");
        mHelper = new DataBaseHelper(context, (useInMemoryDb ? null : DB_NAME),
                null);
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if (mDaoSession == null) {
            mDaoSession = new DaoMaster(db).newSession();
        } else {
            DaoMaster.createAllTables(db, true);
        }
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        final List<User> users = new LinkedList<User>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User(getRandomString(10), getRandomString(10),
                    null);
            users.add(newUser);
        }

        final List<Message> messages = new LinkedList<Message>();
        for (long i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message(null);
            newMessage.setCommand_id(i);
            newMessage.setSorted_by(Double.valueOf(System.nanoTime()));
            newMessage.setContent(Util.getRandomString(100));
            newMessage.setClient_id(System.currentTimeMillis());
            newMessage.setSender_id(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setChannel_id(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setCreated_at((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        mDaoSession.runInTx(new Runnable() {

            @Override
            public void run() {
                UserDao userDao = mDaoSession.getUserDao();
                for (User user : users) {
                    userDao.insertOrReplace(user);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_USER_INSERTS + " users");

                MessageDao messageDao = mDaoSession.getMessageDao();
                for (Message message : messages) {
                    messageDao.insertOrReplace(message);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_MESSAGE_INSERTS + " messages");
            }
        });
        long time = System.nanoTime() - start;
        mDaoSession.clear();
        return time;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        int size = mDaoSession.getMessageDao().loadAll().size();
        // Logging should be outside of time measurement, but others tests do it too
        Log.d(GreenDaoExecutor.class.getSimpleName(), "Read, " + size + " rows");
        long time = System.nanoTime() - start;
        mDaoSession.clear();
        return time;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster.dropAllTables(db, true);
        // Reset version, so OpenHelper does not get confused
        db.setVersion(0);
        long time = System.nanoTime() - start;
        return time;
    }

    @Override
    public String getOrmName() {
        return "GreenDAO";
    }

}
