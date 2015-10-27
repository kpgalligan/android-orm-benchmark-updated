package com.littleinc.orm_benchmark.sugarorm;
import com.orm.Database;
import com.orm.SugarApp;

/**
 * Created by kgalligan on 10/24/15.
 */
public class SugarWeirdAppExtensionBecauseBadDesign extends SugarApp
{
    public Database sneakyBreakEncapsulationBecause()
    {
        return getDatabase();
    }
}
