package com.littleinc.orm_benchmark.sugarorm;
import com.orm.SugarRecord;

/**
 * Created by kgalligan on 10/24/15.
 */
public class Message extends SugarRecord<Message>
{
    public long clientId;

    public long commandId;

    public double sortedBy;

    public int createdAt;

    public String content;

    public long senderId;

    public long channelId;
}
