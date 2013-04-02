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


public class ContactListWebService extends ContactWebService<List<ContactJson>> {

	ContactListJsonListener jsonListener;
	
	public ContactListWebService(ContactListJsonListener listenerActivity){
		this.jsonListener = listenerActivity;	
	}
	
	@Override
	protected List<ContactJson> doInBackground(String... params) {

        String baseurl = ContactApplication.getContext().getResources().getString(R.string.API_URL);
        String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
        String url = baseurl + "?key=" + key;

        List<ContactJson> contactsListJson = null;
        HttpRequestBase request = new HttpGet(url);
        ContactListJsonResponse jsonResponse = (ContactListJsonResponse)getJsonObject(request, ContactListJsonResponse.class);
        if (jsonResponse != null) {
            contactsListJson = jsonResponse.getContacts();
        }

        // This result is passed to the onPostExecute method
        return contactsListJson;
	}
	
	
	@Override
	protected void onPostExecute(List<ContactJson> result) {
		List<Contact> contactsList = getContactsListFromJsonList(result);
		jsonListener.onContactListWebServiceCallComplete(contactsList);
	}
	
}
