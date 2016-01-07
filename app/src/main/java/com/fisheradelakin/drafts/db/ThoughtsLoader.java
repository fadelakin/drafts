package com.fisheradelakin.drafts.db;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by temidayo on 1/7/16.
 */
public class ThoughtsLoader extends DbCursorLoader {

    private ThoughtsDataSource mDataSource;

    public ThoughtsLoader(Context context, ThoughtsDataSource thoughtsDataSource) {
        super(context);
        mDataSource = thoughtsDataSource;
    }

    @Override
    protected Cursor loadCursor() {
        return mDataSource.allThoughtsCursor();
    }
}
