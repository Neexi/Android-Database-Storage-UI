package com.example.android.simplestorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.simplestorage.BaseTableContract.DefaultTable;
/**
 * Created by new on 30/03/2016.
 */
public class BaseTableHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "default.db";
    private static final int DATABASE_VERSION = 1;

    public BaseTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_DEFAULT_TABLE = "CREATE TABLE " + DefaultTable.TABLE_NAME + " (" +
                DefaultTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DefaultTable.COLUMN_ITEM_NAME + " TEXT UNIQUE NOT NULL, " +
                DefaultTable.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL, " +
                DefaultTable.COLUMN_ITEM_EXTRA + " TEXT, " +
                DefaultTable.COLUMN_ITEM_FULL + " TEXT, " +
                DefaultTable.COLUMN_FULL_MATCHES_EXTRA + " INTEGER DEFAULT 0 " +
                " );";

        db.execSQL(SQL_CREATE_DEFAULT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DefaultTable.TABLE_NAME);
        onCreate(db);
    }
}
