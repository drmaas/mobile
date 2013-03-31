package edu.umn.kill9.contactviewer.web;

import android.os.AsyncTask;
import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.json.JsonListener;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class DeleteContactWebService extends ContactWebService<Void> {


    JsonListener jsonListener;

    public DeleteContactWebService(JsonListener listenerActivity){
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
