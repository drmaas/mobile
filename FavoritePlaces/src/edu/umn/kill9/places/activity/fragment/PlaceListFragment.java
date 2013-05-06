package edu.umn.kill9.places.activity.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.ParseException;
import edu.umn.kill9.places.activity.BaseActivity;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra(PlacesConstants.LOCATION_KEY, (Place)getListAdapter().getItem(position));
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }

    /**
     *
     * @param locations
     */
    public void setLocations(List<Place> locations) {
        this.locations = locations;
    }

}

