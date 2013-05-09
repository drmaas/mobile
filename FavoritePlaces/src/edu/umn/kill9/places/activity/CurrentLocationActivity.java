package edu.umn.kill9.places.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;
import edu.umn.kill9.places.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import edu.umn.kill9.places.activity.fragment.AddCurrentLocFragment;
import edu.umn.kill9.places.activity.fragment.PlaceMapFragment;
import edu.umn.kill9.places.activity.geo.PlacesLocationManager;
import edu.umn.kill9.places.adapter.PlaceAdapter;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.web.PlacesWebService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class CurrentLocationActivity extends BaseActivity implements PlacesWebService.PlacesAPIJSONListener {

    private List<Place> places;
    private String currentLocation;

    AddCurrentLocFragment loclist;
    PlaceMapFragment locmap;

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.addcurrentlocation);
       
        //show 'up' button next to home icon
        showHomeAsUp(true);

        //kick off current location listener
        //and execute service call to get locations
        getCurrentLocation();

        //filter list
        EditText filterText = (EditText)findViewById(R.id.currlocsearchbox);
        filterText.addTextChangedListener(filterTextWatcher);

        //fragment references
        loclist = (AddCurrentLocFragment)getFragmentManager().findFragmentById(R.id.currloclistfragment);
        locmap = (PlaceMapFragment)getFragmentManager().findFragmentById(R.id.currlocmapfragment);
    }

    @Override
    public void onWebServiceCallComplete(List<Place> placesList){
        places = new ArrayList<Place>();
        places.addAll(placesList);

        //refresh list
        loclist.setListAdapter(new PlaceAdapter(this, R.layout.place_item, places));

        //add points to map
        locmap.clearLocations();
        locmap.addLocation(places);
        locmap.refreshMap();
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

    private void getCurrentLocation() {
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle bundle = ai.metaData;
        String myApiKey = bundle.getString("com.google.android.maps.v2.API_KEY");
        PlacesWebService webservice = new PlacesWebService(CurrentLocationActivity.this, myApiKey);
        PlacesLocationManager manager = new PlacesLocationManager(this, webservice,"");
        manager.getCurrentLocation();
    }

    /**
     * filter the list adapter
     */
    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //filter list
            final PlaceAdapter pa = (PlaceAdapter)loclist.getListAdapter();
            pa.getFilter().filter(s, new Filter.FilterListener() {
                @Override
                public void onFilterComplete(int count) {
                    //filter map
                    int c = pa.getCount();
                    List<Place> filteredPlaces = new ArrayList<Place>();
                    for (int i = 0; i < c; i++) {
                        filteredPlaces.add(pa.getItem(i));
                    }
                    locmap.clearLocations();
                    locmap.addLocation(filteredPlaces);
                    locmap.refreshMap();
                }
            });

        }

    };


}