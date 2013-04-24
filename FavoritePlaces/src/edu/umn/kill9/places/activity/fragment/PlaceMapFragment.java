package edu.umn.kill9.places.activity.fragment;

import java.util.List;

import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;

import edu.umn.kill9.places.model.data.SampleLocationList;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class PlaceMapFragment extends MapFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> places = SampleLocationList.getLocations();
        
        
        
        //new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, SampleLocationList.getLocations());
        
        // Populate list with our static array of titles.
        /* setListAdapter(new ArrayAdapter<String>(
        getActivity(),
        android.R.layout.simple_list_item_activated_1,
        SampleLocationList.getLocations()
        ));
        */
    }
/*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra("locationName", (String)getListAdapter().getItem(position));
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }
*/
}
