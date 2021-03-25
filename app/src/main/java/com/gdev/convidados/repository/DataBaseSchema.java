package com.gdev.convidados.repository;

import android.provider.BaseColumns;

public class DataBaseSchema {
    /**
     * Schema table
     */
    public static class GuestSchema implements BaseColumns {
        public static final String TABLE_NAME = "guest";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRESENCE = "presence";
    }

    /**
     * Command sql
     */
    /* Types */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";

    /* Helps */
    private static final String COMMA_SEP = ", ";
    private static final String COMMAND_CREATE = "CREATE TABLE IF NOT EXISTS ";
    private static final String COMMAND_DELETE = "DROP TABLE IF EXISTS ";

    /**
     * Create table
     */

    /* Create Table Guest*/
    public static final String SQL_CREATE_TABLE_GUEST =
            COMMAND_CREATE + GuestSchema.TABLE_NAME + " (" +
                    GuestSchema.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    GuestSchema.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    GuestSchema.COLUMN_PRESENCE + INTEGER_TYPE + " );";

    /**
     * Drop table
     */
    /* Drop Chave Table */
    public static final String SQL_DROP_TABLE_GUEST =
            COMMAND_DELETE + GuestSchema.TABLE_NAME;

}
