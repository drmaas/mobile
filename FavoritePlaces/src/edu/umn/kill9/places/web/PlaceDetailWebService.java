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
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.web.PlacesWebService.PlacesAPIJSONListener;

public class PlaceDetailWebService extends AsyncTask<Place, Void, Place>{
	private PlacesAPIJSONListener jsonListener;
	
	
	public PlaceDetailWebService (PlacesAPIJSONListener listenerActivity){
		this.jsonListener = listenerActivity;
	}
	
	@Override
	protected Place doInBackground(Place... params) {
		Place placeClicked = params[0];
		String baseurl = PlacesApplication.getContext().getResources().getString(R.string.PLACE_DETAIL_API_URL);
		String apiKey = PlacesApplication.getContext().getResources().getString(R.string.MY_API_KEY);
		String url = baseurl+ "?reference=" + placeClicked.getReference() + "&sensor=true&key=" + apiKey;
				
		AndroidHttpClient client = null;
		String json = null;
    
		try {
            client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                json = EntityUtils.toString(entity);
            }
            parseJSON(json,placeClicked);
            //placeClicked.setPhone(parseJSON(json));           
         
        }
        catch (IOException ex) {
            Log.w("PlacesWebService", "Error getting web object", ex);
        }
		finally
		{
			client.close();
		}
		
		return placeClicked;
	}
	
	protected void onPostExecute(Place place){
		jsonListener.onWebServiceCallComplete(place);
	}
	
	protected void parseJSON(String json, Place place){
		try {
			JSONObject jsonObj = new JSONObject(json);
	        JSONObject resultObject = jsonObj.getJSONObject("result");
            if(resultObject.has("formatted_address") )
            {
                place.setAddress(resultObject.getString("formatted_address"));
            }
            if(resultObject.has("formatted_phone_number") )
	        {
	        	place.setPhone(resultObject.getString("formatted_phone_number"));
	        }        
	        if(resultObject.has("website"))
	        {
	        	place.setWebsite(resultObject.getString("website"));
	        }
	        
	        if(resultObject.has("opening_hours"))
	        {
	        	
	        	JSONObject hoursObj = resultObject.getJSONObject("opening_hours");
	        	JSONArray daysArray = hoursObj.getJSONArray("periods");
	        	StringBuilder hourString = new StringBuilder();
	        	boolean found = false;
	        	for(int index=0; index< daysArray.length(); index++)
	        	{	
	        		JSONObject openingHour= daysArray.getJSONObject(index).getJSONObject("open");
	        		JSONObject closingHour= daysArray.getJSONObject(index).getJSONObject("close");
	        		String openingtime = openingHour.getString("time");
	        		String Ominutes = openingtime.substring(2);
	        		String Ohours = openingtime.substring(0, 2);
	        		String formatedOpeningTime = Ohours   + ":" + Ominutes ;
	        		
	        		String closingtime = closingHour.getString("time");
	        		String Cminutes = closingtime.substring(2);
	        		String Chours = closingtime.substring(0, 2);
	        		String formatedClosingTime = Chours + ":" + Cminutes;
	        		
	        		int day = openingHour.getInt("day");
	        		
	        		if(day == 0)
	        		{
	        			String temp = "Sun: " + formatedOpeningTime + " - " + formatedClosingTime + "; \n";
	        			hourString = hourString.append(temp);
	        		}	
	        		else if(day == 6)
	        		{
	        			String temp = "Sat: "+ formatedOpeningTime + " - " + formatedClosingTime + "; \n";
	        			hourString = hourString.append(temp);
	        		}
	        		else
	        		{
	        			if(found == false)
	        			{
	        				String temp = "Mon - Fri: "+ formatedOpeningTime + " - " + formatedClosingTime + "; \n";
	        				hourString = hourString.append(temp);
	        				found = true;
	        			}
	        		}
	        	} // end of for loop
	        	
	        	place.setHours(hourString.toString());
	        } // end of if("opening_hours")
		
		} catch (JSONException ex) {
		        Log.w("PlacesWebService", "Cannot process JSON results", ex);
		    }
	}
	
	public interface PlacesAPIJSONListener {
		public void onWebServiceCallComplete(Place placeUpdated);
	}
}
