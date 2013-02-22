package edu.umn.contactviewer.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.umn.contactviewer.R;
import edu.umn.contactviewer.db.CVSQLiteOpenHelper;
import edu.umn.contactviewer.model.ContactDataSource;
import edu.umn.contactviewer.ui.ToolbarConfig;
import edu.umn.contactviewer.model.Contact;

/**
 * User: drmaas
 * Date: 2/17/13
 */
public class EditContactActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact);

        //open database and get DAO
        CVSQLiteOpenHelper dbHelper = new CVSQLiteOpenHelper(this);
        SQLiteDatabase contactDB = dbHelper.getWritableDatabase();
        final ContactDataSource datasource = new ContactDataSource(contactDB);

        Contact c = getIntent().getExtras().getParcelable("contact");
        final Long id = c.getId();

        //set initial contact information for display
        final EditText name = (EditText)findViewById(R.id.name_text);
        name.setText(c.getName());

        final EditText title = (EditText)findViewById(R.id.title_text);
        title.setText(c.getTitle());

        final EditText email = (EditText)findViewById(R.id.email_text);
        email.setText(c.getEmail());

        final EditText phone = (EditText)findViewById(R.id.phone_text);
        phone.setText(c.getPhone());

        final EditText twitter = (EditText)findViewById(R.id.twitter_text);
        twitter.setText(c.getTwitterId());

        //get toolbar
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.edit));

        //set left button
        final Button lbutton = toolbar.getToolbarLeftButton();
        final Intent back = new Intent(this, ContactListActivity.class);
        lbutton.setText(getString(R.string.back));
        lbutton.setOnClickListener(new View.OnClickListener() {
            //go back, saving everything
            public void onClick(View v) {
                Toast.makeText(EditContactActivity.this, getString(R.string.done_contact_toast), Toast.LENGTH_SHORT).show();
                datasource.editContact(id, name.getText().toString(), title.getText().toString(), email.getText().toString(),
                                       phone.getText().toString(), twitter.getText().toString());
                startActivity(back);
            }
        });

        //set middle button
        final Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.save));
        mbutton.setOnClickListener(new View.OnClickListener() {
            //save everything but don't go back
            public void onClick(View v) {
                Toast.makeText(EditContactActivity.this, getString(R.string.save_contact_toast), Toast.LENGTH_SHORT).show();
                //datasource.editContact(id, c.getName(), c.getTitle(), c.getEmail(), c.getPhone(), c.getTwitterId());
                datasource.editContact(id, name.getText().toString(), title.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), twitter.getText().toString());
            }
        });

        //set right button
        final Button rbutton = toolbar.getToolbarRightButton();
        final Intent cancel = new Intent(this, ContactListActivity.class);
        rbutton.setText(getString(R.string.cancel));
        rbutton.setOnClickListener(new View.OnClickListener() {
            //go back without saving anything
            public void onClick(View v) {
                startActivity(back);
            }
        });

    }
}