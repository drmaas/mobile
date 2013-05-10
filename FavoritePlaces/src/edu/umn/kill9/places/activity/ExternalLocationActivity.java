package edu.umn.kill9.places.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.MapFragment;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.AddCurrentLocFragment;
import edu.umn.kill9.places.activity.fragment.MapSearchListFragment;
import edu.umn.kill9.places.activity.fragment.PlaceMapFragment;
import edu.umn.kill9.places.activity.geo.PlacesGeocoder;
import edu.umn.kill9.places.activity.geo.PlacesLocationManager;
import edu.umn.kill9.places.adapter.ExternalPlaceAdapter;
import edu.umn.kill9.places.adapter.PlaceAdapter;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.web.PlacesWebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class ExternalLocationActivity extends BaseActivity implements PlacesWebService.PlacesAPIJSONListener {

    private List<Place> places;
    private final int NUM_SEARCH_RESULTS = 20;

    private MapSearchListFragment loclist;
    private PlaceMapFragment locmap;

    private String searchText;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexternallocation);
        //hideKeyboard();

        //show 'up' button next to home icon
        showHomeAsUp(true);

        //setup listener on search button pressed on keyboard
        final EditText editText = (EditText)findViewById(R.id.mapsearchtext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchText = v.getText().toString();
                    performSearchCurrentLocation(v.getText().toString());
                    hideKeyboard();
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });

        //fragment references
        loclist = (MapSearchListFragment)getFragmentManager().findFragmentById(R.id.mapsearchlistfragment);
        locmap = (PlaceMapFragment)getFragmentManager().findFragmentById(R.id.mapsearchmapfragment);

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

    private void performSearchCurrentLocation(String searchText) {
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle bundle = ai.metaData;
        String myApiKey = bundle.getString("com.google.android.maps.v2.API_KEY");
        PlacesWebService webservice = new PlacesWebService(ExternalLocationActivity.this, myApiKey);
        PlacesLocationManager manager = new PlacesLocationManager(this, webservice, searchText);
        manager.getCurrentLocation();
    }

    /**
     *
     * @param searchText
     */
    private List<Place> performSearch(String searchText) {
        FragmentManager fm = getFragmentManager();
        PlaceMapFragment mf = (PlaceMapFragment)fm.findFragmentById(R.id.mapsearchmapfragment);
        MapSearchListFragment lf = (MapSearchListFragment)fm.findFragmentById(R.id.mapsearchlistfragment);

        PlacesGeocoder coder = new PlacesGeocoder(this);
        List<Place> addresses = coder.getFromLocationName(searchText,NUM_SEARCH_RESULTS);
        if(addresses.size() > 0) {
            mf.clearLocations();
            mf.addLocation(addresses);
            mf.refreshMap();

            lf.setListAdapter(new ExternalPlaceAdapter(this, R.layout.external_place_item, addresses));
        }
        else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(R.string.locations_title);
            ad.setMessage(R.string.invalid_search_string);
            ad.setPositiveButton(R.string.close, null);
            ad.show();
        }

        return addresses;

    }

    @Override
    public void onWebServiceCallComplete(List<Place> placesList) {
        if (placesList.size() < 1) {
            places = performSearch(searchText);
        }
        else {
            places = new ArrayList<Place>();
            places.addAll(placesList);
        }

        //refresh list
        loclist.setListAdapter(new PlaceAdapter(this, R.layout.place_item, places));

        //add points to map
        locmap.clearLocations();
        locmap.addLocation(places);
        locmap.refreshMap();
    }
}
