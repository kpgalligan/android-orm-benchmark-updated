package com.littleinc.orm_benchmark.sqlite;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Message {

    public static final String TABLE_NAME = "message";

    public static final String CONTENT = "content";

    public static final String READERS = "readers";

    public static final String SORTED_BY = "sorted_by";

    public static final String CLIENT_ID = "client_id";

    public static final String SENDER_ID = "sender_id";

    public static final String CHANNEL_ID = "channel_id";

    public static final String COMMAND_ID = "command_id";

    public static final String CREATED_AT = "created_at";

    private long mId;

    private long mClientId;

    private long mCommandId;

    private double mSortedBy;

    private int mCreatedAt;

    private String mContent;

    private long mSenderId;

    private long mChannelId;

    private List<User> mReaders;

    public static final String[] PROJECTION = new String[] { CONTENT,
            SORTED_BY, CLIENT_ID, SENDER_ID, CHANNEL_ID, COMMAND_ID, CREATED_AT };

    public static void createTable(DataBaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL(new StringBuilder("CREATE TABLE '").append(TABLE_NAME)
                .append("' ('").append(BaseColumns._ID)
                .append("' INTEGER PRIMARY KEY AUTOINCREMENT, '")
                .append(CLIENT_ID).append("' INTEGER, '").append(SORTED_BY)
                .append("' REAL, '").append(CREATED_AT).append("' INTEGER, '")
                .append(CONTENT).append("' TEXT, '").append(SENDER_ID)
                .append("' INTEGER NOT NULL, '").append(CHANNEL_ID)
                .append("' INTEGER NOT NULL, '").append(COMMAND_ID)
                .append("' INTEGER);").toString());

        db.execSQL(new StringBuilder("CREATE INDEX IDX_MESSAGE_COMMAND_ID ON ")
                .append(TABLE_NAME).append(" (").append(COMMAND_ID)
                .append(");").toString());
    }

    public static void dropTable(DataBaseHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL(new StringBuilder("DROP TABLE '").append(TABLE_NAME)
                .append("';").toString());
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public long getClientId() {
        return mClientId;
    }

    public void setClientId(long clientId) {
        this.mClientId = clientId;
    }

    public long getCommandId() {
        return mCommandId;
    }

    public void setCommandId(long commandId) {
        this.mCommandId = commandId;
    }

    public double getSortedBy() {
        return mSortedBy;
    }

    public void setSortedBy(double sortedBy) {
        this.mSortedBy = sortedBy;
    }

    public int getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(int createdAt) {
        this.mCreatedAt = createdAt;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public long getSenderId() {
        return mSenderId;
    }

    public void setSenderId(long senderId) {
        this.mSenderId = senderId;
    }

    public long getChannelId() {
        return mChannelId;
    }

    public void setChannelId(long channelId) {
        this.mChannelId = channelId;
    }

    public void setReaders(List<User> readers) {
        mReaders = readers;
    }

    public List<User> getReaders() {
        return mReaders;
    }

    public boolean hasReaders() {
        return mReaders != null && !mReaders.isEmpty();
    }

    public ContentValues prepareForInsert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTENT, mContent);
        contentValues.put(SORTED_BY, mSortedBy);
        contentValues.put(CLIENT_ID, mClientId);
        contentValues.put(SENDER_ID, mSenderId);
        contentValues.put(CHANNEL_ID, mChannelId);
        contentValues.put(COMMAND_ID, mCommandId);
        contentValues.put(CREATED_AT, mCreatedAt);
        return contentValues;
    }
}
