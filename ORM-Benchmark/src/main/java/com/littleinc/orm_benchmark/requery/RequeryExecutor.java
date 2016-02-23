package com.littleinc.orm_benchmark.requery;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

/**
 * Created by kgalligan on 10/24/15.
 */
public class RequeryExecutor implements BenchmarkExecutable
{

    private static final String TAG = "RequeryExecutor";

    private DataBaseHelper mHelper;

    @Override
    public void init(Context context, boolean useInMemoryDb)
    {
        Log.d(TAG, "Creating DataBaseHelper");
        DataBaseHelper.init(context, useInMemoryDb);
        mHelper = DataBaseHelper.getInstance();
    }

    @Override
    public long createDbStructure() throws SQLException
    {
        long start = System.nanoTime();

        mHelper.createTables(mHelper.getWritableDatabase());

        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException
    {
        Random random = new Random();
        final List<UserEntity> users = new LinkedList<UserEntity>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            UserEntity newUser = new UserEntity();
            newUser.lastName = (Util.getRandomString(10));
            newUser.firstName = (Util.getRandomString(10));

            users.add(newUser);
        }

        final List<MessageEntity> messages = new LinkedList<MessageEntity>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            MessageEntity newMessage = new MessageEntity();
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

        final EntityDataStore<Persistable> userStore = new EntityDataStore<>(
                mHelper.getConfiguration());

        long start = System.nanoTime();

        userStore.runInTransaction(new Callable<Object>()
        {
            @Override
            public Object call() throws Exception
            {
                for (User user : users) {
                    userStore.insert(user);
                }
                Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

                for (Message message : messages) {
                    userStore.insert(message);
                }
                Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

                return null;
            }
        });

        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();

        final EntityDataStore<Persistable> userStore = new EntityDataStore<>(
                mHelper.getConfiguration());

        userStore.select(MessageEntity.class).get().toList().size();

        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        mHelper.dropTables();
        return System.nanoTime() - start;
    }

    @Override
    public String getOrmName() {
        return "Requery";
    }
}
