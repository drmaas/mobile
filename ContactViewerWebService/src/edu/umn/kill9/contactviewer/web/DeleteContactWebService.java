package edu.umn.kill9.contactviewer.web;

import edu.umn.kill9.contactviewer.model.json.ContactJsonListener;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class DeleteContactWebService extends ContactWebService<Void> {


    ContactJsonListener jsonListener;

    public DeleteContactWebService(ContactJsonListener listenerActivity){
        this.jsonListener = listenerActivity;
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPostExecute(Void result) {
        jsonListener.onDeleteContactWebServiceCallComplete();
    }
}
