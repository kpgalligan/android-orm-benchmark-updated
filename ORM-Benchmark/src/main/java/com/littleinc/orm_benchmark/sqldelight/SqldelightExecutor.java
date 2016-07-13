package com.littleinc.orm_benchmark.sqldelight;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.squeaky.Message;
import com.wow.soreverse.MessageModel;
import com.wow.soreverse.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kgalligan on 10/12/15.
 */
public class SqldelightExecutor implements BenchmarkExecutable
{

    private static final String TAG = "SqueakyExecutor";

    private DataBaseHelper mHelper;

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        Log.d(TAG, "Creating DataBaseHelper");
        DataBaseHelper.init(context, useInMemoryDb);
        mHelper = DataBaseHelper.getInstance();
    }

    @Override
    public long createDbStructure() throws SQLException
    {
        long start = System.nanoTime();
        final SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        writableDatabase.beginTransaction();

        try
        {
            writableDatabase.execSQL(UserModel.CREATE_TABLE);
            writableDatabase.execSQL(MessageModel.CREATE_TABLE);

            writableDatabase.setTransactionSuccessful();
        }
        finally
        {
            writableDatabase.endTransaction();
        }

        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        Random random = new Random();
        /*List<User> users = new LinkedList<User>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User();
            newUser.lastName = (Util.getRandomString(10));
            newUser.firstName = (Util.getRandomString(10));

            users.add(newUser);
        }

        List<Message> messages = new LinkedList<Message>();
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
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.beginTransaction();

        try {

            for (User user : users) {
                db.insert(UserModel.TABLE_NAME, null, new UserModel.UserMarshal<UserModel.UserMarshal>()
                          .firstName(user.firstName).lastName(user.lastName).asContentValues()
                );
            }
            Log.d(TAG, "Done, wrote " + NUM_USER_INSERTS + " users");

            for (Message message : messages) {
                db.insert(MessageModel.TABLE_NAME, null, new MessageModel.MessageMarshal<MessageModel.MessageMarshal>()
                    .commandId(message.commandId)
                          .sortedBy(message.sortedBy)
                          .content(message.content)
                          .clientId(message.clientId)
                          .senderId(message.senderId)
                          .channelId(message.channelId)
                          .createdAt(message.createdAt)
                          .asContentValues()
                );
            }
            Log.d(TAG, "Done, wrote " + NUM_MESSAGE_INSERTS + " messages");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }*/
        return 0;
    }

    @Override
    public long readWholeData() throws SQLException {

        List<Message> results = new ArrayList<>();
        long start = System.nanoTime();

        /*final UserModel.Mapper mapper = new UserModel.Mapper();
        final Cursor cursor = mHelper.getReadableDatabase().rawQuery(MessageModel.SELECT_ALL, null);
        try
        {
           while(cursor.moveToNext())
           {
//               results.add(MessageModel.MA)
           }
        }
        catch(Exception e)
        {}*/

        /*Log.d(TAG,
              "Read, " + mHelper.getDao(Message.class).queryForAll().list().size()
                      + " rows");*/
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();

//        TableUtils.dropTables(mHelper.getWrappedDatabase(), true, User.class, Message.class);
        return System.nanoTime() - start;
    }

    @Override
    public String getOrmName() {
        return "Squeaky";
    }
}
