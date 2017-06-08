package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RoomExecutor implements BenchmarkExecutable {

    private static final String TAG = "RoomExecutor";

    private RoomDatabase.Builder<RoomDatabaseImpl> mBuilder;
    private RoomDatabaseImpl mHelper;

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        if (useInMemoryDb) {
            mBuilder = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RoomDatabaseImpl.class);
        } else {
            mBuilder = Room.databaseBuilder(context.getApplicationContext(), RoomDatabaseImpl.class, "event_db");
        }
    }

    @Override
    public String getOrmName() {
        return "Room";
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        mHelper = mBuilder.build();
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

        try {
            mHelper.beginTransaction();

            for (User user : users) {
                mHelper.userDao().addUser(user);
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                mHelper.messageDao().addMessage(message);
            }
            Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");
            mHelper.setTransactionSuccessful();
        } finally {
            mHelper.endTransaction();
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        int size = mHelper.messageDao().getMessages().size();
        Log.d(TAG, "Read, " + size + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        mHelper.messageDao().dropTable();
        mHelper.userDao().dropTable();
        return System.nanoTime() - start;
    }
}
