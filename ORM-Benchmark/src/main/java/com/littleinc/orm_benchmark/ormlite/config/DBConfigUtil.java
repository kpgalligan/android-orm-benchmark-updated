package com.littleinc.orm_benchmark.ormlite.config;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.littleinc.orm_benchmark.ormlite.Message;
import com.littleinc.orm_benchmark.ormlite.User;

public class DBConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] sClasses = new Class[] { User.class,
            Message.class };

    /**
     * You must run this method to regenerate res/raw/ormlite_config.txt 
     * any time the database definitions are updated.
     * 
     * You need to update the Run Configuration for this class to set a
     * JRE, and remove the Android bootstrap entry. Instructions here:
     * http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_4.html
     * 
     * If you are adding a new ORM managed class you must add it to the
     * array of classes above.
     */
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", sClasses);
    }
}
