package com.littleinc.orm_benchmark.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * An unoptimized set of SQLite operations for reading and writing the test database objects.
 *
 * See {@link com.littleinc.orm_benchmark.sqliteoptimized.OptimizedSQLiteExecutor} for optimized
 * versions of these SQLite operations.
 */
public class SQLiteExecutor implements BenchmarkExecutable {

    private static final String TAG = "SQLiteExecutor";
    
    private DataBaseHelper mHelper;

    @Override
    public String getOrmName() {
        return "RAW";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        Log.d(TAG, "Creating DataBaseHelper");
        mHelper = new DataBaseHelper(context, useInMemoryDb);
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        User.createTable(mHelper);
        Message.createTable(mHelper);
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        List<User> users = new LinkedList<>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.fillUserWithRandomData();
            users.add(newUser);
        }

        List<Message> messages = new LinkedList<>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message();
            newMessage.fillMessageWithRandomData(i, NUM_USER_INSERTS);
            messages.add(newMessage);
        }

        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        try {
            db.beginTransaction();

            for (User user : users) {
                db.insert(User.TABLE_NAME, null, user.prepareForInsert());
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                db.insert(Message.TABLE_NAME, null, message.prepareForInsert());
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
        Cursor c = null;
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            List<Message> messages = new LinkedList<Message>();
            c = db.query(Message.TABLE_NAME, Message.PROJECTION, null, null,
                    null, null, null);

            while (c != null && c.moveToNext()) {
                Message newMessage = new Message();
                newMessage.setChannelId(c.getLong(c
                        .getColumnIndex(Message.CHANNEL_ID)));
                newMessage.setClientId(c.getLong(c
                        .getColumnIndex(Message.CLIENT_ID)));
                newMessage.setCommandId(c.getLong(c
                        .getColumnIndex(Message.COMMAND_ID)));
                newMessage.setContent(c.getString(c
                        .getColumnIndex(Message.CONTENT)));
                newMessage.setCreatedAt(c.getInt(c
                        .getColumnIndex(Message.CREATED_AT)));
                newMessage.setSenderId(c.getLong(c
                        .getColumnIndex(Message.SENDER_ID)));
                newMessage.setSortedBy(c.getDouble(c
                        .getColumnIndex(Message.SORTED_BY)));

                messages.add(newMessage);
            }
            Log.d(TAG,
                    "Read, " + messages.size() + " rows");
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();
        Cursor c = null;
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            String selection = Message.COMMAND_ID + "=?";
            String[] selectionArgs = new String[] { String
                    .valueOf(LOOK_BY_INDEXED_FIELD) };
            c = db.query(Message.TABLE_NAME, Message.PROJECTION, selection,
                    selectionArgs, null, null, null);

            if (c != null && c.moveToFirst()) {
                Message newMessage = new Message();
                newMessage.setChannelId(c.getLong(c
                        .getColumnIndex(Message.CHANNEL_ID)));
                newMessage.setClientId(c.getLong(c
                        .getColumnIndex(Message.CLIENT_ID)));
                newMessage.setCommandId(c.getLong(c
                        .getColumnIndex(Message.COMMAND_ID)));
                newMessage.setContent(c.getString(c
                        .getColumnIndex(Message.CONTENT)));
                newMessage.setCreatedAt(c.getInt(c
                        .getColumnIndex(Message.CREATED_AT)));
                newMessage.setSenderId(c.getLong(c
                        .getColumnIndex(Message.SENDER_ID)));
                newMessage.setSortedBy(c.getDouble(c
                        .getColumnIndex(Message.SORTED_BY)));

                Log.d(TAG,
                        "Read, " + c.getCount() + " rows");
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        long start = System.nanoTime();
        Cursor c = null;
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            String selection = Message.CONTENT + " LIKE ?";
            List<Message> messages = new LinkedList<Message>();
            String[] selectionArgs = new String[] { '%' + SEARCH_TERM + '%' };
            c = db.query(Message.TABLE_NAME, Message.PROJECTION, selection,
                    selectionArgs, null, null, null,
                    String.valueOf(SEARCH_LIMIT));

            while (c != null && c.moveToNext()) {
                Message newMessage = new Message();
                newMessage.setChannelId(c.getLong(c
                        .getColumnIndex(Message.CHANNEL_ID)));
                newMessage.setClientId(c.getLong(c
                        .getColumnIndex(Message.CLIENT_ID)));
                newMessage.setCommandId(c.getLong(c
                        .getColumnIndex(Message.COMMAND_ID)));
                newMessage.setContent(c.getString(c
                        .getColumnIndex(Message.CONTENT)));
                newMessage.setCreatedAt(c.getInt(c
                        .getColumnIndex(Message.CREATED_AT)));
                newMessage.setSenderId(c.getLong(c
                        .getColumnIndex(Message.SENDER_ID)));
                newMessage.setSortedBy(c.getDouble(c
                        .getColumnIndex(Message.SORTED_BY)));

                messages.add(newMessage);
            }
            Log.d(TAG,
                    "Read, " + messages.size() + " rows");
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        User.dropTable(mHelper);
        Message.dropTable(mHelper);
        return System.nanoTime() - start;
    }
}
