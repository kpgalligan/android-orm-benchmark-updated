package com.littleinc.orm_benchmark.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    // DB CONFIG
    private static int DB_VERSION = 1;

    private static String PACKAGE = "com.littleinc.orm_benchmark.greendao";

    // USER TABLE
    private static final String USER_ENTITY = "User";

    public static final String LAST_NAME_COLUMN = "last_name";

    public static final String FIRST_NAME_COLUMN = "first_name";

    // MESSAGE TABLE
    private static final String MESSAGE_ENTITY = "Message";

    private static final String CONTENT = "content";

    private static final String READERS = "readers";

    private static final String SORTED_BY = "sorted_by";

    private static final String CLIENT_ID = "client_id";

    private static final String SENDER_ID = "sender_id";

    private static final String CHANNEL_ID = "channel_id";

    private static final String COMMAND_ID = "command_id";

    private static final String CREATED_AT = "created_at";

    public static void main(String[] args) {
        Schema schema = new Schema(DB_VERSION, PACKAGE);

        Entity user = schema.addEntity(USER_ENTITY);
        addCommonColumns(user);

        Entity message = schema.addEntity(MESSAGE_ENTITY);
        Property messagePk = message.addIdProperty().autoincrement()
                .getProperty();
        message.addStringProperty(CONTENT);
        message.addLongProperty(CLIENT_ID);
        message.addIntProperty(CREATED_AT);
        message.addDoubleProperty(SORTED_BY);
        message.addLongProperty(COMMAND_ID).index();
        message.addLongProperty(SENDER_ID).notNull();
        message.addLongProperty(CHANNEL_ID).notNull();

        // One-to-many relationship
        user.addToMany(message, messagePk, READERS);

        try {
            new DaoGenerator().generateAll(schema, "../ORM-Benchmark/src/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCommonColumns(Entity entity) {
        entity.addIdProperty().autoincrement();
        entity.addStringProperty(LAST_NAME_COLUMN);
        entity.addStringProperty(FIRST_NAME_COLUMN);
    }
}
