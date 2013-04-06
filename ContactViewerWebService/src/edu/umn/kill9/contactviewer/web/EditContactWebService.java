package edu.umn.kill9.contactviewer.web;

import java.util.regex.Pattern;

import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.application.ContactApplication;
import edu.umn.kill9.contactviewer.model.json.ContactJsonListener;
import edu.umn.kill9.contactviewer.model.json.ContactJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class EditContactWebService extends ContactWebService<ContactJsonResponse, Object> {

    ContactJsonListener jsonListener;

    public EditContactWebService(ContactJsonListener listenerActivity){
        this.jsonListener = listenerActivity;
    }

    @Override
    protected ContactJsonResponse doInBackground(Object... contacts) {
        ContactJsonResponse response = null;
        if (contacts.length > 0) {
            Contact c = (Contact)contacts[0];

            if (c != null) {
                String baseurl = ContactApplication.getContext().getResources().getString(R.string.API_URL);
                String key = ContactApplication.getContext().getResources().getString(R.string.API_KEY);
                String url = baseurl + "/" + c.getId() + "?key=" + key; // + "&name=" + encode(c.getName()) + "&title=" + encode(c.getTitle()) + "&email=" + encode(c.getEmail()) + "&phone=" + encode(c.getPhone()) + "&twitterId=" + encode(c.getTwitterId());

                HttpRequestBase request = new HttpPut(url);

                //add data to request
                try {
                    StringEntity se = new StringEntity(contactToJSON(c));
                    se.setContentEncoding("UTF-8");
                    se.setContentType("application/json");
                    ((HttpPut)request).setEntity(se);
                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    request.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression
                }
                catch (Exception e) {
                    Log.w("doInBackground", "error", e);
                }
                response = (ContactJsonResponse)getJsonObject(request, ContactJsonResponse.class);

            }
            else {
                response = getContactErrorResponse("Can't edit null contact!");
            }
        }
        else {
            response = getContactErrorResponse("No contacts to edit!");
        }

        return response;
    }

    @Override
    protected void onPostExecute(ContactJsonResponse response) {
        jsonListener.onContactWebServiceCallComplete(response, this);
    }
}
