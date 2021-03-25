package com.gdev.convidados.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gdev.convidados.constants.GuestConstants;
import com.gdev.convidados.repository.DataBaseSchema.GuestSchema;

import com.gdev.convidados.model.GuestModel;

import java.util.ArrayList;
import java.util.List;

public class GuestRespository {

    private static GuestRespository INSTANCE;
    private final GuestDataBaseHelper mHelper;

    private GuestRespository(Context context) {
        this.mHelper = new GuestDataBaseHelper(context);
    }

    public static GuestRespository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GuestRespository(context);
        }
        return INSTANCE;
    }

    public List<GuestModel> getAll() {
        return getList(null,null);
    }

    public List<GuestModel> getPresents() {
        String selection = GuestSchema.COLUMN_PRESENCE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(GuestConstants.PRESENT)};
        return getList(selection,selectionArgs);
    }

    public List<GuestModel> getAbsents() {
        String selection = GuestSchema.COLUMN_PRESENCE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(GuestConstants.ABSENT)};
        return getList(selection,selectionArgs);
    }

    private List<GuestModel> getList(String selection, String[] selectionArgs) {
        try {
            List<GuestModel> chaves = new ArrayList<>();
            SQLiteDatabase db = this.mHelper.getReadableDatabase();
            String[] campos = getColumns();
            Cursor cursor = db.query(GuestSchema.TABLE_NAME, campos, selection, selectionArgs, null, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    chaves.add(cursorToObject(cursor));
                } while (cursor.moveToNext());
            }
            db.close();
            return chaves;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean insert(GuestModel guestModel) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();
            ContentValues values = objectToContentValues(guestModel);
            db.insert(GuestSchema.TABLE_NAME, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public GuestModel find(GuestModel guestModel) {
        try {
            GuestModel guest = null;
            Cursor cursor;
            SQLiteDatabase db = this.mHelper.getReadableDatabase();

            cursor = db.query(
                    GuestSchema.TABLE_NAME,
                    getColumns(),
                    GuestSchema.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(guestModel.getId())},
                    null,
                    null,
                    null
            );

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                guest = cursorToObject(cursor);
            }
            db.close();
            return guest;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean update(GuestModel guestModel) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();
            ContentValues values = objectToContentValues(guestModel);
            db.update(GuestSchema.TABLE_NAME, values, GuestSchema.COLUMN_ID + " = ?", new String[]{String.valueOf(guestModel.getId())});
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(GuestModel guestModel) {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();
            db.delete(GuestSchema.TABLE_NAME, GuestSchema.COLUMN_ID + " = ?", new String[]{String.valueOf(guestModel.getId())});
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clear() {
        try {
            SQLiteDatabase db = this.mHelper.getWritableDatabase();
            db.delete(GuestSchema.TABLE_NAME, null, null);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ContentValues objectToContentValues(GuestModel guestModel) {
        ContentValues values = new ContentValues();
        values.put(GuestSchema.COLUMN_NAME, guestModel.getName());
        values.put(GuestSchema.COLUMN_PRESENCE, guestModel.getConfirmation());
        return values;
    }

    private GuestModel cursorToObject(Cursor cursor) {
        GuestModel guestModel = new GuestModel();
        guestModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GuestSchema.COLUMN_ID)));
        guestModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(GuestSchema.COLUMN_NAME)));
        guestModel.setConfirmation(cursor.getInt(cursor.getColumnIndexOrThrow(GuestSchema.COLUMN_PRESENCE)));
        return guestModel;
    }

    private String[] getColumns() {
        return new String[]{
                GuestSchema.COLUMN_ID,
                GuestSchema.COLUMN_NAME,
                GuestSchema.COLUMN_PRESENCE
        };
    }


}
