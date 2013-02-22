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
public class EditContactActivity extends Activity {

    private CVSQLiteOpenHelper dbHelper;
    private SQLiteDatabase contactDB;
    private ContactDataSource datasource;
    private Contact c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact);

        //open database and get DAO
        dbHelper = new CVSQLiteOpenHelper(this);
        contactDB = dbHelper.getWritableDatabase();
        datasource = new ContactDataSource(contactDB);
        c = getIntent().getExtras().getParcelable("contact");

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
        //final Intent back = new Intent(this, ContactListActivity.class);
        lbutton.setText(getString(R.string.back));
        lbutton.setOnClickListener(new View.OnClickListener() {
            //go back, saving everything
            public void onClick(View v) {
                Toast.makeText(EditContactActivity.this, getString(R.string.done_contact_toast), Toast.LENGTH_SHORT).show();
                datasource.editContact(c.getId(), name.getText().toString(), title.getText().toString(), email.getText().toString(),
                                       phone.getText().toString(), twitter.getText().toString());
                setResult(RESULT_OK, null);
                finish();
            }
        });

        //set middle button
        final Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.save));
        mbutton.setOnClickListener(new View.OnClickListener() {
            //save everything but don't go back
            public void onClick(View v) {
                Toast.makeText(EditContactActivity.this, getString(R.string.save_contact_toast), Toast.LENGTH_SHORT).show();
                datasource.editContact(c.getId(), name.getText().toString(), title.getText().toString(), email.getText().toString(),
                        phone.getText().toString(), twitter.getText().toString());
            }
        });

        //set right button
        final Button rbutton = toolbar.getToolbarRightButton();
        rbutton.setText(getString(R.string.cancel));
        rbutton.setOnClickListener(new View.OnClickListener() {
            //go back without saving anything
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //datasource.editContact(c.getId(), name.getText().toString(), title.getText().toString(), email.getText().toString(),
        //        phone.getText().toString(), twitter.getText().toString());
        return;
    }

}