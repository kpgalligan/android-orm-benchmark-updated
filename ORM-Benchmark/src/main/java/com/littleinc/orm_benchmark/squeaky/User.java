package com.littleinc.orm_benchmark.squeaky;
import co.touchlab.squeaky.field.DatabaseField;
import co.touchlab.squeaky.table.DatabaseTable;

/**
 * Created by kgalligan on 10/12/15.
 */
@DatabaseTable
public class User
{
    @DatabaseField(generatedId = true)
    public long id;

    @DatabaseField
    public String lastName;

    @DatabaseField
    public String firstName;
}
