package edu.umn.kill9.places.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.model.Place;

public class PlacesWebService extends AsyncTask<String, Void, List<Place>>{
	
	PlacesAPIJSONListener jsonListener;
	// http://maps.googleapis.com/maps/api/geocode/json?address=Minneapolis,+MN&sensor=true
	private static final LatLng DEFAULT_LOCATION = new LatLng(44.983334, -93.26666999999999); 
	
	public PlacesWebService(PlacesAPIJSONListener listenerActivity){
		this.jsonListener = listenerActivity;	
	}
	
	@Override
	protected List<Place> doInBackground(String... params) {
		
		Double currentLat = DEFAULT_LOCATION.latitude;
		Double currentLong = DEFAULT_LOCATION.longitude;
		String Device_location = currentLat +"," + currentLong;
		String baseurl = PlacesApplication.getContext().getResources().getString(R.string.PLACE_API_BASE_URL);
    
		//TODO: check if we can get the API_KEY from the Manifest file
		//String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
		String API_KEY = "AIzaSyAKb3QnPVarVnI8D9Fqh8qTuh2nVSosQd8";
		String url = baseurl+ "?location=" + Device_location + "&radius=2000&sensor=true&key=" + API_KEY;				
				
		AndroidHttpClient client = null;
		String json=null;
		ArrayList<Place> jsonResults = null;
    
		try {
            client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                json = EntityUtils.toString(entity);
            }
                         
            jsonResults = parseJSON(json, currentLat, currentLong);                       
        }
        catch (IOException ex) {
            Log.w("PlacesWebService", "Error getting web object", ex);
        }
        return jsonResults;
	}
	
	protected ArrayList<Place> parseJSON(String json, Double currentLat, Double currentLong ){
		 
		ArrayList<Place> jsonResults = null;
		
		try {
			JSONObject jsonObj = new JSONObject(json);
	        JSONArray resultArray = jsonObj.getJSONArray("results");
	         
	        Location currentLoc = new Location("CurrentLocation");
            currentLoc.setLatitude(currentLat);
            currentLoc.setLongitude(currentLong);
            
	        jsonResults = new ArrayList<Place>(resultArray.length());
	        for (int i = 0; i < resultArray.length(); i++) {
	             Place newplace = new Place();
	             
	             JSONObject geometryJson = resultArray.getJSONObject(i).getJSONObject("geometry");
	             JSONObject locationJson = geometryJson.getJSONObject("location");
	             
	             Double latitude = locationJson.getDouble("lat");
	             Double longitude = locationJson.getDouble("lng");
	      
	             Location destLocation = new Location("DestinationLocation");
	             destLocation.setLatitude(latitude);
	             destLocation.setLongitude(longitude);
	             float distance = currentLoc.distanceTo(destLocation);
	             newplace.setDistance(distance);
	             
	             newplace.setName(resultArray.getJSONObject(i).getString("name"));
	             newplace.setReference(resultArray.getJSONObject(i).getString("reference"));
	             newplace.setVicinity(resultArray.getJSONObject(i).getString("vicinity"));
	         	 jsonResults.add(newplace);
	         }
		}
		catch (JSONException ex) {
	        Log.w("PlacesWebService", "Cannot process JSON results", ex);
	    }
        
        return jsonResults;
	}
	
	protected void onPostExecute(List<Place> placesList){
		jsonListener.onWebServiceCallComplete(placesList);
	}
	public interface PlacesAPIJSONListener {
		public void onWebServiceCallComplete(List<Place> places);
	}
}
