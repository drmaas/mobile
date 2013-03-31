package edu.umn.kill9.contactviewer.model.dao;

import edu.umn.kill9.contactviewer.model.pojo.Contact;

import java.util.List;

/**
 * User: drmaas
 * Date: 3/30/13
 */
public interface ContactDataSource {

    public Contact createContact(String name, String title, String email, String phone, String twitter);

    public void editContact(Contact c, String name, String title, String email, String phone, String twitter);

    public void deleteContact(Contact contact);

    public List<Contact> getAllContacts();



}
