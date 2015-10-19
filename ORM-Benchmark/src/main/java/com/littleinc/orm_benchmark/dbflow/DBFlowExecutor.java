package com.littleinc.orm_benchmark.dbflow;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.stmt.SelectArg;
import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.util.Util;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import static com.littleinc.orm_benchmark.util.Util.getRandomString;

/**
 * Created by kgalligan on 10/12/15.
 */
public class DBFlowExecutor  implements BenchmarkExecutable
{

    private static final String TAG = "DBFlowExecutor";
    private Context applicationContext;

    @Override
    public void init(Context context, boolean useInMemoryDb)
    {
        Log.d(TAG, "Creating DataBaseHelper");
        applicationContext = context.getApplicationContext();
//        FlowManager.init(applicationContext);
    }

    @Override
    public long createDbStructure() throws SQLException
    {
        long start = System.nanoTime();

        FlowManager.init(applicationContext);
        FlowManager.getDatabase(DatabaseModule.NAME);
        /*ConnectionSource connectionSource = mHelper.getConnectionSource();
        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, Message.class);*/
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException
    {
        Random random = new Random();
        List<User> users = new LinkedList<User>();
        for(int i = 0; i < NUM_USER_INSERTS; i++)
        {
            User newUser = new User();
            newUser.mLastName = (getRandomString(10));
            newUser.mFirstName = (getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
        for (int i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message();
            newMessage.mCommandId = i;




            newMessage.mSortedBy = System.nanoTime();
            newMessage.mContent = Util.getRandomString(100);
            newMessage.mClientId = System.currentTimeMillis();
            newMessage
                    .mSenderId = (Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage
                    .mChannelId = (Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.mCreatedAt = ((int) (System.currentTimeMillis() / 1000L));

            newMessage.user = users.get(random.nextInt(users.size()));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        SQLiteDatabase db = FlowManager.getDatabase(DatabaseModule.NAME).getWritableDatabase();
        db.beginTransaction();

        try {
            for (User user : users) {
                user.save();
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                message.save();
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
        List<Message> messages = new Select().from(Message.class).queryList();
        Log.d(TAG,
              "Read, " + messages.size()
                      + " rows");
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();
        /*Log.d(TAG,
              "Read, "
                      + mHelper
                      .getDao(Message.class)
                      .queryForEq(Message.COMMAND_ID,
                                  LOOK_BY_INDEXED_FIELD).size() + " rows");*/
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        SelectArg arg = new SelectArg("%" + SEARCH_TERM + "%");
        long start = System.nanoTime();
        /*Log.d(TAG,
              "Read, "
                      + mHelper.getDao(Message.class).queryBuilder()
                               .limit(SEARCH_LIMIT).where()
                               .like(Message.CONTENT, arg).query().size()
                      + " rows");*/
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        Delete.table(Message.class);
        Delete.table(User.class);
//        applicationContext.deleteDatabase(DatabaseModule.NAME +".db");
        /*ConnectionSource connectionSource = mHelper.getConnectionSource();
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, Message.class, true);*/
        return System.nanoTime() - start;
    }

    @Override
    public String getOrmName() {
        return "DBFlow";
    }
}
