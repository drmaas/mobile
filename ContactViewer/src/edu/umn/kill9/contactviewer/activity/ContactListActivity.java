package edu.umn.kill9.contactviewer.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.db.CVSQLiteOpenHelper;
import edu.umn.kill9.contactviewer.model.Contact;
import edu.umn.kill9.contactviewer.model.ContactDataSource;
import edu.umn.kill9.contactviewer.ui.ToolbarConfig;

/**
 * Displays a list of contacts.
 */

public class ContactListActivity extends ListActivity {

    private CVSQLiteOpenHelper dbHelper;
    private SQLiteDatabase contactDB;

    public static final String ACTION = "action";
    public static final String EDIT = "edit";
    public static final String CREATE = "create";
    public static final String CONTACT = "contact";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.contacts));

	    // setup the about button
        Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setVisibility(View.GONE);

        Button flbutton = toolbar.getToolbarFarLeftButton();
        flbutton.setVisibility(View.GONE);

        Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.new_contact));
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactListActivity.this, getString(R.string.contact_create), Toast.LENGTH_SHORT).show();

                //make new intent from this list activity to edit activity
                Intent intent = new Intent(ContactListActivity.this, ContactActivity.class);
                intent.putExtra(ACTION, CREATE);
                startActivityForResult(intent, 1);
            }
        });

	    Button rbutton = toolbar.getToolbarRightButton();
	    rbutton.setText(getString(R.string.about));
		rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactListActivity.this, getString(R.string.info), Toast.LENGTH_LONG).show();
            }
        });

        //setup sorting icons
        final ImageView desc = toolbar.getDescIcon();
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshContacts(false);
            }
        });
        final ImageView asc = toolbar.getAscIcon();
        asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshContacts(true);
            }
        });

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        //open (and possibly initialize) database
        dbHelper = new CVSQLiteOpenHelper(this);
        contactDB = dbHelper.getWritableDatabase();

        //refresh data
        refreshContacts(true);

        //set to last contact
        //lv.setSelection(getListAdapter().getCount() - 1);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //super
        super.onListItemClick(l, v, position, id);

        //get selected items
        Contact contact = (Contact)getListAdapter().getItem(position);
        Toast.makeText(this, getString(R.string.contact_toast) + " " + contact.getName(), Toast.LENGTH_SHORT).show();

        //make new intent from this list activity to edit activity
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(ACTION, EDIT);
        intent.putExtra(CONTACT, contact);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //re-open database and refresh data
        if(resultCode == RESULT_OK) {
            contactDB = dbHelper.getWritableDatabase();
            refreshContacts(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //re-open database
        contactDB = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //close the db
        dbHelper.close();
    }

    /**
     * helper method to refresh contact list
     * assumes dbHelper already created and open
     */
    private void refreshContacts(boolean sortAscendingOrder) {
        //get db object
        ContactDataSource datasource = new ContactDataSource(contactDB);

        // make some contacts
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        //get contacts from the sqlite database
        contacts.addAll(datasource.getAllContacts());

        Collections.sort(contacts, new ContactComparator());

        //If sortIncreasing is false, reverse the sort to decreasing order.
        if(!sortAscendingOrder)
        {
            Collections.reverse(contacts);
        }

        // initialize the list view
        setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
    }

    public class ContactComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact contact1, Contact contact2) {
            return contact1.getName().toLowerCase().compareTo(contact2.getName().toLowerCase());
        }
    }

	/* We need to provide a custom adapter in order to use a custom list item view.
	 */
	public class ContactAdapter extends ArrayAdapter<Contact> {
	
		public ContactAdapter(Context context, int textViewResourceId, List<Contact> objects) {
			super(context, textViewResourceId, objects);
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View item = inflater.inflate(R.layout.list_item, parent, false);
			
			Contact contact = getItem(position);
			((TextView)item.findViewById(R.id.item_name)).setText(contact.getName());
			((TextView)item.findViewById(R.id.item_title)).setText(contact.getTitle());
			((TextView)item.findViewById(R.id.item_phone)).setText(contact.getPhone());
			
			return item;
		}
	}
    
}


