package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.littleinc.orm_benchmark.util.Util;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public long clientId;
    public long commandId;
    public double sortedBy;
    public int createdAt;
    public String content;
    public long senderId;
    public long channelId;

    public void fillMessageWithRandomData(int messageNumber, int totalNumber) {
        commandId = messageNumber;
        sortedBy = System.nanoTime();
        content = Util.getRandomString(100);
        clientId = System.currentTimeMillis();
        senderId = Math.round(Math.random() * totalNumber);
        channelId = Math.round(Math.random() * totalNumber);
        createdAt = (int) (System.currentTimeMillis() / 1000L);
    }
}
