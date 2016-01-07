package com.fisheradelakin.drafts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fisheradelakin.drafts.model.Thought;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temidayo on 1/7/16.
 */
public class ThoughtsDataSource {

    private DbHelper mDbHelper;

    private String[] allColumns = {
            DbHelper.COLUMN_ID,
            DbHelper.COLUMN_DRAFTS
    };

    public ThoughtsDataSource(Context context) {
        mDbHelper = DbHelper.getInstance(context);
    }

    public void createThought(Thought thought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_DRAFTS, thought.getDrafts());
        thought.setId(mDbHelper.getWritableDatabase().insert(DbHelper.THOUGHTS_TABLE, null, contentValues));
    }

    public List<Thought> getAllThoughts() {
        Cursor cursor = allThoughtsCursor();
        return cursorToThoughts(cursor);
    }

    public Cursor allThoughtsCursor() {
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
        thought.setDrafts(cursor.getString(1));
        return thought;
    }

    public void updateThought(Thought thought) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DRAFTS, thought.getDrafts());

        String[] whereArgs = {String.valueOf(thought.getId())};

        mDbHelper.getWritableDatabase().update(DbHelper.THOUGHTS_TABLE, values, DbHelper.COLUMN_ID + "=?", whereArgs);
    }

    public void deleteThought(Thought thought) {
        String[] whereArgs = {String.valueOf(thought.getId())};

        mDbHelper.getWritableDatabase().delete(DbHelper.THOUGHTS_TABLE, DbHelper.COLUMN_ID + "=?", whereArgs);
    }
}