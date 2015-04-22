package com.littleinc.orm_benchmark.optimizedsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

public enum SQLiteRawExecutor implements BenchmarkExecutable {

    INSTANCE;

    private DataBaseHelper mHelper;

    @Override
    public int getProfilerId() {
        return 4;
    }

    @Override
    public String getOrmName() {
        return "RAW_OPTIMIZED";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb) {
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
        SQLiteDatabase db = mHelper.getWritableDatabase();

        SQLiteStatement insertUser = db.compileStatement(
            String.format("Insert into %s (%s, %s) values (?,?)",
                User.TABLE_NAME,
                User.FIRST_NAME_COLUMN,
                User.LAST_NAME_COLUMN));

        SQLiteStatement insertMessage = db.compileStatement(
            String.format("Insert into %s (%s, %s, %s, %s, %s, %s, %s) values (?,?,?,?,?,?,?)",
                Message.TABLE_NAME,
                Message.CONTENT,
                Message.SORTED_BY,
                Message.CLIENT_ID,
                Message.SENDER_ID,
                Message.CHANNEL_ID,
                Message.COMMAND_ID,
                Message.CREATED_AT ));

        try {
            db.beginTransaction();

            for (User user : users) {
                user.prepareForInsert(insertUser);
                insertUser.execute();
            }
            Log.d(SQLiteRawExecutor.class.getSimpleName(), "Done, wrote "
                    + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                message.prepareForInsert(insertMessage);
                insertMessage.execute();
            }
            Log.d(SQLiteRawExecutor.class.getSimpleName(), "Done, wrote "
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
        Cursor c = null;
        try {
            SQLiteDatabase db = mHelper.getReadableDatabase();
            List<Message> messages = new LinkedList<Message>();
            c = db.query(Message.TABLE_NAME, Message.PROJECTION, null, null,
                    null, null, null);

            if(c != null) {

                int channelIdIndex = c.getColumnIndex(Message.CHANNEL_ID);
                int clientIdIndex = c.getColumnIndex(Message.CLIENT_ID);
                int commandIdIndex = c.getColumnIndex(Message.COMMAND_ID);
                int contentIndex = c.getColumnIndex(Message.CONTENT);
                int createdAtIndex = c.getColumnIndex(Message.CREATED_AT);
                int senderIdIndex = c.getColumnIndex(Message.SENDER_ID);
                int sortedByIndex = c.getColumnIndex(Message.SORTED_BY);

                while (c.moveToNext()) {
                    Message newMessage = new Message();
                    newMessage.setChannelId(c.getLong(channelIdIndex));
                    newMessage.setClientId(c.getLong(clientIdIndex));
                    newMessage.setCommandId(c.getLong(commandIdIndex));
                    newMessage.setContent(c.getString(contentIndex));
                    newMessage.setCreatedAt(c.getInt(createdAtIndex));
                    newMessage.setSenderId(c.getLong(senderIdIndex));
                    newMessage.setSortedBy(c.getDouble(sortedByIndex));

                    messages.add(newMessage);
                }
            }
            Log.d(SQLiteRawExecutor.class.getSimpleName(),
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

                Log.d(SQLiteRawExecutor.class.getSimpleName(),
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

            if(c != null) {

                int channelIdIndex = c.getColumnIndex(Message.CHANNEL_ID);
                int clientIdIndex = c.getColumnIndex(Message.CLIENT_ID);
                int commandIdIndex = c.getColumnIndex(Message.COMMAND_ID);
                int contentIndex = c.getColumnIndex(Message.CONTENT);
                int createdAtIndex = c.getColumnIndex(Message.CREATED_AT);
                int senderIdIndex = c.getColumnIndex(Message.SENDER_ID);
                int sortedByIndex = c.getColumnIndex(Message.SORTED_BY);

                while (c.moveToNext()) {
                    Message newMessage = new Message();
                    newMessage.setChannelId(c.getLong(channelIdIndex));
                    newMessage.setClientId(c.getLong(clientIdIndex));
                    newMessage.setCommandId(c.getLong(commandIdIndex));
                    newMessage.setContent(c.getString(contentIndex));
                    newMessage.setCreatedAt(c.getInt(createdAtIndex));
                    newMessage.setSenderId(c.getLong(senderIdIndex));
                    newMessage.setSortedBy(c.getDouble(sortedByIndex));

                    messages.add(newMessage);
                }
            }

            Log.d(SQLiteRawExecutor.class.getSimpleName(),
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
