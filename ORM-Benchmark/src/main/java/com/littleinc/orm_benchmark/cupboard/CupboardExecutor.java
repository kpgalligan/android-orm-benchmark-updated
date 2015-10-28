package com.littleinc.orm_benchmark.cupboard;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;


import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by kgalligan on 10/27/15.
 */
public class CupboardExecutor  implements BenchmarkExecutable
{

    private static final String TAG = "SQLiteExecutor";

    private DataBaseHelper mHelper;

    @Override
    public String getOrmName() {
        return "Cupboard";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        Log.d(TAG, "Creating DataBaseHelper");
        mHelper = new DataBaseHelper(context, useInMemoryDb);
    }

    @Override
    public long createDbStructure() throws SQLException
    {
        long start = System.nanoTime();
        cupboard().withDatabase(mHelper.getWritableDatabase()).createTables();
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        List<User> users = new LinkedList<>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.lastName = (Util.getRandomString(10));
            newUser.firstName = (Util.getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message();
            newMessage.commandId = (i);
            newMessage.sortedBy = (System.nanoTime());
            newMessage.content = (Util.getRandomString(100));
            newMessage.clientId = (System.currentTimeMillis());
            newMessage
                    .senderId = (Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage
                    .channelId = (Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.createdAt = ((int) (System.currentTimeMillis() / 1000L));
            messages.add(newMessage);
        }

        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        try {
            db.beginTransaction();

            for (User user : users) {
                cupboard().withDatabase(mHelper.getWritableDatabase()).put(user);
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                cupboard().withDatabase(mHelper.getWritableDatabase()).put(message);
            }
            Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();

        Cursor cursor = cupboard().withDatabase(mHelper.getWritableDatabase()).query(Message.class)
                                  .getCursor();
        QueryResultIterable<Message> iterate = null;
        try {
            int countMessages = 0;
            iterate = cupboard().withCursor(cursor).iterate(Message.class);
            for(Message message : iterate)
            {
                countMessages++;
            }
            Log.d(TAG,
                    "Read, " + countMessages + " rows");
        } finally {
            if (iterate != null) {
                iterate.close();
                cursor.close();
            }
        }
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        cupboard().withDatabase(mHelper.getWritableDatabase()).dropAllTables();
        return System.nanoTime() - start;
    }
}
