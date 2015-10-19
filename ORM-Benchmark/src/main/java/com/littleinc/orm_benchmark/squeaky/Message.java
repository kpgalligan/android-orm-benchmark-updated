package com.littleinc.orm_benchmark.squeaky;

import android.provider.BaseColumns;

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
    public long id;

    @DatabaseField(columnName = CLIENT_ID, dataType = DataType.LONG)
    public long clientId;

    @DatabaseField(columnName = COMMAND_ID, index = true, dataType = DataType.LONG)
    public long commandId;

    @DatabaseField(columnName = SORTED_BY, dataType = DataType.DOUBLE)
    public double sortedBy;

    @DatabaseField(columnName = CREATED_AT, dataType = DataType.INTEGER)
    public int createdAt;

    @DatabaseField(columnName = CONTENT, dataType = DataType.STRING)
    public String content;

    @DatabaseField(columnName = SENDER_ID, canBeNull = false, dataType = DataType.LONG)
    public long senderId;

    @DatabaseField(columnName = CHANNEL_ID, canBeNull = false, dataType = DataType.LONG)
    public long channelId;
}
