package edu.umn.kill9.places.activity.fragment;

import android.app.ListFragment;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.*;

import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.util.PlacesConstants;


public class AddCurrentLocFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Place place = (Place)getListAdapter().getItem(position);
	    Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + place.getPlaceName() + "\n" + place.getPlacePoint(), Toast.LENGTH_SHORT).show();
	    
	    //TODO: Do something when this item is clicked
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra("locationName", place.getPlaceName());
        intent.putExtra("address", place.getAddress());
        intent.putExtra("hours", place.getHours());
        intent.putExtra("phone", place.getPhone());
        intent.putExtra("vicinity", place.getVicinity());
        intent.putExtra("website", place.getWebsite());
        intent.putExtra("latitude", place.getPlacePoint().latitude);
        intent.putExtra("longitude", place.getPlacePoint().longitude);
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }

}
