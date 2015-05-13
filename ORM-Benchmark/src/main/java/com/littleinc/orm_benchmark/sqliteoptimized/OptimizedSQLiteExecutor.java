package com.littleinc.orm_benchmark.sqliteoptimized;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.sqlite.Message;
import com.littleinc.orm_benchmark.sqlite.User;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

/**
 * This executor takes the basic idea of the standard Android sqlLite query helper, but adds a few optimizations.
 *
 * Select optimization
 * A Cursor can only access columns by their respective position in the result
 * the standard way of reading the fields value is cursor.getString(cursor.getColumnIndex("field_name"))
 * this actually loops through all column names until it finds one that matches and then return that index.
 *
 * To decrease unnecessary lookup, we do this once before we read any of the rows, and remembers the position
 * in local variables.
 *
 * Insert optimizations
 * The database.Insert function does not cache the insert query. Instead it re-creates the full statement
 * for each time its called including adding all values to the statement
 * What we do here instead is to create the SQL-statement manually and let the database driver compile it
 * for us. This creates a re-usable and very fast executable statement for us.
 *
 * For each row we just need to insert the values into the statement via the bind function and then
 * execute it.
 *
 * Everything should of course still be encapsulated by a transaction, otherwise you will get a huge overhead
 * per row.
 *
 */
public enum OptimizedSQLiteExecutor implements BenchmarkExecutable {

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
        List<OptimizedUser> users = new LinkedList<OptimizedUser>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            OptimizedUser newUser = new OptimizedUser();
            newUser.setLastName(getRandomString(10));
            newUser.setFirstName(getRandomString(10));

            users.add(newUser);
        }

        List<OptimizedMessage> messages = new LinkedList<OptimizedMessage>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            OptimizedMessage newMessage = new OptimizedMessage();
            newMessage.setCommandId(i);
            newMessage.setSortedBy(System.nanoTime());
            newMessage.setContent(Util.getRandomString(100));
            newMessage.setClientId(System.currentTimeMillis());
            newMessage.setSenderId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setChannelId(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setCreatedAt((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        SQLiteStatement insertUser = db.compileStatement(
            String.format("Insert into %s (%s, %s) values (?,?)",
                OptimizedUser.TABLE_NAME,
                OptimizedUser.FIRST_NAME_COLUMN,
                OptimizedUser.LAST_NAME_COLUMN));

        SQLiteStatement insertMessage = db.compileStatement(
            String.format("Insert into %s (%s, %s, %s, %s, %s, %s, %s) values (?,?,?,?,?,?,?)",
                OptimizedMessage.TABLE_NAME,
                OptimizedMessage.CONTENT,
                OptimizedMessage.SORTED_BY,
                OptimizedMessage.CLIENT_ID,
                OptimizedMessage.SENDER_ID,
                OptimizedMessage.CHANNEL_ID,
                OptimizedMessage.COMMAND_ID,
                OptimizedMessage.CREATED_AT ));

        try {
            db.beginTransaction();

            for (OptimizedUser user : users) {
                user.prepareForInsert(insertUser);
                insertUser.execute();
            }
            Log.d(OptimizedSQLiteExecutor.class.getSimpleName(), "Done, wrote "
                    + NUM_USER_INSERTS + " users");

            for (OptimizedMessage message : messages) {
                message.prepareForInsert(insertMessage);
                insertMessage.execute();
            }
            Log.d(OptimizedSQLiteExecutor.class.getSimpleName(), "Done, wrote "
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
            List<OptimizedMessage> messages = new LinkedList<OptimizedMessage>();
            c = db.query(OptimizedMessage.TABLE_NAME, OptimizedMessage.PROJECTION, null, null,
                    null, null, null);

            if(c != null) {

                int channelIdIndex = c.getColumnIndex(OptimizedMessage.CHANNEL_ID);
                int clientIdIndex = c.getColumnIndex(OptimizedMessage.CLIENT_ID);
                int commandIdIndex = c.getColumnIndex(OptimizedMessage.COMMAND_ID);
                int contentIndex = c.getColumnIndex(OptimizedMessage.CONTENT);
                int createdAtIndex = c.getColumnIndex(OptimizedMessage.CREATED_AT);
                int senderIdIndex = c.getColumnIndex(OptimizedMessage.SENDER_ID);
                int sortedByIndex = c.getColumnIndex(OptimizedMessage.SORTED_BY);

                while (c.moveToNext()) {
                    OptimizedMessage newMessage = new OptimizedMessage();
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
            Log.d(OptimizedSQLiteExecutor.class.getSimpleName(),
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
            String selection = OptimizedMessage.COMMAND_ID + "=?";
            String[] selectionArgs = new String[] { String
                    .valueOf(LOOK_BY_INDEXED_FIELD) };
            c = db.query(OptimizedMessage.TABLE_NAME, OptimizedMessage.PROJECTION, selection,
                    selectionArgs, null, null, null);

            if (c != null && c.moveToFirst()) {
                OptimizedMessage newMessage = new OptimizedMessage();
                newMessage.setChannelId(c.getLong(c
                        .getColumnIndex(OptimizedMessage.CHANNEL_ID)));
                newMessage.setClientId(c.getLong(c
                        .getColumnIndex(OptimizedMessage.CLIENT_ID)));
                newMessage.setCommandId(c.getLong(c
                        .getColumnIndex(OptimizedMessage.COMMAND_ID)));
                newMessage.setContent(c.getString(c
                        .getColumnIndex(OptimizedMessage.CONTENT)));
                newMessage.setCreatedAt(c.getInt(c
                        .getColumnIndex(OptimizedMessage.CREATED_AT)));
                newMessage.setSenderId(c.getLong(c
                        .getColumnIndex(OptimizedMessage.SENDER_ID)));
                newMessage.setSortedBy(c.getDouble(c
                        .getColumnIndex(OptimizedMessage.SORTED_BY)));

                Log.d(OptimizedSQLiteExecutor.class.getSimpleName(),
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
            String selection = OptimizedMessage.CONTENT + " LIKE ?";
            List<OptimizedMessage> messages = new LinkedList<OptimizedMessage>();
            String[] selectionArgs = new String[] { '%' + SEARCH_TERM + '%' };
            c = db.query(OptimizedMessage.TABLE_NAME, OptimizedMessage.PROJECTION, selection,
                    selectionArgs, null, null, null,
                    String.valueOf(SEARCH_LIMIT));

            if(c != null) {

                int channelIdIndex = c.getColumnIndex(OptimizedMessage.CHANNEL_ID);
                int clientIdIndex = c.getColumnIndex(OptimizedMessage.CLIENT_ID);
                int commandIdIndex = c.getColumnIndex(OptimizedMessage.COMMAND_ID);
                int contentIndex = c.getColumnIndex(OptimizedMessage.CONTENT);
                int createdAtIndex = c.getColumnIndex(OptimizedMessage.CREATED_AT);
                int senderIdIndex = c.getColumnIndex(OptimizedMessage.SENDER_ID);
                int sortedByIndex = c.getColumnIndex(OptimizedMessage.SORTED_BY);

                while (c.moveToNext()) {
                    OptimizedMessage newMessage = new OptimizedMessage();
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

            Log.d(OptimizedSQLiteExecutor.class.getSimpleName(),
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
