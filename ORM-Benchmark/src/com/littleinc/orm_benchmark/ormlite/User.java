package com.littleinc.orm_benchmark.ormlite;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = User.TABLE_NAME)
public class User extends Contact {

    public static final String TABLE_NAME = "user";

    private static Dao<User, Long> sDao;

    @DatabaseField(columnName = "message_id", foreign = true, foreignAutoRefresh = true)
    private Message mMessage;

    public User() {
    }

    public static Dao<User, Long> getDao() {
        if (sDao == null) {
            try {
                sDao = DataBaseHelper.getInstance().getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sDao;
    }
}
