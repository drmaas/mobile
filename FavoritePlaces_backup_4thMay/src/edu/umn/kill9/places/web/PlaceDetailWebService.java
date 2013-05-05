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

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.model.DRMLocation;
import edu.umn.kill9.places.web.PlacesWebService.PlacesAPIJSONListener;

public class PlaceDetailWebService extends AsyncTask<DRMLocation, Void, Void>{
	private PlacesAPIJSONListener jsonListener;
	private String mapsAPIKey;
	
	public PlaceDetailWebService (String apiKey){
		//this.jsonListener = listenerActivity;
		//this.mapsAPIKey = apiKey;
		this.mapsAPIKey = "AIzaSyAKb3QnPVarVnI8D9Fqh8qTuh2nVSosQd8";
	}
	
	@Override
	protected Void doInBackground(DRMLocation... params) {
		
		String baseurl = PlacesApplication.getContext().getResources().getString(R.string.PLACE_DETAIL_API_URL);
		String url = baseurl+ "?reference=" + params[0].getReference() + "&sensor=true&key=" + mapsAPIKey;
				
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
        params[0].setPhone(parseJSON(json));           
        //    jsonResults = parseJSON(json, currentLat, currentLong);                       
        }
        catch (IOException ex) {
            Log.w("PlacesWebService", "Error getting web object", ex);
        }
		finally
		{
			client.close();
		}
        //return jsonResults;
		
		return null;
	}
	
	protected void onPostExecute(List<DRMLocation> placesList){
		jsonListener.onWebServiceCallComplete(placesList);
	}
	
	protected String parseJSON(String json){
		String phone = "test_phone";
		try {
			JSONObject jsonObj = new JSONObject(json);
	        JSONObject resultObject = jsonObj.getJSONObject("result");
	        if(resultObject.has("formatted_phone_number") )
	        	phone = resultObject.getString("formatted_phone_number");
	        
	        
		} catch (JSONException ex) {
		        Log.w("PlacesWebService", "Cannot process JSON results", ex);
		    }
		return phone;
	}
	public interface PlacesAPIJSONListener {
		public void onWebServiceCallComplete(List<DRMLocation> places);
	}
}
