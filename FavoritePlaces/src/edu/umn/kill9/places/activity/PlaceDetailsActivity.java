package edu.umn.kill9.places.activity;

import com.google.android.gms.maps.model.LatLng;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.DetailInformationFragment;
import edu.umn.kill9.places.activity.fragment.DetailMapFragment;
import edu.umn.kill9.places.activity.fragment.EventsFragment;
import edu.umn.kill9.places.activity.fragment.PlaceListFragment;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.tab.TabListener;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class PlaceDetailsActivity extends BaseActivity {

    Place place = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showHomeAsUp(true);

        //String locationName = getIntent().getStringExtra("locationName");
        place  = getIntent().getParcelableExtra(PlacesConstants.LOCATION_KEY);
        //String locationName = place.getPlaceName();
        //setTitle(locationName + " Details");

        // setup action bar for tabs
        //we will have 3 tabs - information (including call action), map (including navigation action), and eventslist
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tab_details)
                .setTabListener(new TabListener<DetailInformationFragment>(
                        this, "details", DetailInformationFragment.class) {
                	
                });
        actionBar.addTab(tab);

		//Place loc = SampleLocationList.findByLocationName(locationName);
        
        if ( place == null ) {
        	// The location doesn't exist in the database, this is a new location (maybe set some other flag here)
        	
        	Intent actIntent = getIntent();
        	
        	// Get the point
            double latitude = actIntent.getDoubleExtra("latitude", 0);
            double longitude = actIntent.getDoubleExtra("longitude", 0);
            place = new Place("My Location", new LatLng(latitude, longitude));
        }

		final Place locFinal = place;
        tab = actionBar.newTab()
                .setText(R.string.tab_map)
                .setTabListener(new TabListener<DetailMapFragment>(
                        this, "map", DetailMapFragment.class) {

							@Override
							public void onTabSelected(Tab tab, FragmentTransaction ft) {
								super.onTabSelected(tab, ft);

								if ( mFragment instanceof DetailMapFragment )
								{
									DetailMapFragment dmf = (DetailMapFragment) mFragment;
									dmf.addLocation(locFinal);
								}
							}
                	
                });
        actionBar.addTab(tab);

        /*
        tab = actionBar.newTab()
                .setText(R.string.tab_events)
                .setTabListener(new TabListener<EventsFragment>(
                        this, "eventslist", EventsFragment.class));
        actionBar.addTab(tab);
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                intent = new Intent(this, PlacesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @return
     */
    public Place getPlace() {
        return place;
    }

}