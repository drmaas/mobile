package edu.umn.contactviewer.activiy;

import java.util.ArrayList;
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
import edu.umn.contactviewer.R;
import edu.umn.contactviewer.ui.ToolbarConfig;
import edu.umn.contactviewer.db.CVSQLiteOpenHelper;
import edu.umn.contactviewer.model.Contact;
import edu.umn.contactviewer.model.ContactDataSource;

/** Displays a list of contacts.
 *
 */
public class ContactListActivity extends ListActivity {

    private CVSQLiteOpenHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.contacts));

	    // setup the about button
        Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setVisibility(View.INVISIBLE);

        Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setVisibility(View.INVISIBLE);

	    Button rbutton = toolbar.getToolbarRightButton();
	    rbutton.setText(getString(R.string.about));
		rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactListActivity.this, getString(R.string.info), Toast.LENGTH_LONG).show();
            }
        });

        //open (and possibly initialize) database
        dbHelper = new CVSQLiteOpenHelper(this);
        SQLiteDatabase contactDB = dbHelper.getWritableDatabase();
        ContactDataSource datasource = new ContactDataSource(contactDB);

        // make some contacts
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        /*
        contacts.add(new Contact("Malcom Reynolds")
    		.setEmail("mal@serenity.com")
    		.setTitle("Captain")
    		.setPhone("612-555-1234")
    		.setTwitterId("malcomreynolds"));
        contacts.add(new Contact("Zoe Washburne")
			.setEmail("zoe@serenity.com")
			.setTitle("First Mate")
			.setPhone("612-555-5678")
			.setTwitterId("zoewashburne"));
        contacts.add(new Contact("Hoban Washburne")
			.setEmail("wash@serenity.com")
			.setTitle("Pilot")
			.setPhone("612-555-9012")
			.setTwitterId("wash"));
        contacts.add(new Contact("Jayne Cobb")
			.setEmail("jayne@serenity.com")
			.setTitle("Muscle")
			.setPhone("612-555-3456")
			.setTwitterId("heroofcanton"));
        contacts.add(new Contact("Kaylee Frye")
			.setEmail("kaylee@serenity.com")
			.setTitle("Engineer")
			.setPhone("612-555-7890")
			.setTwitterId("kaylee"));
        contacts.add(new Contact("Simon Tam")
			.setEmail("simon@serenity.com")
			.setTitle("Doctor")
			.setPhone("612-555-4321")
			.setTwitterId("simontam"));
        contacts.add(new Contact("River Tam")
			.setEmail("river@serenity.com")
			.setTitle("Doctor's Sister")
			.setPhone("612-555-8765")
			.setTwitterId("miranda"));
        contacts.add(new Contact("Shepherd Book")
			.setEmail("shepherd@serenity.com")
			.setTitle("Shepherd")
			.setPhone("612-555-2109")
			.setTwitterId("shepherdbook"));
        */

        contacts.addAll(datasource.getAllContacts());

        // initialize the list view
        setListAdapter(new ContactAdapter(this, R.layout.list_item, contacts));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //super
        super.onListItemClick(l, v, position, id);

        //get selected items
        Contact contact = (Contact)getListAdapter().getItem(position);
        Toast.makeText(this, getString(R.string.contact_toast) + " " + contact.getName(), Toast.LENGTH_SHORT).show();

        //make new intent from this list activity to edit activity
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        dbHelper.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dbHelper.close();
        super.onPause();
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


