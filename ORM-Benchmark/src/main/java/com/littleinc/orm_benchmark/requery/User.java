package com.littleinc.orm_benchmark.requery;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;

/**
 * Created by kgalligan on 10/24/15.
 */
@Entity
public class User implements Persistable
{
    @Key
    @Generated
    public int   id;
    public String lastName;
    public String firstName;
}
