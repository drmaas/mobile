package edu.umn.kill9.places.application;

import android.app.Application;
import android.content.Context;
import com.parse.Parse;
import edu.umn.kill9.places.model.PlaceUser;

/**
 * User: drmaas
 * Date: 4/12/13
 */
public class PlacesApplication extends Application {

    private static Context mContext;
    private static PlaceUser mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //initialize our parse account
        Parse.initialize(this, "r84BaA4aJmPGFtx3wqYlfduv6InfkbX28MDvGUNu", "ZDG9HP5hagZryeaMZqwSPWlnBlB4R5whol2pXv67");
    }

    public static Context getContext() {
        return mContext;
    }

    public void setUser(PlaceUser user) {
        mUser = user;
    }

    public PlaceUser getUser() {
        return mUser;
    }
}
