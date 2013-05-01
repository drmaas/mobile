package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.umn.kill9.places.R;
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

<<<<<<< HEAD
=======
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
*/	
	public void getPlaces(){
		try
		{
			ApplicationInfo ai = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
		    Bundle bundle = ai.metaData;
		    String myApiKey = bundle.getString("com.google.android.maps.v2.API_KEY");
			PlacesWebService webservice = new PlacesWebService(this, myApiKey);
			webservice.execute("Test");
		}
		catch (NameNotFoundException e)
		{
		    Log.e("AddCurrentLocFragment", "Failed to load meta-data, NameNotFound: " + e.getMessage());
		}
		catch (NullPointerException e)
		{
		    Log.e("AddCurrentLocFragment", "Failed to load meta-data, NullPointer: " + e.getMessage());         
		}
	}   
	
	@Override
	 public void onWebServiceCallComplete(List<Place> placesList){
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
	

>>>>>>> 3dbdc7766fc799b963694870a996ab98c0e02268
}
