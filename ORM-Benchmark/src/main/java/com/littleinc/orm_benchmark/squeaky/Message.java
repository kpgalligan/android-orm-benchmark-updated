package com.littleinc.orm_benchmark.squeaky;

import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

@DatabaseTable
public class Message
{
    public static final String NAME_BASE = "aabbddccssddfdsdfs_";

    @DatabaseField(generatedId = true, canBeNull = true, columnName = NAME_BASE + "id")
    public long id;

    @DatabaseField(columnName = NAME_BASE + "clientId")
    public long clientId;

    @DatabaseField(columnName = NAME_BASE + "commandId", index = true)
    public long commandId;

    @DatabaseField(columnName = NAME_BASE + "sortedBy")
    public double sortedBy;

    @DatabaseField(canBeNull = false, columnName = NAME_BASE + "createdAt")
    public int createdAt;

    @DatabaseField(columnName = NAME_BASE + "content")
    public String content;

    @DatabaseField(canBeNull = false, columnName = NAME_BASE + "senderId")
    public long senderId;

    @DatabaseField(canBeNull = false, columnName = NAME_BASE + "channelId")
    public long channelId;
}
