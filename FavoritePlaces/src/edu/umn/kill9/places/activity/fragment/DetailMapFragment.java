package edu.umn.kill9.places.activity.fragment;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Location;
import edu.umn.kill9.places.model.data.SampleLocationList;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailMapFragment extends MapFragment {
	
	private static final float DEFAULT_ZOOM_CLOSE = 14.0f;
    private GoogleMap _map;
    private Location _location;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        String locationName = getActivity().getIntent().getStringExtra("locationName");
        _location = SampleLocationList.findByLocationName(locationName);

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
        // Hide the zoom controls as the button panel will cover it.
    	_map.setMyLocationEnabled(true);
    	_map.getUiSettings().setZoomControlsEnabled(true);

        // Add lots of markers to the map.
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
                    _map.moveCamera(CameraUpdateFactory.newLatLngZoom(_location.getLocationPoint(), DEFAULT_ZOOM_CLOSE));
                }
            });
        }
    }
    
    private void addMarkersToMap()
    {
        Location loc = _location;

        LatLng locPoint = loc.getLocationPoint();
    	String locName = loc.getLocationName();
        	
    	Marker m = _map.addMarker( new MarkerOptions()
    					.position( locPoint )
    					.title( locName )
    					//.snippet("Snippet Text Here")
    					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
    				)
		);
    	
    	m.showInfoWindow();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_map_menu, menu);
        setUpMapIfNeeded();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.get_directions:
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
