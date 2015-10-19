package com.littleinc.orm_benchmark.ormlite;

import android.provider.BaseColumns;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = User.TABLE_NAME)
public class User
{

    public static final String TABLE_NAME = "user";

    private static Dao<User, Long> sDao;

    @DatabaseField(columnName = BaseColumns._ID, generatedId = true)
    public Long mId;

    @DatabaseField
    public String mLastName;

    @DatabaseField
    public String mFirstName;

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        this.mId = id;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName(String lastName)
    {
        this.mLastName = lastName;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName(String firstName)
    {
        this.mFirstName = firstName;
    }

    public User()
    {
    }

    public static Dao<User, Long> getDao() throws SQLException
    {
        if(sDao == null)
        {
            sDao = DataBaseHelper.getInstance().getDao(User.class);

        }
        return sDao;
    }
}
