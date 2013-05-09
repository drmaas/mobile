package edu.umn.kill9.places.activity.geo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import edu.umn.kill9.places.web.PlacesWebService;

/**
 * User: drmaas
 * Date: 5/8/13
 */
public class PlacesLocationManager {

    private Activity activity;
    private PlacesWebService placesWebService;
    private String keyword;

    public PlacesLocationManager(Activity activity, PlacesWebService placesWebService, String keyword) {
        this.activity = activity;
        this.placesWebService = placesWebService;
        this.keyword = keyword;
    }

    /**
     *
     */
    public void getCurrentLocation() {
        final LocationManager locationMgr = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        LocationListener listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                // A new location update is received.  Do something useful with it.
                String currentLocation = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());

                // Execute query
                placesWebService.execute(currentLocation, keyword);
                Toast.makeText(activity.getApplicationContext(), location.getProvider() + ": " + currentLocation, Toast.LENGTH_SHORT).show();

                //move map to current location
                //locmap.moveToLocation(location);

            }
            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status,Bundle extras)
            {
            }
        };

        locationMgr.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                listener,
                null // Use the callback on the calling thread
        );

        locationMgr.requestSingleUpdate(
                LocationManager.NETWORK_PROVIDER,
                listener,
                null // Use the callback on the calling thread
        );
    }

}
