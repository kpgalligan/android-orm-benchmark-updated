package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kskrzynecki on 6/2/17.
 */
@Entity
public class User {
    @PrimaryKey
    public int id;
    public String mLastName;
    public String mFirstName;
}
