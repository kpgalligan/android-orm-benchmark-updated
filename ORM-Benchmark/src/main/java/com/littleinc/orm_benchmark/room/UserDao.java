package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Insert(onConflict = REPLACE)
    void addUser(User user);

    @Query("DELETE FROM User")
    void dropTable();
}
