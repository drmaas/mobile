package edu.umn.kill9.places.activity.fragment;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.Location;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class PlaceMapFragment extends MapFragment
		implements OnInfoWindowClickListener {
	
	private static final int LAT_LNG_BOUNDS_PADDING = 50;

    private GoogleMap _map;
    private List<Marker> _markers;
    private List<Location> locations;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.places_fragment);

        locations = SampleLocationList.getLocations();

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
        // Hide the zoom controls as the button panel will cover it.
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
                    LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                    
                    for ( Location loc : locations )
                    {
                    	LatLng locPoint = loc.getLocationPoint();
                    	boundsBuilder.include( locPoint );
                    }
                    
                    LatLngBounds bounds = boundsBuilder.build();
                    
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                      mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                      mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    _map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, LAT_LNG_BOUNDS_PADDING));
                }
            });
        }
    }
    
    private void addMarkersToMap()
    {
    	_markers = new LinkedList<Marker>();
        
        for ( Location loc : locations )
        {
        	LatLng locPoint = loc.getLocationPoint();
        	String locName = loc.getLocationName();
        	
        	_markers.add( _map.addMarker( new MarkerOptions()
        					.position( locPoint )
        					.title( locName )
        					//.snippet("Snippet Text Here")
        					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
							)
        				)
    			);
        }

    }

	@Override
	public void onInfoWindowClick(Marker mark) {
		Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra("locationName", mark.getTitle());
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }
}
