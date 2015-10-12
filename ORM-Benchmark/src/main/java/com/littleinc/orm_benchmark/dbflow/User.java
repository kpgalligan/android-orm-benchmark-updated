package com.littleinc.orm_benchmark.dbflow;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by kgalligan on 10/12/15.
 */
@Table(databaseName = DatabaseModule.NAME)
public class User extends BaseModel
{
    @Column
    @PrimaryKey(autoincrement = true)
    public long mId;

    @Column
    public String mLastName;

    @Column
    public String mFirstName;
}
