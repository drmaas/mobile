package edu.umn.kill9.places.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SpinnerAdapter;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.BaseMapFragment;
import edu.umn.kill9.places.activity.fragment.PlaceListFragment;
import edu.umn.kill9.places.activity.fragment.PlaceMapFragment;
import edu.umn.kill9.places.activity.preferences.PlacesPreferenceActivity;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.adapter.NavigationAdapter;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

public class PlacesActivity extends BaseActivity {
    
    private SpinnerAdapter viewSpinnerAdapter;

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
        String defaultView = getResources().getString(R.string.default_view);
        viewSpinnerAdapter = new NavigationAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.locations_navigation_view), defaultView);
        getActionBar().setListNavigationCallbacks(viewSpinnerAdapter, new ActionBar.OnNavigationListener() {
            // Get the same strings provided for the drop-down's ArrayAdapter
            String[] strings = getResources().getStringArray(R.array.locations_navigation_view);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                String item = strings[position];
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment content;

                String list = getResources().getString(R.string.list_view);
                String map = getResources().getString(R.string.map_view);
                
                if (item.equals( list ) ) {
                    //show list view
                    content = new PlaceListFragment();
                }
                else if (item.equals( map )) {
//                    //show map view
//                    content = new PlaceMapFragment();

                    //show map view
                    content = new BaseMapFragment();
                    
                    // Set arguments to the map
                    Bundle b = new Bundle();
                    b.putBoolean("multiplePoints", true);
                    b.putStringArrayList("locations", new ArrayList<String>(SampleLocationList.getLocationStrings()));
                    content.setArguments(b);
                }
                else {
                    content = new PlaceListFragment(); //default
                }

                ((NavigationAdapter)viewSpinnerAdapter).setCurrentView( item );

                ft.replace(R.id.contentview, content);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                return true;
            }
        });

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
            //do something here when returning from details
        }

    }

}
