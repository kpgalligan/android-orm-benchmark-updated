package com.littleinc.orm_benchmark.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Index;
import com.raizlabs.android.dbflow.annotation.IndexGroup;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by kgalligan on 10/12/15.
 */
@Table(database = DatabaseModule.class,
    indexGroups = {
        @IndexGroup(number = 1, name = "index")
    })
public class Message extends BaseModel
{
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public long clientId;

    @Index(indexGroups = 1)
    @Column
    public long commandId;

    @Column
    public double sortedBy;

    @Column
    public int createdAt;

    @Column
    public String content;

    @Column
    public long senderId;

    @Column
    public long channelId;
}
