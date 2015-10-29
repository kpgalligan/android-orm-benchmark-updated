package com.littleinc.orm_benchmark.squidb;
import com.yahoo.squidb.annotations.ColumnSpec;
import com.yahoo.squidb.annotations.PrimaryKey;
import com.yahoo.squidb.annotations.TableModelSpec;

/**
 * Created by kgalligan on 10/24/15.
 */
@TableModelSpec(className = "Message", tableName = "message")
public class MessageSpec
{
    @PrimaryKey
    public long id;

    public long clientId;


    public long commandId;

    public double sortedBy;

    public int createdAt;

    public String content;

    public long senderId;

    public long channelId;
}
