package com.littleinc.orm_benchmark.sqliteoptimized;

import android.database.sqlite.SQLiteStatement;

import com.littleinc.orm_benchmark.sqlite.User;

public class OptimizedUser extends User {

    public void prepareForInsert(final SQLiteStatement insertUser) {
        insertUser.bindString(1, mLastName);
        insertUser.bindString(2, mFirstName);
    }
}
