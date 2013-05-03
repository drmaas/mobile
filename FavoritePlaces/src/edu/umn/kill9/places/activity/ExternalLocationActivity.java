package edu.umn.kill9.places.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
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
import edu.umn.kill9.places.activity.fragment.MapSearchListFragment;
import edu.umn.kill9.places.activity.fragment.PlaceMapFragment;
import edu.umn.kill9.places.activity.geo.PlacesGeocoder;
import edu.umn.kill9.places.adapter.ExternalPlaceAdapter;
import edu.umn.kill9.places.model.DRMLocation;

import java.io.IOException;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class ExternalLocationActivity extends BaseActivity {

    private final int NUM_SEARCH_RESULTS = 20;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexternallocation);

        //show 'up' button next to home icon
        showHomeAsUp(true);

        //setup listener on search button pressed on keyboard
        final EditText editText = (EditText)findViewById(R.id.mapsearchtext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(v.getText().toString());
                    hideKeyboard();
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });

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
     * TODO currently uses Geocoder to search, should we use places API instead?
     *
     * @param searchText
     */
    private void performSearch(String searchText) {
        FragmentManager fm = getFragmentManager();
        PlaceMapFragment mf = (PlaceMapFragment)fm.findFragmentById(R.id.mapsearchmapfragment);
        MapSearchListFragment msf = (MapSearchListFragment)fm.findFragmentById(R.id.mapsearchlistfragment);

        PlacesGeocoder coder = new PlacesGeocoder(this);
        List<DRMLocation> addresses = coder.getFromLocationName(searchText,NUM_SEARCH_RESULTS);
        if(addresses.size() > 0) {
            mf.clearLocations();
            mf.addLocation(addresses);
            mf.refreshMap();

            msf.setListAdapter(new ExternalPlaceAdapter(this, R.layout.external_place_item, addresses));
        }
        else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(R.string.locations_title);
            ad.setMessage(R.string.invalid_search_string);
            ad.setPositiveButton(R.string.close, null);
            ad.show();
        }

    }
}
