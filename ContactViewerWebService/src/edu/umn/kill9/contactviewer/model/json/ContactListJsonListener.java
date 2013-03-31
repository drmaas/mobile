package edu.umn.kill9.contactviewer.model.json;

import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public interface ContactListJsonListener {

    public void onContactListWebServiceCallComplete(List<Contact> contacts);

}
