package edu.umn.kill9.places.activity.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.ParseException;
import edu.umn.kill9.places.activity.BaseActivity;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.activity.PlacesActivity;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.model.PlaceDataSource;
import edu.umn.kill9.places.model.PlaceUser;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class PlaceListFragment extends ListFragment {

    private List<Place> locations;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_activated_1, locations));
    }

    @Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        setListAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_activated_1, locations));
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra(PlacesConstants.LOCATION_KEY, (Place)getListAdapter().getItem(position));
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PlacesConstants.ADD_CURRENT_LOCATION) {
            // TODO: There could be some refactoring here
            //Update regardless of the resultCode
        	Activity act = getActivity();
        	if (act instanceof PlacesActivity) {
        		setLocations(((PlacesActivity)act).getAllPlaces());
                setListAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_activated_1, locations));
        	}
        }
        else if (requestCode == PlacesConstants.ADD_EXTERNAL_LOCATION) {
            // TODO: There could be some refactoring here
            //Update regardless of the resultCode
        	Activity act = getActivity();
        	if (act instanceof PlacesActivity) {
        		setLocations(((PlacesActivity)act).getAllPlaces());
                setListAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_activated_1, locations));
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
                setListAdapter(new ArrayAdapter<Place>(getActivity(), android.R.layout.simple_list_item_activated_1, locations));
        	}
        }

	}

	/**
     *
     * @param locations
     */
    public void setLocations(List<Place> locations) {
        this.locations = locations;
    }

}

