package com.fisheradelakin.drafts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fisheradelakin.drafts.model.Thought;

import java.util.ArrayList;
import java.util.List;


public class ThoughtsDataSource {

    private DbHelper mDbHelper;

    private String[] allColumns = {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_CREATED_AT,
            DbHelper.COLUMN_UPDATED_AT,
            DbHelper.COLUMN_TITLE,
            DbHelper.COLUMN_DRAFTS
    };

    public ThoughtsDataSource(Context context) {
        mDbHelper = DbHelper.getInstance(context);
    }

    public void createThought(Thought thought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_CREATED_AT, System.currentTimeMillis());
        contentValues.put(DbHelper.COLUMN_UPDATED_AT, System.currentTimeMillis());
        contentValues.put(DbHelper.COLUMN_TITLE, thought.getTitle());
        contentValues.put(DbHelper.COLUMN_DRAFTS, thought.getDrafts());
        thought.setId(mDbHelper.getWritableDatabase().insert(DbHelper.THOUGHTS_TABLE, null, contentValues));
    }

    Cursor allThoughtsCursor() {
        return mDbHelper.getReadableDatabase().query(DbHelper.THOUGHTS_TABLE, allColumns, null, null, null, null, null);
    }

    public List<Thought> cursorToThoughts(Cursor cursor) {
        List<Thought> thoughts = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Thought thought = cursorToThought(cursor);
            thoughts.add(thought);
            cursor.moveToNext();
        }
        return thoughts;
    }

    private Thought cursorToThought(Cursor cursor) {
        Thought thought = new Thought();
        thought.setId(cursor.getLong(0));
        thought.setCreatedAt(cursor.getLong(1));
        thought.setUpdatedAt(cursor.getLong(2));
        thought.setTitle(cursor.getString(3));
        thought.setDrafts(cursor.getString(4));
        return thought;
    }

    public void updateThought(Thought thought) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DRAFTS, thought.getDrafts());
        values.put(DbHelper.COLUMN_TITLE, thought.getTitle());
        values.put(DbHelper.COLUMN_UPDATED_AT, System.currentTimeMillis());

        String[] whereArgs = {String.valueOf(thought.getId())};

        mDbHelper.getWritableDatabase().update(DbHelper.THOUGHTS_TABLE, values, DbHelper.COLUMN_ID + "=?", whereArgs);
    }

    public void deleteThought(Thought thought) {
        String[] whereArgs = {String.valueOf(thought.getId())};

        mDbHelper.getWritableDatabase().delete(DbHelper.THOUGHTS_TABLE, DbHelper.COLUMN_ID + "=?", whereArgs);
    }
}