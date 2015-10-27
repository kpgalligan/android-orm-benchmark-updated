package com.littleinc.orm_benchmark.squidb;
import android.content.Context;

import com.yahoo.squidb.data.SquidDatabase;
import com.yahoo.squidb.data.adapter.SQLiteDatabaseWrapper;
import com.yahoo.squidb.sql.Table;

/**
 * Created by kgalligan on 10/24/15.
 */
public class MyDatabase extends SquidDatabase
{
    public MyDatabase(Context context)
    {
        super(context);
    }

    @Override
    public String getName()
    {
        return "squidbdb";
    }

    @Override
    protected int getVersion()
    {
        return 1;
    }

    @Override
    protected Table[] getTables()
    {
        return new Table[]{
            Message.TABLE, User.TABLE
        };
    }

    @Override
    protected boolean onUpgrade(SQLiteDatabaseWrapper sqLiteDatabaseWrapper, int i, int i1)
    {
        return false;
    }
}
