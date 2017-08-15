package com.fisheradelakin.drafts;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by fisher on 8/14/17.
 */

public class DraftsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
