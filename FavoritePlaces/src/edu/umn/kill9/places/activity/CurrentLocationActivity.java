package edu.umn.kill9.places.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;
import edu.umn.kill9.places.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import edu.umn.kill9.places.activity.fragment.AddCurrentLocFragment;
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

    private ArrayList<Place> places;
    private String currentLocation;

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
        getCurrentLocation();
    }

    @Override
    public void onWebServiceCallComplete(List<Place> placesList){
        places = new ArrayList<Place>();
        places.addAll(placesList);
        AddCurrentLocFragment f = (AddCurrentLocFragment)getFragmentManager().findFragmentById(R.id.currloclistfragment);
        f.setListAdapter(new PlaceAdapter(this, R.layout.place_item, places));
    }

    public void getCurrentLocation(){
        LocationManager locationMgr;
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // A new location update is received.  Do something useful with it.
                currentLocation = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
                PlacesWebService webservice = new PlacesWebService(CurrentLocationActivity.this);
                webservice.execute(currentLocation);
                Toast.makeText(getApplicationContext(), currentLocation, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStatusChanged(String provider, int status,Bundle extras)
            {
                // TODO Auto-generated method stub
            }
        };

        locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000, 0, listener);
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

}