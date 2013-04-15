package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Category;

import java.util.List;

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

    /**
     * Define what happens when home or settings item selected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                //go home
                goHome(PlacesActivity.class);
                return true;
            case R.id.settings:
                //go to settings
                return true;
            case R.id.add_this_location:
                //go to add current location screen
                return true;
            case R.id.add_external_location:
                //got to search for external location screen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}