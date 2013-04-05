package edu.umn.kill9.contactviewer.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import edu.umn.kill9.contactviewer.model.json.ContactJsonResponse;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

/**
 * User: drmaas
 * Date: 3/30/13
 *
 * Base class which all web service classes should extend.
 * T is the type of object we expect back from doInBackground, and is passed
 * to onPostExecute.
 */
public abstract class ContactWebService<T, R> extends AsyncTask<R, Void, T> {

    @Override
    protected T doInBackground(R... params) {
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        {}
    }

    /**
     * Get a contact list from json    /**
     * Generic method to get json data from the reqest object of type clazz
     *
     * @param request
     * @param clazz
     * @return
     */
    protected Object getJsonObject(HttpRequestBase request, Class clazz) {
        AndroidHttpClient client = null;
        try {
            client = AndroidHttpClient.newInstance("Android", null);
            HttpResponse response = client.execute(request);
            Gson gson = new Gson();
            return gson.fromJson(new InputStreamReader(response.getEntity().getContent()), clazz);
        }
        catch (IOException ex) {
            Log.w("getJsonObject", "Error getting web object", ex);
        }
        finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }


    /**
     * @param contactsListJson
     * @return
     */
    public ArrayList<Contact> getContactsListFromJsonList(List<ContactJson> contactsListJson) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        if (contactsListJson != null) {
            for(int i=0;i<contactsListJson.size();i++){
                ContactJson jsonContact = contactsListJson.get(i);
                Contact contact = new Contact(jsonContact.getName());
                contact.setEmail(jsonContact.getEmail());
                contact.setId(jsonContact.getId());
                contact.setPhone(jsonContact.getPhone());
                contact.setTitle(jsonContact.getTitle());
                contact.setTwitterId(jsonContact.getTwitterId());

                contacts.add(contact);
            }
        }
        return contacts;
    }

    /**
     * Get a contact from json
     *
     * @param json
     * @return
     */
    public Contact getContactFromJson(ContactJson json) {

        Contact contact = null;
        if (json != null) {
            contact = new Contact(json.getName());
            contact.setEmail(json.getEmail());
            contact.setId(json.getId());
            contact.setPhone(json.getPhone());
            contact.setTitle(json.getTitle());
            contact.setTwitterId(json.getTwitterId());
        }

        return contact;
    }

    /**
     *
     * @param url
     * @return
     */
    protected String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    /**
     *
     * @param message
     * @return
     */
    protected ContactJsonResponse getContactErrorResponse(String message) {
        ContactJsonResponse response = new ContactJsonResponse();
        response.setStatus("error");
        response.setMessage(message);

        return response;
    }

    /**
     *
     * @param message
     * @return
     */
    protected ContactListJsonResponse getContactListErrorResponse(String message) {
        ContactListJsonResponse response = new ContactListJsonResponse();
        response.setStatus("error");
        response.setMessage(message);

        return response;
    }
}
