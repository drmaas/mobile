package edu.umn.kill9.places.activity.geo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.model.LatLng;
import edu.umn.kill9.places.model.Place;

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

    public List<Place> getFromLocationName(String searchText, int numResults) {
        Geocoder coder = new Geocoder(context);
        List<Place> places = new ArrayList<Place>();
        try {
            List<Address> addresses = coder.getFromLocationName(searchText, numResults);
            Place place;
            for (Address a : addresses) {
                place = new Place();
                place.setPlacePoint(new LatLng(a.getLatitude(),a.getLongitude()));
                String addr = "";
                for (int i = 0; i < a.getMaxAddressLineIndex(); i++) {
                    addr += a.getAddressLine(i) + " ";
                }
                place.setAddress(addr);
                place.setPlaceName(a.getFeatureName());
                places.add(place);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return places;
    }


}
