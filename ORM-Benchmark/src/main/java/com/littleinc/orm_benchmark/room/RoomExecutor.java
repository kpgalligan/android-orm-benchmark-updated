package com.littleinc.orm_benchmark.room;

import android.content.Context;

import com.littleinc.orm_benchmark.BenchmarkExecutable;

import java.sql.SQLException;

/**
 * Created by kskrzynecki on 6/2/17.
 */
public class RoomExecutor implements BenchmarkExecutable {

    private static final String TAG = "RoomExecutor";

    @Override
    public void init(Context context, boolean useInMemoryDb) {
    }

    @Override
    public String getOrmName() {
        return "Room";
    }

    @Override
    public long createDbStructure() throws SQLException {
        return 0;
    }

    @Override
    public long writeWholeData() throws SQLException {
        return 0l;
    }

    @Override
    public long readWholeData() throws SQLException {
        return 0l;
    }

    @Override
    public long dropDb() throws SQLException {
        return 0l;
    }
}
