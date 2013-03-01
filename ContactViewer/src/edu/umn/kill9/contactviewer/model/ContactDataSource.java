package edu.umn.kill9.contactviewer.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import edu.umn.kill9.contactviewer.db.CVSQLiteOpenHelper;

/**
 * User: drmaas
 * Date: 2/19/13
 */
public class ContactDataSource {

    // Database fields
    private SQLiteDatabase database;
    private String[] allColumns = {
            CVSQLiteOpenHelper.COLUMN_ID,
            CVSQLiteOpenHelper.COLUMN_NAME,
            CVSQLiteOpenHelper.COLUMN_TITLE,
            CVSQLiteOpenHelper.COLUMN_EMAIL,
            CVSQLiteOpenHelper.COLUMN_PHONE,
            CVSQLiteOpenHelper.COLUMN_TWITTER
    };

    /**
     *
     * @param database
     */
    public ContactDataSource(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Create a new contact
     *
     * @param name
     * @param title
     * @param email
     * @param phone
     * @param twitter
     * @return
     */
    public Contact createContact(String name, String title, String email, String phone, String twitter) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(CVSQLiteOpenHelper.COLUMN_NAME, name);
        values.put(CVSQLiteOpenHelper.COLUMN_TITLE, title);
        values.put(CVSQLiteOpenHelper.COLUMN_EMAIL, email);
        values.put(CVSQLiteOpenHelper.COLUMN_PHONE, phone);
        values.put(CVSQLiteOpenHelper.COLUMN_TWITTER, twitter);
        long insertId = database.insert(CVSQLiteOpenHelper.TABLE_CONTACT, null, values);
        Cursor cursor = database.query(CVSQLiteOpenHelper.TABLE_CONTACT, allColumns, CVSQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Contact contact = cursorToContact(cursor);
        cursor.close();
        System.out.println("Created contact with id: " + insertId);
        return contact;
    }

    /**
     * Edit an existing contact
     *
     * To leave a column unchanged, pass in null
     *
     * @param name
     * @param title
     * @param email
     * @param phone
     * @param twitter
     */
    public void editContact(Long id, String name, String title, String email, String phone, String twitter) throws SQLException {
        ContentValues values = new ContentValues();
        if (name != null) {
            values.put(CVSQLiteOpenHelper.COLUMN_NAME, name);
        }
        if (title != null) {
            values.put(CVSQLiteOpenHelper.COLUMN_TITLE, title);
        }
        if (email != null) {
            values.put(CVSQLiteOpenHelper.COLUMN_EMAIL, email);
        }
        if (phone != null) {
            values.put(CVSQLiteOpenHelper.COLUMN_PHONE, phone);
        }
        if (twitter != null) {
            values.put(CVSQLiteOpenHelper.COLUMN_TWITTER, twitter);
        }
        database.update(CVSQLiteOpenHelper.TABLE_CONTACT, values, CVSQLiteOpenHelper.COLUMN_ID+"=?", new String[] { String.valueOf(id) } );
        System.out.println("Updated contact with id: " + id);
    }

    /**
     * Delete a contact
     *
     * @param contact
     */
    public void deleteContact(Contact contact) throws SQLException {
        long id = contact.getId();
        database.delete(CVSQLiteOpenHelper.TABLE_CONTACT, CVSQLiteOpenHelper.COLUMN_ID + " = " + id, null);
        System.out.println("Contact deleted with id: " + id);
    }

    /**
     * Get all contacts in the database
     *
     * @return
     */
    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<Contact>();

        Cursor cursor = database.query(CVSQLiteOpenHelper.TABLE_CONTACT, allColumns, null, null, null, null, null);

        Contact contact;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return contacts;
    }

    /**
     * convert cursor row to contact
     *
     * @param cursor
     * @return
     */
    private Contact cursorToContact(Cursor cursor) {
        Long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String title = cursor.getString(2);
        String email = cursor.getString(3);
        String phone = cursor.getString(4);
        String twitter = cursor.getString(5);

        Contact contact = new Contact(name);
        contact.setId(id);
        contact.setTitle(title);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setTwitterId(twitter);

        return contact;
    }
}
