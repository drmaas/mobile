package edu.umn.kill9.contactviewer.model.dao;

import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public class ContactWebDataSource implements ContactDataSource {

    @Override
    public Contact createContact(String name, String title, String email, String phone, String twitter) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void editContact(Contact c, String name, String title, String email, String phone, String twitter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteContact(Contact contact) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Contact> getAllContacts() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
