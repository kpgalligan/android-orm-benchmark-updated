package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Insert(onConflict = REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Update(onConflict = REPLACE)
    void updateUser(User user);
}
