package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.PlaceAdapter;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.web.PlacesWebService;
import edu.umn.kill9.places.web.PlacesWebService.PlacesAPIJSONListener;

public class AddCurrentLocFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Place place = (Place)getListAdapter().getItem(position);
	    Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + place.getName() + "\n" + place.getLatLng(), Toast.LENGTH_SHORT).show();
	    
	    //TODO: Do something when this item is clicked
    }

}
