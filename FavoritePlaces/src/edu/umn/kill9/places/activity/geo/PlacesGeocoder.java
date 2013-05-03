package edu.umn.kill9.places.activity.geo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import edu.umn.kill9.places.model.DRMLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 5/2/13
 */
public class PlacesGeocoder {

    Context context;

    public PlacesGeocoder(Context context) {
        this.context = context;
    }

    public List<DRMLocation> getFromLocationName(String searchText, int numResults) {
        Geocoder coder = new Geocoder(context);
        List<DRMLocation> places = new ArrayList<DRMLocation>();
        try {
            List<Address> addresses = coder.getFromLocationName(searchText, numResults);
            DRMLocation place;
            for (Address a : addresses) {
                place = new DRMLocation();
                place.setLocationPoint(new LatLng(a.getLatitude(),a.getLongitude()));
                String addr = "";
                for (int i = 0; i < a.getMaxAddressLineIndex(); i++) {
                    addr += a.getAddressLine(i) + " ";
                }
                place.setAddress(addr);
                place.setLocationName(a.getFeatureName());
                places.add(place);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return places;
    }


}
