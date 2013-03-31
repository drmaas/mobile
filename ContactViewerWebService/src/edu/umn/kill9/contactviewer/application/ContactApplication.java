package edu.umn.kill9.contactviewer.application;

import android.app.Application;
import android.content.Context;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class ContactApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
