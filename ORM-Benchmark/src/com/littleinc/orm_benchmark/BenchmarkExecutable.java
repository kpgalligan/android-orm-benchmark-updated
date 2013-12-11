package com.littleinc.orm_benchmark;

import java.sql.SQLException;

import android.content.Context;

public interface BenchmarkExecutable {

    public static final String SEARCH_TERM = "a";

    public static final long SEARCH_LIMIT = 100;

    public static final int NUM_USER_INSERTS = 1000;

    public static final int NUM_MESSAGE_INSERTS = 15000;

    public static final int LOOK_BY_INDEXED_FIELD = 10000;

    public static enum Task {
        CREATE_DB, WRITE_DATA, READ_DATA, READ_INDEXED, READ_SEARCH, DROP_DB;
    }

    int getProfilerId();

    String getOrmName();

    void init(Context context);

    long createDbStructure() throws SQLException;

    long writeWholeData() throws SQLException;

    long readWholeData() throws SQLException;

    long readIndexedField() throws SQLException;

    long readSearch() throws SQLException;

    long dropDb() throws SQLException;
}
