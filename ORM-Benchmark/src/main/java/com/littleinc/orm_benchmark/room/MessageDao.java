package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM Message")
    List<Message> getMessages();

    @Insert(onConflict = REPLACE)
    void addMessage(Message message);

    @Query("DELETE FROM Message")
    void dropTable();
}
