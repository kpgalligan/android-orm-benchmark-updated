#android-orm-benchmark

##Summary

ORM-Benchmark is an Android application that benchmarks the following against each other:

- [ORMLite](http://ormlite.com/)
- [GreenDAO](http://greendao-orm.com/)
- [Android raw SQLite](http://developer.android.com/guide/topics/data/data-storage.html#db)

The benchmark runs the following tasks:

- CREATE_DB - Creation of database structure
- WRITE_DATA - Writing X number of records
- READ_DATA - Read the whole table data
- READ_INDEXED - Read an indexed field
- READ_SEARCH - Query limited amount of records that matches a search term
- DROP_DB - Drop database strucuture

##Sample Output

Building and running the ORM-Benchmark project produces the output below. 

- The times are in milliseconds.
- The results are shown on the device and are also logged to Logcat.  
- An "M" in front of the test shows the in-memory result so you can compare it to the on disk database.
- The results below are for running the tests on a Nexus 4 on 20th Dec 2013.

<pre>
Task CREATE_DB
M RAW - Avg: 4
RAW - Avg: 230

M ORMLite - Avg: 6
ORMLite - Avg: 233

M GreenDAO - Avg: 3
GreenDAO - Avg: 163

Task WRITE_DATA
M RAW - Avg: 2925
RAW - Avg: 3281

M ORMLite - Avg: 6898
ORMLite - Avg: 7203

M GreenDAO - Avg: 1960
GreenDAO - Avg: 2470

Task READ_DATA
M RAW - Avg: 803
RAW - Avg: 808

M ORMLite - Avg: 14275
ORMLite - Avg: 15729

M GreenDAO - Avg: 1183
GreenDAO - Avg: 1186

Task READ_INDEXED
M RAW - Avg: 1
RAW - Avg: 1

M ORMLite - Avg: 2
ORMLite - Avg: 3

M GreenDAO - Avg: 1
GreenDAO - Avg: 1

Task READ_SEARCH
M RAW - Avg: 8
RAW - Avg: 8

M ORMLite - Avg: 119
ORMLite - Avg: 156

M GreenDAO - Avg: 7
GreenDAO - Avg: 6

Task DROP_DB
M RAW - Avg: 8
RAW - Avg: 515

M ORMLite - Avg: 10
ORMLite - Avg: 558

M GreenDAO - Avg: 8
GreenDAO - Avg: 369
</pre>

##Technical Reference
###Customizing Benchmark Tests

- Enable/disable the usage of [in-memory](https://www.sqlite.org/inmemorydb.html) SQLite databases. Check [Application#USE_IN_MEMORY_DB](<https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/Application.java#L9>)
- Number of times to run the tests. Check [MainActivity#NUM_ITERATIONS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/MainActivity.java#L36)
- Implementing your own [BenchmarkExecutable](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java) you can add more tests.
- Search term. Check [BenchmarkExecutable#SEARCH_TERM](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L9)
- Search limit. Check [BenchmarkExecutable#SEARCH_LIMIT](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L11)
- Number of users to be saved into the db. Check [BenchmarkExecutable#NUM_USER_INSERTS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L15)
- Number of messages to be saved into the db. Check [BenchmarkExecutable#NUM_MESSAGE_INSERTS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L17)
- Number of messages with readers. Check [BenchmarkExecutable#NUM_MESSAGES_WITH_READERS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L19)
- Number of reades on messages. Check [BenchmarkExecutable#NUM_READERS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L13)

###Changing The GreenDao Tests 

GreenDao requires a separate project to generate the source code for database entities and DAOs.  The ORM-Benchmark-GreenDAO-Generator project is a Java application that you can run to regenerate the database definitions.  You only need to do this if you want to change the GreenDao database model! 

