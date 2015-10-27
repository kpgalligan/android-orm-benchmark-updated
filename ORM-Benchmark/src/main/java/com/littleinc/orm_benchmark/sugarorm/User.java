package com.littleinc.orm_benchmark.sugarorm;
import com.orm.SugarRecord;

import co.touchlab.squeaky.field.DataType;
import co.touchlab.squeaky.field.DatabaseField;

/**
 * Created by kgalligan on 10/24/15.
 */
public class User extends SugarRecord<User>
{
    public String lastName;
    public String firstName;
}
