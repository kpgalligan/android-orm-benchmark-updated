package com.littleinc.orm_benchmark.realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kgalligan on 6/17/15.
 */
public class Message extends RealmObject
{
    @PrimaryKey
    private int id;
    private long clientId;
    private long commandId;
    private double sortedBy;
    private int createdAt;
    private String content;
    private long senderId;
    private long channelId;

    public int getId()
    {
        return id;
    }

    public void setId(int mId)
    {
        this.id = mId;
    }

    public long getClientId()
    {
        return clientId;
    }

    public void setClientId(long clientId)
    {
        this.clientId = clientId;
    }

    public long getCommandId()
    {
        return commandId;
    }

    public void setCommandId(long commandId)
    {
        this.commandId = commandId;
    }

    public double getSortedBy()
    {
        return sortedBy;
    }

    public void setSortedBy(double sortedBy)
    {
        this.sortedBy = sortedBy;
    }

    public int getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(int createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public long getSenderId()
    {
        return senderId;
    }

    public void setSenderId(long senderId)
    {
        this.senderId = senderId;
    }

    public long getChannelId()
    {
        return channelId;
    }

    public void setChannelId(long channelId)
    {
        this.channelId = channelId;
    }
}
