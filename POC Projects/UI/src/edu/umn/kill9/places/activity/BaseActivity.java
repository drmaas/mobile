package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.preferences.PlacesPreferenceActivity;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/12/13
 */
public abstract class BaseActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * if true, enable home icon with 'up' (back) next to it
     * otherwise just show home icon
     *
     * @param up
     */
    protected void showHomeAsUp(boolean up) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(up);
    }

    /**
     * Go to home activity or up one in hierarchy
     */
    protected void goHome(Class clazz) {
        // app icon in action bar clicked; go home
        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Enable navigation mode for top action bar
     */
    protected void enableNavigationMode() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }

}