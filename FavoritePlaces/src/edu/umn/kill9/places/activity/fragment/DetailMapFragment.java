package edu.umn.kill9.places.activity.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Marker;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Place;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailMapFragment extends BaseMapFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_map_menu, menu);
        setUpMapIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Place loc = getLocation();

		if ( loc != null )
		{
	    	// Handle item selection
	        switch (item.getItemId()) {
	            case R.id.get_directions:
	    			navigateToLocation( loc );
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
		}
		else
		{
            return super.onOptionsItemSelected(item);
		}
    }
    
    @Override
    protected CameraUpdate getCameraUpdate()
    {
    	CameraUpdate retVal = null;
    	Place loc = getLocation();

		if ( loc != null )
		{
	    	retVal = CameraUpdateFactory.newLatLngZoom(loc.getPlacePoint(), MAX_DEFAULT_ZOOM);
		}
		else
    	{
    		retVal = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM);
    	}
		
    	return retVal;
    }

    @Override
    protected void addMarkersToMap()
    {
    	super.addMarkersToMap();
    	
    	Marker mark = getMarker();
    	mark.showInfoWindow();
    }
    
    private Place getLocation()
    {
    	Place retVal = null;

    	if ( _locations != null && _locations.size() > 0 )
		{
			// There should only be one, but grab just the first one
    		retVal = _locations.get(0);
		}

    	return retVal;
    }
    
    private Marker getMarker()
    {
    	Marker retVal = null;

    	if ( _markers != null && _markers.size() > 0 )
		{
			// There should only be one, but grab just the first one
    		retVal = _markers.get(0);
		}

    	return retVal;
    }
}
