package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.PlaceListFragment;
import edu.umn.kill9.places.activity.fragment.PlaceMapFragment;
import edu.umn.kill9.places.activity.preferences.PlacesPreferenceActivity;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.adapter.NavigationAdapter;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.util.PlacesConstants;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends BaseActivity {

    private static final String LISTVIEW = "List View";
    private static final String MAPVIEW = "Map View";

    private List<Category> categories;
    private List<Boolean> selected;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_main);

        //don't show 'up' button next to home icon
        showHomeAsUp(false);

        //enable navigation mode in action bar
        enableNavigationMode();

        //setup navigation menu to switch between list and map view
        final SpinnerAdapter viewSpinnerAdapter = new NavigationAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.locations_navigation_view), LISTVIEW);
        getActionBar().setListNavigationCallbacks(viewSpinnerAdapter, new ActionBar.OnNavigationListener() {
            // Get the same strings provided for the drop-down's ArrayAdapter
            String[] strings = getResources().getStringArray(R.array.locations_navigation_view);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                String item = strings[position];
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment content;
                if (item.equals(LISTVIEW)) {
                    ((NavigationAdapter)viewSpinnerAdapter).setCurrentView(LISTVIEW);
                    //show list view
                    content = new PlaceListFragment();
                }
                else if (item.equals(MAPVIEW)) {
                    ((NavigationAdapter)viewSpinnerAdapter).setCurrentView(MAPVIEW);
                    //show map view
                    content = new PlaceMapFragment();
                }
                else {
                    content = new PlaceListFragment(); //default
                }

                ft.replace(R.id.contentview, content);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                return true;
            }
        });

        //refresh categories drop-down
        categories = SampleCategoryList.getCategories();
        selected = new ArrayList();
        for (int i = 0; i < categories.size(); i++) {
            selected.add(new Boolean(false));
        }

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
            wrapper.show(new CategoryAdapter(PlacesActivity.this, R.layout.categorylist_item, categories, selected));
        }
    }

    /**
     * Show menu items
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    /**
     * Add logic here to add/remove menu items if needed
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == PlacesConstants.ADD_CURRENT_LOCATION) {
            //do something here when returning from add current location
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == PlacesConstants.ADD_EXTERNAL_LOCATION) {
            //do something here when returning from add external location
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == PlacesConstants.PREFERENCES) {
            //do something here when returning from preferences
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == PlacesConstants.DETAILS) {
            //do something here when returning from preferences
        }

    }

}
