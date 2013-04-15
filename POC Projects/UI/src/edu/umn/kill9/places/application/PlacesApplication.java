package edu.umn.kill9.places.application;

import android.app.Application;
import android.content.Context;

/**
 * User: drmaas
 * Date: 4/12/13
 */
public class PlacesApplication extends Application {

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
