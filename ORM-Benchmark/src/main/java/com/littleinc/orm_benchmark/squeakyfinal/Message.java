package com.littleinc.orm_benchmark.squeakyfinal;

import android.provider.BaseColumns;

import co.touchlab.squeaky.field.DataType;
import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

@DatabaseTable
public class Message
{
    @DatabaseField(generatedId = true)
    public long id;

    @DatabaseField(canBeNull = false)
    public final long clientId;

    @DatabaseField(index = true, canBeNull = false)
    public final long commandId;

    @DatabaseField(canBeNull = false)
    public final double sortedBy;

    @DatabaseField(canBeNull = false)
    public final int createdAt;

    @DatabaseField
    public final String content;

    @DatabaseField(canBeNull = false)
    public final long senderId;

    @DatabaseField(canBeNull = false)
    public final long channelId;

    public Message(long clientId, long commandId, double sortedBy, int createdAt, String content, long senderId, long channelId)
    {
        this.clientId = clientId;
        this.commandId = commandId;
        this.sortedBy = sortedBy;
        this.createdAt = createdAt;
        this.content = content;
        this.senderId = senderId;
        this.channelId = channelId;
    }
}
