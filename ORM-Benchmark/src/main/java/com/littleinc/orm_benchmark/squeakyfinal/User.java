package com.littleinc.orm_benchmark.squeakyfinal;
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
    public final String lastName;

    @DatabaseField(dataType = DataType.STRING)
    public final String firstName;

    public User(String lastName, String firstName)
    {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
