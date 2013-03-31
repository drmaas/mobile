package edu.umn.kill9.contactviewer.model.json;

import edu.umn.kill9.contactviewer.model.pojo.Contact;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public interface ContactJsonListener {

    public void onAddContactWebServiceCallComplete(Contact contact);

    public void onDeleteContactWebServiceCallComplete();

    public void onEditContactWebServiceCallComplete(Contact contact);
}
