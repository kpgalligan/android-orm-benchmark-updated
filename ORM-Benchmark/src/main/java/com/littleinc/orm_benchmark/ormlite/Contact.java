package com.littleinc.orm_benchmark.ormlite;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class Contact {

    public static final String LAST_NAME_COLUMN = "last_name";

    public static final String FIRST_NAME_COLUMN = "first_name";

    @DatabaseField(columnName = BaseColumns._ID, generatedId = true, dataType = DataType.LONG)
    private long mId;

    @DatabaseField(columnName = LAST_NAME_COLUMN, dataType = DataType.STRING)
    private String mLastName;

    @DatabaseField(columnName = FIRST_NAME_COLUMN, dataType = DataType.STRING)
    private String mFirstName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }
}
