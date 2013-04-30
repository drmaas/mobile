package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.Location;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

public class BaseMapFragment extends MapFragment
implements OnInfoWindowClickListener {

	private static final int LAT_LNG_BOUNDS_PADDING = 50;
	private static final float MAX_DEFAULT_ZOOM = 16.0f;

	// http://maps.googleapis.com/maps/api/geocode/json?address=Minneapolis,+MN&sensor=true
	private static final LatLng DEFAULT_LOCATION = new LatLng(44.983334, -93.26666999999999); 
	
    private GoogleMap _map;
    private List<Location> _locations;
    private boolean _multiplePoints;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> locationsString;
	    
        Bundle args = getArguments();
        
		if ( args == null )
		{
			_multiplePoints = false;
			locationsString = new ArrayList<String>();
		}
		else
		{
			_multiplePoints = args.getBoolean("multiplePoints", false);
		    locationsString = args.getStringArrayList("locations");
		}
        
	    _locations = new ArrayList<Location>( locationsString.size() );
	    
	    for ( String locationName : locationsString )
	    {
	        Location loc = SampleLocationList.findByLocationName(locationName);
	    	_locations.add( loc );
	    }
	    
        if ( !_multiplePoints )
        {
        	setHasOptionsMenu(true);
        }

        setUpMapIfNeeded();
	}

    @Override
	public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
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

    private void setUpMap() {
    	_map.setMyLocationEnabled(true);
    	_map.getUiSettings().setZoomControlsEnabled(true);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Set listeners for marker events.
        _map.setOnInfoWindowClickListener(this);

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
                    if ( _multiplePoints )
                    {
                    	if (_locations.isEmpty() )
                    	{
                			_map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, MAX_DEFAULT_ZOOM));
                    	}
                    	else
                    	{
                    		// Display all the points on the screen
                    		
	                        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
	                        
	                        for ( Location loc : _locations )
	                        {
	                        	LatLng locPoint = loc.getLocationPoint();
	                        	boundsBuilder.include( locPoint );
	                        }
	                        LatLngBounds bounds = boundsBuilder.build();
	                        
	                        // Make sure the zoom isn't too close
	                    	_map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, LAT_LNG_BOUNDS_PADDING));
	                    	if ( _map.getCameraPosition().zoom > MAX_DEFAULT_ZOOM )
	                    	{
	                        	_map.moveCamera(CameraUpdateFactory.zoomTo(MAX_DEFAULT_ZOOM));
	                    	}
                    	}
                    }
                    else
                    {
                    	Location loc = _locations.get(0);
                    	_map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc.getLocationPoint(), MAX_DEFAULT_ZOOM));
                    }
                }
            });
        }
    }
    
    private void addMarkersToMap()
    {        
        for ( Location loc : _locations )
        {
        	LatLng locPoint = loc.getLocationPoint();
        	String locName = loc.getLocationName();
        	
        	Marker m;
        	m = _map.addMarker( new MarkerOptions()
					.position( locPoint )
					.title( locName )
					//.snippet("Snippet Text Here")
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
			);
        	
        	// Only display the marker if the number of locations is 1
        	if ( !_multiplePoints )
        	{
        		m.showInfoWindow();
        	}
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	if ( !_multiplePoints )
    	{
    		inflater.inflate(R.menu.details_map_menu, menu);
    		setUpMapIfNeeded();
    	}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if ( !_multiplePoints )
    	{
    		Location location = _locations.get(0);
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.get_directions:
        			navigateToLocation( location );
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
    
    private void navigateToLocation( Location location )
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

	@Override
	public void onInfoWindowClick(Marker mark) {
        if ( _multiplePoints )
        {
			Intent intent = new Intent();
	        intent.setClass(getActivity(), PlaceDetailsActivity.class);
	        intent.putExtra("locationName", mark.getTitle());
	        startActivityForResult(intent, PlacesConstants.DETAILS);
		}
    }
    
}
