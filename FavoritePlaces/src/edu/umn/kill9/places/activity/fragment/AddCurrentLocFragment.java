package edu.umn.kill9.places.activity.fragment;

import android.app.ListFragment;

import android.os.Bundle;

import android.view.View;

import android.widget.*;

import edu.umn.kill9.places.model.DRMLocation;


public class AddCurrentLocFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DRMLocation place = (DRMLocation)getListAdapter().getItem(position);
	    Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + place.getLocationName() + "\n" + place.getLocationPoint(), Toast.LENGTH_SHORT).show();
	    
	    //TODO: Do something when this item is clicked
    }

}
