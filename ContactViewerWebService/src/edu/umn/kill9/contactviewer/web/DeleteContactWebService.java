package edu.umn.kill9.contactviewer.web;

import edu.umn.kill9.contactviewer.model.json.ContactJsonListener;
import edu.umn.kill9.contactviewer.model.json.ContactJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

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

            //TODO add details here
        }
        return null;
    }

    @Override
    protected void onPostExecute(ContactJsonResponse response) {
        jsonListener.onContactWebServiceCallComplete(response, this);
    }
}
