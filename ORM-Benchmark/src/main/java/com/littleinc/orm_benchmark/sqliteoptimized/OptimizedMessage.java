package com.littleinc.orm_benchmark.sqliteoptimized;

import android.database.sqlite.SQLiteStatement;

import com.littleinc.orm_benchmark.sqlite.Message;

public class OptimizedMessage extends Message {

    public void prepareForInsert(final SQLiteStatement insertMessage) {
        insertMessage.bindString(1, mContent);
        insertMessage.bindDouble(2, mSortedBy);
        insertMessage.bindLong(3, mClientId);
        insertMessage.bindLong(4, mSenderId);
        insertMessage.bindLong(5, mChannelId);
        insertMessage.bindLong(6, mCommandId);
        insertMessage.bindLong(7, mCreatedAt);
    }
}
