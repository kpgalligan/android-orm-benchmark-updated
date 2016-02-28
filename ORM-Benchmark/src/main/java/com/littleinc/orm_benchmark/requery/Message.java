package com.littleinc.orm_benchmark.requery;
import com.orm.SugarRecord;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;

/**
 * Created by kgalligan on 10/24/15.
 */
@Entity(cacheable = false)
public class Message implements Persistable
{
    @Key
    @Generated
    public int id;

    public long clientId;

    public long commandId;

    public double sortedBy;

    public int createdAt;

    public String content;

    public long senderId;

    public long channelId;
}
