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
import edu.umn.kill9.places.model.DRMLocation;

public class PlacesWebService extends AsyncTask<String, Void, List<DRMLocation>>{

	private PlacesAPIJSONListener jsonListener;
	private String mapsAPIKey;

	public PlacesWebService(PlacesAPIJSONListener listenerActivity, String apiKey){
		this.jsonListener = listenerActivity;
		this.mapsAPIKey = apiKey;
	}

	@Override
	protected List<DRMLocation> doInBackground(String... params) {

		String device_location = params[0];
        Double currentLat = Double.parseDouble(device_location.split(",")[0]);
        Double currentLong = Double.parseDouble(device_location.split(",")[1]);

		String baseurl = PlacesApplication.getContext().getResources().getString(R.string.PLACE_API_BASE_URL);
		String url = baseurl+ "?location=" + device_location + "&radius=2000&sensor=true&key=" + mapsAPIKey;
				
		AndroidHttpClient client = null;
		String json=null;
		ArrayList<DRMLocation> jsonResults = null;
    
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
		finally
		{
			client.close();
		}
        return jsonResults;
	}
	
	protected ArrayList<DRMLocation> parseJSON(String json, Double currentLat, Double currentLong ){
		 
		ArrayList<DRMLocation> jsonResults = null;
		
		try {
			JSONObject jsonObj = new JSONObject(json);
	        JSONArray resultArray = jsonObj.getJSONArray("results");
	         
	        Location currentLoc = new Location("CurrentLocation");
            currentLoc.setLatitude(currentLat);
            currentLoc.setLongitude(currentLong);
            
	        jsonResults = new ArrayList<DRMLocation>(resultArray.length());
	        for (int i = 0; i < resultArray.length(); i++) {
                 DRMLocation newplace = new DRMLocation();
	             
	             JSONObject geometryJson = resultArray.getJSONObject(i).getJSONObject("geometry");
	             JSONObject locationJson = geometryJson.getJSONObject("location");
	             
	             Double latitude = locationJson.getDouble("lat");
	             Double longitude = locationJson.getDouble("lng");
	      
	             Location destLocation = new Location("DestinationLocation");
	             destLocation.setLatitude(latitude);
	             destLocation.setLongitude(longitude);
	             float distance = currentLoc.distanceTo(destLocation);
	             newplace.setDistance(distance);
	             
	             LatLng latLng = new LatLng(latitude, longitude);
	             newplace.setLocationPoint(latLng);
	             
	             newplace.setLocationName(resultArray.getJSONObject(i).getString("name"));
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
	
	protected void onPostExecute(List<DRMLocation> placesList){
		jsonListener.onWebServiceCallComplete(placesList);
	}

	public interface PlacesAPIJSONListener {
		public void onWebServiceCallComplete(List<DRMLocation> places);
	}
}
