package edu.umn.kill9.contactviewer.model.json;

import edu.umn.kill9.contactviewer.web.ContactWebService;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public interface ContactJsonListener {

    public void onContactWebServiceCallComplete(ContactJsonResponse response, ContactWebService service);
}
