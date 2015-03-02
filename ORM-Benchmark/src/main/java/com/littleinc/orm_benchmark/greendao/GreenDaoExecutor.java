package com.littleinc.orm_benchmark.greendao;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.greendao.MessageDao.Properties;
import com.littleinc.orm_benchmark.util.Util;

public enum GreenDaoExecutor implements BenchmarkExecutable {

    INSTANCE;

    private static String DB_NAME = "greendao_db";

    private DataBaseHelper mHelper;

    private DaoMaster mDaoMaster;

    @Override
    public int getProfilerId() {
        return 3;
    }

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        mHelper = new DataBaseHelper(context, (useInMemoryDb ? null : DB_NAME),
                null);
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        if (mDaoMaster == null) {
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        } else {
            DaoMaster.createAllTables(mHelper.getWritableDatabase(), true);
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
            newMessage
                    .setSender_id(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setChannel_id(Math.round(Math.random()
                    * NUM_USER_INSERTS));
            newMessage
                    .setCreated_at((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        final DaoSession daoSession = mDaoMaster.newSession();
        daoSession.runInTx(new Runnable() {

            @Override
            public void run() {
                UserDao userDao = daoSession.getUserDao();
                for (User user : users) {
                    userDao.insertOrReplace(user);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_USER_INSERTS + " users");

                MessageDao messageDao = daoSession.getMessageDao();
                for (Message message : messages) {
                    messageDao.insertOrReplace(message);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_MESSAGE_INSERTS + " messages");
                daoSession.clear();
            }
        });
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        DaoSession daoSession = mDaoMaster.newSession();
        MessageDao messageDao = daoSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(), "Read, "
                + messageDao.queryBuilder().list().size() + " rows");
        daoSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();
        DaoSession daoSession = mDaoMaster.newSession();
        MessageDao messageDao = daoSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(),
                "Read, "
                        + messageDao
                                .queryBuilder()
                                .where(Properties.Command_id
                                        .eq(LOOK_BY_INDEXED_FIELD)).list()
                                .size() + " rows");
        daoSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        long start = System.nanoTime();
        DaoSession daoSession = mDaoMaster.newSession();
        MessageDao messageDao = daoSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(),
                "Read, "
                        + messageDao
                                .queryBuilder()
                                .limit((int) SEARCH_LIMIT)
                                .where(Properties.Content.like("%"
                                        + SEARCH_TERM + "%")).list().size()
                        + " rows");
        daoSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        DaoMaster.dropAllTables(mHelper.getWritableDatabase(), true);
        return System.nanoTime() - start;
    }

    @Override
    public String getOrmName() {
        return "GreenDAO";
    }

}
