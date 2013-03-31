package edu.umn.kill9.contactviewer.web;

import android.os.AsyncTask;
import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.json.JsonListener;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class AddContactWebService extends ContactWebService<ContactJson> {

    JsonListener jsonListener;

    public AddContactWebService(JsonListener listenerActivity) {
        this.jsonListener = listenerActivity;
    }

    @Override
    protected ContactJson doInBackground(String... params) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(ContactJson result) {
        Contact contact = getContactFromJson(result);
        jsonListener.onAddContactWebServiceCallComplete(contact);
    }
}
