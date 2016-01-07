package com.fisheradelakin.drafts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by temidayo on 1/7/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "drafts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String THOUGHTS_TABLE = "thoughts";

    public static final String COLUMN_DRAFTS = "drafts";
    public static final String COLUMN_ID = "_id";

    private static DbHelper singleton = null;

    public synchronized static DbHelper getInstance(Context context) {
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
                + COLUMN_DRAFTS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + THOUGHTS_TABLE);
        onCreate(db);
    }
}
