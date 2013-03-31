package edu.umn.kill9.contactviewer.model.json;

import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public interface JsonListener {

    public void onContactListWebServiceCallComplete(List<Contact> contacts);

    public void onAddContactWebServiceCallComplete(Contact contact);

    public void onDeleteContactWebServiceCallComplete();

    public void onEditContactWebServiceCallComplete(Contact contact);

}
