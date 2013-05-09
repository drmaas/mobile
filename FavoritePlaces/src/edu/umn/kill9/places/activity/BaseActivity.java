package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.parse.ParseAnalytics;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.preferences.PlacesPreferenceActivity;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.util.PlacesConstants;
import edu.umn.kill9.places.web.PlacesWebService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/12/13
 */
public abstract class BaseActivity extends Activity {

    private List<Category> categories;
    private List<Boolean> selected;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize categories drop-down
        categories = SampleCategoryList.getCategories();
        selected = new ArrayList();
        for (int i = 0; i < categories.size(); i++) {
            selected.add(new Boolean(false));
        }

        setTitle("");

        //parse analytics
        ParseAnalytics.trackAppOpened(getIntent());
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
     * hide keyboard
     */
    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * listener for category drop-down
     * @param v
     */
    public void onDropdownClick(View v) {
        CategoryListPopupWrapper wrapper = new CategoryListPopupWrapper(this);
        if (wrapper.isShowing()) {
            wrapper.dismiss();
        }
        else {
            wrapper.show(new CategoryAdapter(this, R.layout.categorylist_item, categories, selected));
        }
    }

    public String getDeviceId() {
        return ((TelephonyManager)getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

}