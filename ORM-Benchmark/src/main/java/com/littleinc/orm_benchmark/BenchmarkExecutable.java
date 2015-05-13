package com.littleinc.orm_benchmark;

import java.sql.SQLException;

import android.content.Context;

public interface BenchmarkExecutable {

    String SEARCH_TERM = "a";

    long SEARCH_LIMIT = 100;

    int NUM_READERS = 10;

    int NUM_USER_INSERTS = 1000;

    int NUM_MESSAGE_INSERTS = 10000;

    int NUM_MESSAGES_WITH_READERS = 50;

    int LOOK_BY_INDEXED_FIELD = 5000;

    enum Task {
        CREATE_DB, WRITE_DATA, READ_DATA, READ_INDEXED, READ_SEARCH, DROP_DB;
    }

    String getOrmName();

    void init(Context context, boolean useInMemoryDb);

    long createDbStructure() throws SQLException;

    long writeWholeData() throws SQLException;

    long readWholeData() throws SQLException;

    long readIndexedField() throws SQLException;

    long readSearch() throws SQLException;

    long dropDb() throws SQLException;
}
