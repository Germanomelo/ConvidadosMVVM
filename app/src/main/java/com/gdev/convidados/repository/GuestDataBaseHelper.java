package com.gdev.convidados.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GuestDataBaseHelper extends SQLiteOpenHelper {
private static final String DB_NAME = "convidados.bd";
private static final int DB_VERSION = 1;

    public GuestDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> statements = new ArrayList<String>();
        statements.add(DataBaseSchema.SQL_CREATE_TABLE_GUEST);

        for (String sql : statements) {
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        onCreate(db);
    }
}
