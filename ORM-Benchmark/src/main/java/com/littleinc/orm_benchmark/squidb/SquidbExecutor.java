package com.littleinc.orm_benchmark.squidb;
import android.content.Context;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;
import com.yahoo.squidb.data.SquidCursor;
import com.yahoo.squidb.sql.Query;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by kgalligan on 10/24/15.
 */
public class SquidbExecutor implements BenchmarkExecutable
{
    private static final String TAG = "SquidbExecutor";

    private MyDatabase myDatabase;

    @Override
    public String getOrmName()
    {
        return "Squidb";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb)
    {
        myDatabase = new MyDatabase(context);
    }

    @Override
    public long createDbStructure() throws SQLException
    {
        return 0;
    }

    @Override
    public long writeWholeData() throws SQLException
    {
        Random random = new Random();
        List<User> users = new LinkedList<User>();
        for(int i = 0; i < NUM_USER_INSERTS; i++)
        {
            User newUser = new User();
            newUser.setFirstName(Util.getRandomString(10));
            newUser.setLastName(Util.getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message();
            newMessage.setCommandId((long) i);
            newMessage.setSortedBy((double) System.nanoTime());
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
        myDatabase.beginTransaction();

        try {
            for (User user : users) {
                myDatabase.persist(user);
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                myDatabase.persist(message);
            }
            Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

            myDatabase.setTransactionSuccessful();
        } finally {
            myDatabase.endTransaction();
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException
    {
        long start = System.nanoTime();
        SquidCursor<Message> query = myDatabase.query(Message.class, Query.select());
        Message message = new Message();
        while(query.moveToNext())
        {
            message.readPropertiesFromCursor(query);
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException
    {
        return 0;
    }

    @Override
    public long readSearch() throws SQLException
    {
        return 0;
    }

    @Override
    public long dropDb() throws SQLException
    {
        myDatabase.deleteAll(Message.class);
        myDatabase.deleteAll(User.class);
        return 0;
    }
}
