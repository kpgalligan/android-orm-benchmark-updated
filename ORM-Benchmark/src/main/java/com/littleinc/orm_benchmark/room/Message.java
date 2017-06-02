package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kskrzynecki on 6/2/17.
 */
@Entity
public class Message {
    @PrimaryKey
    public int id;
    public long clientId;
    public long commandId;
    public double sortedBy;
    public int createdAt;
    public String content;
    public long senderId;
    public long channelId;
}
