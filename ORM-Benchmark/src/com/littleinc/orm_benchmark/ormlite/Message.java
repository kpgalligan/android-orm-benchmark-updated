package com.littleinc.orm_benchmark.ormlite;

import java.sql.SQLException;

import android.provider.BaseColumns;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Message.TABLE_NAME)
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

    @DatabaseField(columnName = BaseColumns._ID, generatedId = true, dataType = DataType.LONG)
    private long mId;

    @DatabaseField(columnName = CLIENT_ID, dataType = DataType.LONG)
    private long mClientId;

    @DatabaseField(columnName = COMMAND_ID, index = true, dataType = DataType.LONG)
    private long mCommandId;

    @DatabaseField(columnName = SORTED_BY, dataType = DataType.DOUBLE)
    private double mSortedBy;

    @DatabaseField(columnName = CREATED_AT, dataType = DataType.INTEGER)
    private int mCreatedAt;

    @DatabaseField(columnName = CONTENT, dataType = DataType.STRING)
    private String mContent;

    @DatabaseField(columnName = SENDER_ID, canBeNull = false, dataType = DataType.LONG)
    private long mSenderId;

    @DatabaseField(columnName = CHANNEL_ID, canBeNull = false, dataType = DataType.LONG)
    private long mChannelId;

    @ForeignCollectionField(eager = true, columnName = READERS)
    private ForeignCollection<User> mReaders;

    private static Dao<Message, Long> sDao;

    public static Dao<Message, Long> getDao() {
        if (sDao == null) {
            try {
                sDao = DataBaseHelper.getInstance().getDao(Message.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sDao;
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

    public ForeignCollection<User> getReaders() {
        return mReaders;
    }
}
