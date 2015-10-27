package com.littleinc.orm_benchmark.squidb;
import com.yahoo.squidb.annotations.PrimaryKey;
import com.yahoo.squidb.annotations.TableModelSpec;

/**
 * Created by kgalligan on 10/24/15.
 */
@TableModelSpec(className = "User", tableName = "user")
public class UserSpec
{
    @PrimaryKey
    public long id;
    public String lastName;
    public String firstName;
}
