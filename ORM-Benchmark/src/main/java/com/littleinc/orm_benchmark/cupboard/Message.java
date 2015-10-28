package com.littleinc.orm_benchmark.cupboard;
import nl.qbusict.cupboard.annotation.Index;

/**
 * Created by kgalligan on 10/27/15.
 */
public class Message
{
    public Long _id;

    public long clientId;

    @Index
    public long commandId;

    public double sortedBy;

    public int createdAt;

    public String content;

    public long senderId;

    public long channelId;
}
