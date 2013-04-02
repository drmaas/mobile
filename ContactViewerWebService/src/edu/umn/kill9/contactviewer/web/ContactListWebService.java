package edu.umn.kill9.contactviewer.web;

import java.util.List;

import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.application.ContactApplication;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;
import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonListener;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

//import android.R;


public class ContactListWebService extends ContactWebService<ContactListJsonResponse, Void> {

	ContactListJsonListener jsonListener;
	
	public ContactListWebService(ContactListJsonListener listenerActivity){
		this.jsonListener = listenerActivity;	
	}
	
	@Override
	protected ContactListJsonResponse doInBackground(Void... params) {

        String baseurl = ContactApplication.getContext().getResources().getString(R.string.API_URL);
        String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
        String url = baseurl + "?key=" + key;

        HttpRequestBase request = new HttpGet(url);

        return (ContactListJsonResponse)getJsonObject(request, ContactListJsonResponse.class);

	}

	@Override
	protected void onPostExecute(ContactListJsonResponse jsonResponse) {
	    jsonListener.onContactListWebServiceCallComplete(jsonResponse, this);
    }
	
}
