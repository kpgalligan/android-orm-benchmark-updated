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
    private long id;

    @DatabaseField(dataType = DataType.STRING)
    private String lastName;

    @DatabaseField(dataType = DataType.STRING)
    private String firstName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
