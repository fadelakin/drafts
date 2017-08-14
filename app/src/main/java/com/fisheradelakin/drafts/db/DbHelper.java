package com.fisheradelakin.drafts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DbHelper extends SQLiteOpenHelper {

    // TODO: add date fields for the created at and updated at columns
    // TODO: increment db version but move over all info to new db

    private static final String DATABASE_NAME = "drafts.db";
    private static final int DATABASE_VERSION = 1;

    static final String THOUGHTS_TABLE = "thoughts";

    static final String COLUMN_DRAFTS = "drafts";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_CREATED_AT = "createdAt";
    static final String COLUMN_UPDATED_AT = "updatedAt";
    static final String COLUMN_TITLE = "title";

    private static DbHelper singleton = null;

    synchronized static DbHelper getInstance(Context context) {
        if(singleton == null) {
            singleton = new DbHelper(context.getApplicationContext());
        }
        return singleton;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + THOUGHTS_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CREATED_AT + " INTEGER, "
                + COLUMN_UPDATED_AT + "INTEGER, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DRAFTS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + THOUGHTS_TABLE);
        onCreate(db);
    }
}
