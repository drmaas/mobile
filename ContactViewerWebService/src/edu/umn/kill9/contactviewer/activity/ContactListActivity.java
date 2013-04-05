package edu.umn.kill9.contactviewer.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.model.json.ContactJson;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonListener;
import edu.umn.kill9.contactviewer.model.json.ContactListJsonResponse;
import edu.umn.kill9.contactviewer.model.pojo.Contact;
import edu.umn.kill9.contactviewer.ui.ToolbarConfig;
import edu.umn.kill9.contactviewer.web.ContactListWebService;
import edu.umn.kill9.contactviewer.web.ContactWebService;

/**
 * Displays a list of contacts.
 *
 * TODO: move ContactListJsonListener implementation to ContactWebDataSource
 */

public class ContactListActivity extends ListActivity {

    public static final String ACTION = "action";
    public static final String EDIT = "edit";
    public static final String CREATE = "create";
    public static final String CONTACT = "contact";

    private boolean sortAscendingOrder = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.contacts));

	    // setup the about button
        Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setVisibility(View.GONE);

        Button flbutton = toolbar.getToolbarFarLeftButton();
        //HACK ALERT for layout beautification
        flbutton.setVisibility(View.INVISIBLE);
        flbutton.setText("");

        Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText("");
        mbutton.setBackgroundResource(android.R.drawable.ic_menu_add);
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
	    rbutton.setVisibility(View.INVISIBLE);

        //setup sorting icons
        final ImageView desc = toolbar.getDescIcon();
        final ImageView asc = toolbar.getAscIcon();
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortAscendingOrder = false;
                refreshContactsFromWebService();
                asc.setVisibility(View.VISIBLE);
                desc.setVisibility(View.GONE);
            }
        });
        asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortAscendingOrder = true;
                refreshContactsFromWebService();
                asc.setVisibility(View.GONE);
                desc.setVisibility(View.VISIBLE);
            }
        });
        asc.setVisibility(View.GONE);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        //refresh data from web service
        refreshContactsFromWebService();
        
        //filter
        EditText filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);

        // This will remove the keyboard from being displayed when this activity is created, but it will still have focus
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            refreshContactsFromWebService();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Get all contacts from the web
     */
    private void refreshContactsFromWebService() {
    	ContactListWebService contactwebservice = new ContactListWebService(new ContactListJsonListener() {

            @Override
            public void onContactListWebServiceCallComplete(ContactListJsonResponse response, ContactWebService service) {
                //TODO check the status of response

                // make some contacts
                List<ContactJson> contactsListJson = response.getContacts();
                List<Contact> contactsList = service.getContactsListFromJsonList(contactsListJson);
                ArrayList<Contact> contacts = new ArrayList<Contact>();
                contacts.addAll(contactsList);
                Collections.sort(contacts, new ContactComparator());

                //If sortIncreasing is false, reverse the sort to decreasing order.
                if(!sortAscendingOrder) {
                    Collections.reverse(contacts);
                }
                setListAdapter(new ContactAdapter(ContactListActivity.this, R.layout.list_item, contacts));
            }
        });
        contactwebservice.execute();
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

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((ContactAdapter)getListAdapter()).getFilter().filter(s);
        }

    };
    
}


