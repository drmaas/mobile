package edu.umn.kill9.places.activity.fragment;

import android.app.Activity;
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
import edu.umn.kill9.places.activity.PlacesActivity;
import edu.umn.kill9.places.model.Place;
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
            
            for ( Place loc : _locations )
            {
            	LatLng locPoint = loc.getPlacePoint();
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
		
		String markTitle = mark.getTitle();
		
		Place locFound = null;
		for ( Place loc : _locations )
		{
			String locName = loc.getPlaceName();
			if ( locName.equals(markTitle) )
			{
				locFound = loc;
				break;
			}
		}
		
		if ( locFound == null )
		{
		    Toast.makeText(getActivity().getApplicationContext(), "Can't find location for: " + markTitle, Toast.LENGTH_SHORT).show();
		}
		else
		{
		
			Intent intent = new Intent();
	        intent.setClass(getActivity(), PlaceDetailsActivity.class);
	
	        //intent.putExtra("locationName", locFound.getPlaceName());
	        //intent.putExtra("address", locFound.getAddress());
	        //intent.putExtra("hours", locFound.getHours());
	        //intent.putExtra("phone", locFound.getPhone());
	        //intent.putExtra("vicinity", locFound.getVicinity());
	        //intent.putExtra("website", locFound.getWebsite());
	        //intent.putExtra("latitude", locFound.getPlacePoint().latitude);
	        //intent.putExtra("longitude", locFound.getPlacePoint().longitude);

            intent.putExtra(PlacesConstants.LOCATION_KEY, locFound);
	        startActivityForResult(intent, PlacesConstants.DETAILS);
		}
    }

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PlacesConstants.ADD_CURRENT_LOCATION) {
            // TODO: There could be some refactoring here
            //Update regardless of the resultCode
        	Activity act = getActivity();
        	if (act instanceof PlacesActivity) {
        		setLocations(((PlacesActivity)act).getAllPlaces());
        		refreshMap();
        	}
        }
        else if (requestCode == PlacesConstants.ADD_EXTERNAL_LOCATION) {
            // TODO: There could be some refactoring here
            //Update regardless of the resultCode
        	Activity act = getActivity();
        	if (act instanceof PlacesActivity) {
        		setLocations(((PlacesActivity)act).getAllPlaces());
        		refreshMap();
        	}
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == PlacesConstants.PREFERENCES) {
            //do something here when returning from preferences
        }
        else if (requestCode == PlacesConstants.DETAILS) {
            // TODO: There could be some refactoring here
            //Update regardless of the resultCode
        	Activity act = getActivity();
        	if (act instanceof PlacesActivity) {
        		setLocations(((PlacesActivity)act).getAllPlaces());
        		refreshMap();
        	}
        }
    }
}
