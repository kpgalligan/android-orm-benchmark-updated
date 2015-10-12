package com.littleinc.orm_benchmark.squeaky;
import co.touchlab.squeaky.field.DataType;
import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

/**
 * Created by kgalligan on 10/12/15.
 */
@DatabaseTable
public class User
{
    @DatabaseField(generatedId = true, dataType = DataType.LONG)
    public long id;

    @DatabaseField(dataType = DataType.STRING)
    public String lastName;

    @DatabaseField(dataType = DataType.STRING)
    public String firstName;
}
