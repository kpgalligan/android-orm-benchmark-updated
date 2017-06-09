package com.littleinc.orm_benchmark.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

@Entity
public class User {
    @PrimaryKey
    public int id;
    public String mLastName;
    public String mFirstName;

    public void fillUserWithRandomData() {
        mLastName = getRandomString(10);
        mFirstName = getRandomString(10);
    }
}
