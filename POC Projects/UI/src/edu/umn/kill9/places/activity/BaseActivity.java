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

    /**
     * Define what happens when home or settings item selected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                //go home
                goHome(PlacesActivity.class);
                return true;
            case R.id.settings:
                intent = new Intent(this, PlacesPreferenceActivity.class);
                startActivityForResult(intent, PlacesConstants.PREFERENCES);
                return true;
            case R.id.add_this_location:
                intent = new Intent(this, CurrentLocationActivity.class);
                startActivityForResult(intent, PlacesConstants.ADD_CURRENT_LOCATION);
                return true;
            case R.id.add_external_location:
                intent = new Intent(this, ExternalLocationActivity.class);
                startActivityForResult(intent, PlacesConstants.ADD_EXTERNAL_LOCATION);
                return true;
            case R.id.edit_location_details:
                //add edit location details logic - just make certain items editable
                return true;
            case R.id.save_location_details:
                //add save locaton details logic - make all items uneditable and save
                return true;
            case R.id.delete_location:
                //add delete location logic
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}