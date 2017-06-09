package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Message.class}, version = 1)
public abstract class RoomDatabaseImpl extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
}
