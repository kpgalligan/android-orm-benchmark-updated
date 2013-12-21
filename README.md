#android-orm-benchmark

Android application for benchmarking Android RAW SQLite, ORMLite and GreenDao against each other.

##Projects
###ORM-Benchmark

ORM-Benchmark is the android application that will run several database tasks using different SQLite tools. 
These tools are:
  - [ORMLite](http://ormlite.com/)
  - [GreenDAO](http://greendao-orm.com/)
  - [Android raw SQLite](http://developer.android.com/guide/topics/data/data-storage.html#db)

###ORM-Benchmark-GreenDAO-Generator
ORM-Benchmark-GreenDAO-Generator is a Java application that generates java representations of db entities and DAOs.

##Database task
- Creation of database structure
- Writting X number of records
- Read the whole table data
- Read an indexed field
- Query limited amount of records that matches a search term
- Drop database strucuture

##Customization

- Enable/disable the usage of [in-memory](https://www.sqlite.org/inmemorydb.html) SQLite databases. Check [Application#USE_IN_MEMORY_DB](<https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/Application.java#L9>)
- Number of times to run the tests. Check [MainActivity#NUM_ITERATIONS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/MainActivity.java#L36)
- Implementing your own [BenchmarkExecutable](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java) you can add more tests.
- Search term. Check [BenchmarkExecutable#SEARCH_TERM](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L9)
- Search limit. Check [BenchmarkExecutable#SEARCH_LIMIT](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L11)
- Number of users to be saved into the db. Check [BenchmarkExecutable#NUM_USER_INSERTS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L15)
- Number of messages to be saved into the db. Check [BenchmarkExecutable#NUM_MESSAGE_INSERTS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L17)
- Number of messages with readers. Check [BenchmarkExecutable#NUM_MESSAGES_WITH_READERS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L19)
- Number of reades on messages. Check [BenchmarkExecutable#NUM_READERS](https://github.com/littleinc/android-orm-benchmark/blob/master/ORM-Benchmark/src/com/littleinc/orm_benchmark/BenchmarkExecutable.java#L13)
