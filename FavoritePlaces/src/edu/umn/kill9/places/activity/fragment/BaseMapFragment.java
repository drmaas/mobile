package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Location;

public abstract class BaseMapFragment extends MapFragment {

	protected static final int LAT_LNG_BOUNDS_PADDING = 50;
	protected static final float MAX_DEFAULT_ZOOM = 16.0f;

	// http://maps.googleapis.com/maps/api/geocode/json?address=Minneapolis,+MN&sensor=true
	protected static final LatLng DEFAULT_LOCATION = new LatLng(44.983334, -93.26666999999999); 
	
    protected GoogleMap _map;
    protected ArrayList<Location> _locations;
    protected ArrayList<Marker> _markers;
    protected boolean _multiplePoints;
    
    public BaseMapFragment()
    {
    	super();
		clearLocations();
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( savedInstanceState == null || !savedInstanceState.containsKey("locationList"))
        {
        	if ( _locations == null )
        	{
        		// Initialize the list
        		clearLocations();
        	}
        }
        else
        {
        	// Restore the list
        	_locations = savedInstanceState.getParcelableArrayList("locationList");
        }
        
        setUpMapIfNeeded();
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("locationList", _locations);
//        super.onSaveInstanceState(outState);
    }

    @Override
	public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    protected void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (_map == null)
        {
            // Try to obtain the map from the SupportMapFragment.
        	_map = getMap();
            // Check if we were successful in obtaining the map.
            if (_map != null) {
                setUpMap();
            }
        }
    }

    protected void setUpMap() {
    	_map.setMyLocationEnabled(true);
    	_map.getUiSettings().setZoomControlsEnabled(true);

        // Add markers to the map.
        addMarkersToMap();

        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size.
        final View mapView = getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                      mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                      mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    
                    updateCamera();
                }
            });
        }
    }
    
    protected abstract CameraUpdate getCameraUpdate();
    
    protected void updateCamera()
    {
    	CameraUpdate cu = getCameraUpdate();
    	_map.moveCamera(cu);
    	
    	if ( _map.getCameraPosition().zoom > MAX_DEFAULT_ZOOM )
    	{
        	_map.moveCamera(CameraUpdateFactory.zoomTo(MAX_DEFAULT_ZOOM));
    	}
    }
    
    protected void addMarkersToMap()
    {
    	// TODO: Probably need to clear the exiting location on the map before adding all of them
    	
    	for ( Location loc : _locations )
        {
        	LatLng locPoint = loc.getLocationPoint();
        	String locName = loc.getLocationName();
        	
        	_markers.add(_map.addMarker( new MarkerOptions()
					.position( locPoint )
					.title( locName )
					//.snippet("Snippet Text Here")
					.icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) )
        			)
			);
        }

    }
    
    protected void navigateToLocation( Location location )
    {
		if ( location != null )
		{
	    	try {
	    		LatLng latLong = location.getLocationPoint();
	    		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + latLong.latitude + "," + latLong.longitude)));
	    	} catch (android.content.ActivityNotFoundException ex) {
	    	    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_maps_msg), Toast.LENGTH_SHORT).show();
	    	}
		}
		else
		{
			Toast.makeText(getActivity().getApplicationContext(), "Location is null", Toast.LENGTH_SHORT).show();
		}
    }
    
    public boolean addLocation(Location[] arrayLocation)
    {
    	boolean retValue = false;
    	
    	if ( _locations != null )
    	{
    		for ( Location loc : arrayLocation )
    		{
    			retValue |= _locations.add( loc );
    		}
    	}
    	
    	refreshMap();
    	
    	return retValue;
    }
    
    public boolean addLocation(List<Location> listLocation)
    {
    	boolean retValue = false;
    	
    	if ( _locations != null )
    	{
    		retValue = _locations.addAll(listLocation);
    	}
    	
    	refreshMap();
    	
    	return retValue;
    }
    
    public boolean addLocation(Location location)
    {
    	boolean retValue = false;
    	
    	if ( _locations != null )
    	{
    		retValue = _locations.add(location);
    	}
    	
    	refreshMap();
    	
    	return retValue;
    }
    
    public void clearLocations()
    {
    	_locations = new ArrayList<Location>();
    	_markers = new ArrayList<Marker>();
    	
    	refreshMap();
    }
    
	protected void refreshMap()
	{
		//TODO: Somehow refresh the map
	}
    
}
