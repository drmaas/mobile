package edu.umn.kill9.contactviewer.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.db.CVSQLiteOpenHelper;
import edu.umn.kill9.contactviewer.model.Contact;
import edu.umn.kill9.contactviewer.model.ContactDataSource;
import edu.umn.kill9.contactviewer.ui.ToolbarConfig;

/**
 * User: drmaas
 * Date: 2/17/13
 */
public class ContactActivity extends Activity {

    private CVSQLiteOpenHelper dbHelper;
    private SQLiteDatabase contactDB;
    private ContactDataSource datasource;
    private Contact c;
    private String action;

    private EditText name;
    private EditText title;
    private EditText email;
    private EditText phone;
    private EditText twitter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact);

        //set initial contact information for display
        name = (EditText)findViewById(R.id.name_text);
        title = (EditText)findViewById(R.id.title_text);
        email = (EditText)findViewById(R.id.email_text);
        phone = (EditText)findViewById(R.id.phone_text);
        twitter = (EditText)findViewById(R.id.twitter_text);

        //open database and get DAO
        dbHelper = new CVSQLiteOpenHelper(this);
        contactDB = dbHelper.getWritableDatabase();
        datasource = new ContactDataSource(contactDB);

        action = getIntent().getStringExtra("action");
        if (action.equals("edit")) {
            c = getIntent().getExtras().getParcelable("contact");

            //set initial contact information for display
            name.setText(c.getName());
            title.setText(c.getTitle());
            email.setText(c.getEmail());
            phone.setText(c.getPhone());
            twitter.setText(c.getTwitterId());
        }
        else {
            c = null;
        }

        //get toolbar
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.edit));

        //set left button
        final Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setText(getString(R.string.back));
        lbutton.setOnClickListener(new View.OnClickListener() {
            //go back, saving everything
            public void onClick(View v) {
                goBack();
            }
        });

        //set middle button
        final Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.save));
        mbutton.setOnClickListener(new View.OnClickListener() {
            //save everything but don't go back
            public void onClick(View v) {
                save();
            }
        });

        //set right button
        final Button rbutton = toolbar.getToolbarRightButton();
        rbutton.setText(getString(R.string.cancel));
        rbutton.setOnClickListener(new View.OnClickListener() {
            //go back without saving anything
            public void onClick(View v) {
                cancel();
            }
        });

        //start transaction
        dbHelper.startTransaction();

    }

    /**
     * Be nice and save changes before going back
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        goBack();
    }

    /**
     * go back and keep all saves, including most recent unsaved change
     */
    private void goBack() {
        Toast.makeText(ContactActivity.this, getString(R.string.done_contact_toast), Toast.LENGTH_SHORT).show();
        if (c != null) {
            datasource.editContact(c.getId(), name.getText().toString(), title.getText().toString(), email.getText().toString(),
                    phone.getText().toString(), twitter.getText().toString());
        }
        else {
            c = datasource.createContact(name.getText().toString(), title.getText().toString(), email.getText().toString(),
                    phone.getText().toString(), twitter.getText().toString());
        }

        dbHelper.commit();
        setResult(RESULT_OK, null);
        finish();
    }

    /**
     * Go back and undo all saves
     */
    private void cancel() {
        setResult(RESULT_CANCELED, null);
        dbHelper.rollback();
        finish();
    }

    /**
     * save and stay on screen
     */
    private void save() {
        Toast.makeText(ContactActivity.this, getString(R.string.save_contact_toast), Toast.LENGTH_SHORT).show();
        if (c != null) {
            datasource.editContact(c.getId(), name.getText().toString(), title.getText().toString(), email.getText().toString(),
                    phone.getText().toString(), twitter.getText().toString());
        }
        else {
            c = datasource.createContact(name.getText().toString(), title.getText().toString(), email.getText().toString(),
                    phone.getText().toString(), twitter.getText().toString());
        }
    }
}