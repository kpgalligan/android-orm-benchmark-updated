package com.littleinc.orm_benchmark.sugarorm;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.squeaky.*;
import com.littleinc.orm_benchmark.util.Util;
import com.orm.SugarApp;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import co.touchlab.squeaky.dao.Dao;

/**
 * Created by kgalligan on 10/24/15.
 */
public class SugarOrmExecutor implements BenchmarkExecutable
{
    private static final String TAG = "SugarOrmExecutor";
    SugarWeirdAppExtensionBecauseBadDesign sugarApp;

    @Override
    public String getOrmName()
    {
        return "SugarOrm";
    }

    @Override
    public void init(Context context, boolean useInMemoryDb)
    {
        List<Message> messages = Message.listAll(Message.class);
        sugarApp = (SugarWeirdAppExtensionBecauseBadDesign)context.getApplicationContext();
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
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.lastName = (Util.getRandomString(10));
            newUser.firstName = (Util.getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
        Message lastMessage = null;

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

            newMessage.chasingTail = lastMessage;
            lastMessage = newMessage;

            messages.add(newMessage);
        }

        long start = System.nanoTime();

        SQLiteDatabase db = sugarApp.sneakyBreakEncapsulationBecause().getDB();
        db.beginTransaction();

        try {

            /*for (User user : users) {
                user.save();
            }*/
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            int count = 0;
            long lastLogCount = 0;
            for (Message message : messages) {
                message.save();

                count++;

                long logCount = Math.round(Math.log(count));
                if(logCount > lastLogCount)
                {
                    Log.w(TAG, "count: "+ count +"/time: "+ (System.currentTimeMillis() - start));
                    lastLogCount = logCount;
                }
            }
            Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException
    {
        long start = System.nanoTime();

        long id = DatabaseUtils.longForQuery(sugarApp.sneakyBreakEncapsulationBecause().getDB(),
                                             "select max(id) from message", null);
        Message bigChain = Message.findById(Message.class, id);
        Log.d(TAG,
              "Read, " + Message.listAll(Message.class).size()
                      + " rows");
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
        User.deleteAll(Message.class);
        Message.deleteAll(User.class);
        return 0;
    }
}
