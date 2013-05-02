package edu.umn.kill9.places.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.DRMLocation;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class PlaceMapFragment extends BaseMapFragment
		implements OnInfoWindowClickListener {
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpMap() {
        // Set listeners for marker events.
        _map.setOnInfoWindowClickListener(this);
    	super.setUpMap();
    }
    
    @Override
    protected CameraUpdate getCameraUpdate()
    {
    	CameraUpdate retVal;
    	
    	if (_locations.isEmpty() )
    	{
    		retVal = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM);
    	}
    	else
    	{
    		// Display all the points on the screen
    		
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            
            for ( DRMLocation loc : _locations )
            {
            	LatLng locPoint = loc.getLocationPoint();
            	boundsBuilder.include( locPoint );
            }
            LatLngBounds bounds = boundsBuilder.build();
            
            // Make sure the zoom isn't too close
            retVal = CameraUpdateFactory.newLatLngBounds(bounds, LAT_LNG_BOUNDS_PADDING);
    	}
    	
    	return retVal;
    }

	@Override
	public void onInfoWindowClick(Marker mark) {
	    //TODO: Do something when this item is clicked
		
		Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra("locationName", mark.getTitle());
        intent.putExtra("latitude", mark.getPosition().latitude);
        intent.putExtra("longitude", mark.getPosition().longitude);
        startActivityForResult(intent, PlacesConstants.DETAILS);
        
	    Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + mark.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
