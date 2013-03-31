package edu.umn.kill9.contactviewer.web;

import android.os.AsyncTask;
import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 *
 * Base class which all web service classes should extend.
 * T is the type of object we expect back from doInBackground, and is passed
 * to onPostExecute.
 */
public abstract class ContactWebService<T> extends AsyncTask<String, Void, T> {

    @Override
    protected T doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(T result) {
        {}
    }

    /**
     * Get a contact list from json
     *
     * @param contactsListJson
     * @return
     */
    protected ArrayList<Contact> getContactsListFromJsonList(List<ContactJson> contactsListJson) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        if (contactsListJson != null) {
            for(int i=0;i<contactsListJson.size();i++){
                ContactJson jsonContact = contactsListJson.get(i);
                Contact contact = new Contact(jsonContact.getName());
                contact.setEmail(jsonContact.getEmail());
                contact.setId(Long.parseLong(jsonContact.getId()));
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
    protected Contact getContactFromJson(ContactJson json) {

        Contact contact = null;
        if (json != null) {
            contact = new Contact(json.getName());
            contact.setEmail(json.getEmail());
            contact.setId(Long.parseLong(json.getId()));
            contact.setPhone(json.getPhone());
            contact.setTitle(json.getTitle());
            contact.setTwitterId(json.getTwitterId());
        }

        return contact;
    }
}
