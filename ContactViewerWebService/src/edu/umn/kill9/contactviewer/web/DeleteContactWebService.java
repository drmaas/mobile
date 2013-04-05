package edu.umn.kill9.contactviewer.web;

import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.application.ContactApplication;
import edu.umn.kill9.contactviewer.model.json.ContactJsonListener;
import edu.umn.kill9.contactviewer.model.json.ContactJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class DeleteContactWebService extends ContactWebService<ContactJsonResponse, Object> {


    ContactJsonListener jsonListener;

    public DeleteContactWebService(ContactJsonListener listenerActivity){
        this.jsonListener = listenerActivity;
    }

    @Override
    protected ContactJsonResponse doInBackground(Object... contacts) {
        if (contacts.length > 0) {
            Contact c = (Contact)contacts[0];

            if (c != null) {
                String baseurl = ContactApplication.getContext().getResources().getString(R.string.API_URL);
                String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
                String url = baseurl + "/" + c.getId() + "?key=" + key;

                HttpRequestBase request = new HttpDelete(url);

                return (ContactJsonResponse)getJsonObject(request, ContactJsonResponse.class);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ContactJsonResponse response) {
        jsonListener.onContactWebServiceCallComplete(response, this);
    }
}
