package edu.umn.kill9.places.activity.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;
import edu.umn.kill9.places.web.PlaceDetailWebService;
import edu.umn.kill9.places.web.PlaceDetailWebService.PlacesAPIJSONListener;

import java.util.ArrayList;

/**
 * User: drmaas
 * Date: 4/28/13
 */
public class MapSearchListFragment extends ListFragment implements PlacesAPIJSONListener {


    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Populate list with no initial data
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, new ArrayList<String>()));
    }

    /**
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Place location = (Place)getListAdapter().getItem(position);
    	
    	Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + location.getPlaceName(), Toast.LENGTH_SHORT).show();
	       
	    PlaceDetailWebService placeDetails = new PlaceDetailWebService(this);
	    placeDetails.execute(location);
	    
    	
    }

	@Override
	public void onWebServiceCallComplete(Place placeUpdated) {
		
		Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        
        intent.putExtra(PlacesConstants.LOCATION_KEY, placeUpdated);
        startActivityForResult(intent, PlacesConstants.DETAILS);
		
	}
}
