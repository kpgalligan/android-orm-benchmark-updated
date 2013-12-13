package com.littleinc.orm_benchmark.ormlite;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;

public enum ORMLiteExecutor implements BenchmarkExecutable {

    INSTANCE;

    private DataBaseHelper mHelper;

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        DataBaseHelper.init(context, useInMemoryDb);
        mHelper = DataBaseHelper.getInstance();
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        ConnectionSource connectionSource = mHelper.getConnectionSource();
        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, Message.class);
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        List<User> users = new LinkedList<User>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.setLastName(getRandomString(10));
            newUser.setFirstName(getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message();
            newMessage.setCommandId(i);
            newMessage.setSortedBy(System.nanoTime());
            newMessage.setContent(Util.getRandomString(100));
            newMessage.setClientId(System.currentTimeMillis());
            newMessage
                    .setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage
                    .setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            for (User user : users) {
                User.getDao().createOrUpdate(user);
            }
            Log.d(ORMLiteExecutor.class.getSimpleName(), "Done, wrote "
                    + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                Message.getDao().createOrUpdate(message);
            }
            Log.d(ORMLiteExecutor.class.getSimpleName(), "Done, wrote "
                    + NUM_MESSAGE_INSERTS + " messages");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        Log.d(ORMLiteExecutor.class.getSimpleName(),
                "Read, " + mHelper.getDao(Message.class).queryForAll().size()
                        + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();
        Log.d(ORMLiteExecutor.class.getSimpleName(),
                "Read, "
                        + mHelper
                                .getDao(Message.class)
                                .queryForEq(Message.COMMAND_ID,
                                        LOOK_BY_INDEXED_FIELD).size() + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        SelectArg arg = new SelectArg("%" + SEARCH_TERM + "%");
        long start = System.nanoTime();
        Log.d(ORMLiteExecutor.class.getSimpleName(),
                "Read, "
                        + mHelper.getDao(Message.class).queryBuilder()
                                .limit(SEARCH_LIMIT).where()
                                .like(Message.CONTENT, arg).query().size()
                        + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        ConnectionSource connectionSource = mHelper.getConnectionSource();
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, Message.class, true);
        return System.nanoTime() - start;
    }

    @Override
    public int getProfilerId() {
        return 2;
    }

    @Override
    public String getOrmName() {
        return "ORMLite";
    }
}
