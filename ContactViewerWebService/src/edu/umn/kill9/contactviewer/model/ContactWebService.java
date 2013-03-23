package edu.umn.kill9.contactviewer.model;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.umn.kill9.contactviewer.R;
//import android.R;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import android.util.Log;

import edu.umn.kill9.contactviewer.model.JsonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactWebService extends AsyncTask<String, Void, List<ContactJson>>{
	
	GetJsonListener jsonListener;
	
	public ContactWebService(GetJsonListener listenerActivity){
		this.jsonListener = listenerActivity;	
	}
	
	@Override
	protected List<ContactJson> doInBackground(String... params) {
		String URL_BASE = "http://contacts.tinyapollo.com/contacts?key=kill-9";
			
		String key = params[0];
		
		List<ContactJson> contactsListJson = null;
		try {
			AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
			HttpUriRequest request = new HttpGet(URL_BASE);
			HttpResponse response = client.execute(request);
			Gson gson = new Gson();

			JsonResponse jsonResponse = gson.fromJson(new InputStreamReader(response.getEntity().getContent()), JsonResponse.class);
			
			contactsListJson = jsonResponse.contacts;
			client.close();					
		}
		catch (Exception ex) {
			Log.w("GetContactTask", "Error getting contact", ex);
		}
		finally{
			// This result is pasesed to the onPostExecute method
			return contactsListJson;
		}		
	}
	
	
	@Override
	protected void onPostExecute(List<ContactJson> contactsListJson){
		List<Contact> contactsList = GetContactsListFromJsonList(contactsListJson);
		jsonListener.onWebServiceCallComplete(contactsList);
	}
	
	private ArrayList<Contact> GetContactsListFromJsonList(List<ContactJson> contactsListJson){
    	ArrayList<Contact> contacts = new ArrayList<Contact>();
    	
    	for(int i=0;i<contactsListJson.size();i++){
    		ContactJson jsonContact = contactsListJson.get(i);
    		Contact contact = new Contact(jsonContact.name);
    		contact.setEmail(jsonContact.email);
    		//contact.setId(jsonContact._id);
    		contact.setPhone(jsonContact.phone);
    		contact.setTitle(jsonContact.title);
    		contact.setTwitterId(jsonContact.twitterId);
    		
    		contacts.add(contact);
    	}
		return contacts;
    	
    }
 
	public interface GetJsonListener {
		public void onWebServiceCallComplete(List<Contact> contacts);
	}
	
}
