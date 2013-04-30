package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umn.kill9.places.R;

import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.web.PlacesWebService;
import edu.umn.kill9.places.web.PlacesWebService.PlacesAPIJSONListener;

public class AddCurrentLocFragment extends ListFragment implements PlacesAPIJSONListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.placelist_main);
		
		getPlaces();
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
*/	
	public void getPlaces(){
		PlacesWebService webservice = new PlacesWebService(this);
		webservice.execute("Test");
	}   
	
	@Override
	 public void onWebServiceCallComplete(List<Place> placesList){
	        ArrayList<Place> places = new ArrayList<Place>();
	        places.addAll(placesList);
	        
	    	setListAdapter(new PlaceAdapter(getActivity(),R.layout.place_item ,places));
	    }
	    
	 public class PlaceAdapter extends ArrayAdapter<Place>{
		 public PlaceAdapter(Context context, int textViewResourceId, List<Place> places) {
				super(context, textViewResourceId, places);
			}		

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View item = inflater.inflate(R.layout.place_item, parent, false);
				Place place = getItem(position);
				((TextView)item.findViewById(R.id.place_name)).setText(place.getName());
				((TextView)item.findViewById(R.id.place_vicinity)).setText(place.getVicinity());
				((TextView)item.findViewById(R.id.place_distance)).setText(place.getDistance());
				
				return item;
			}
	 }
	

}