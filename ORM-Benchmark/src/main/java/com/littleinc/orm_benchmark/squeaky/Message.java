package com.littleinc.orm_benchmark.squeaky;

import java.sql.SQLException;

import android.provider.BaseColumns;

import com.littleinc.orm_benchmark.ormlite.DataBaseHelper;

import co.touchlab.squeaky.dao.Dao;
import co.touchlab.squeaky.field.DataType;
import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

@DatabaseTable(tableName = Message.TABLE_NAME)
public class Message
{

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
    private long id;

    @DatabaseField(columnName = CLIENT_ID, dataType = DataType.LONG)
    private long clientId;

    @DatabaseField(columnName = COMMAND_ID, index = true, dataType = DataType.LONG)
    private long commandId;

    @DatabaseField(columnName = SORTED_BY, dataType = DataType.DOUBLE)
    private double sortedBy;

    @DatabaseField(columnName = CREATED_AT, dataType = DataType.INTEGER)
    private int createdAt;

    @DatabaseField(columnName = CONTENT, dataType = DataType.STRING)
    private String content;

    @DatabaseField(columnName = SENDER_ID, canBeNull = false, dataType = DataType.LONG)
    private long senderId;

    @DatabaseField(columnName = CHANNEL_ID, canBeNull = false, dataType = DataType.LONG)
    private long channelId;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getClientId()
    {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public double getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(double sortedBy) {
        this.sortedBy = sortedBy;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }
}
