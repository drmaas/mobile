package edu.umn.kill9.contactviewer.web;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.application.ContactApplication;
import edu.umn.kill9.contactviewer.model.json.ContactJsonListener;
import edu.umn.kill9.contactviewer.model.json.ContactJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.net.URLEncoder;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class AddContactWebService extends ContactWebService<ContactJsonResponse, Object> {

    ContactJsonListener jsonListener;

    public AddContactWebService(ContactJsonListener listenerActivity) {
        this.jsonListener = listenerActivity;
    }

    @Override
    protected ContactJsonResponse doInBackground(Object... contacts) {
        if (contacts.length > 0) {
            Contact c = (Contact)contacts[0];
            if (c != null) {
                String baseurl = ContactApplication.getContext().getResources().getString(R.string.API_URL);
                String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
                String url = baseurl + "?key=" + key + "&name=" + encode(c.getName()) + "&title=" + encode(c.getTitle()) + "&email=" + encode(c.getEmail()) + "&phone=" + encode(c.getPhone()) + "&twitterId=" + encode(c.getTwitterId());

                HttpRequestBase request = new HttpPost(url);

                return (ContactJsonResponse)getJsonObject(request, ContactJsonResponse.class);
            }
            else {
                return getContactErrorResponse("Can't add null contact!");
            }
        }
        else {
            return getContactErrorResponse("No contacts to add!");
        }
    }

    @Override
    protected void onPostExecute(ContactJsonResponse response) {
        jsonListener.onContactWebServiceCallComplete(response, this);
    }
}
