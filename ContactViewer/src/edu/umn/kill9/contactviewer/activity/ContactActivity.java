package edu.umn.kill9.contactviewer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private boolean edit;

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

        edit = getIntent().getStringExtra("action").equals("edit") ? true : false;
        ToolbarConfig toolbar = null;
        if (edit) {
            c = getIntent().getExtras().getParcelable("contact");

            //set initial contact information for display
            name.setText(c.getName());
            title.setText(c.getTitle());
            email.setText(c.getEmail());
            phone.setText(c.getPhone());
            twitter.setText(c.getTwitterId());

            //get toolbar
            toolbar = new ToolbarConfig(this, getString(R.string.edit));
        }
        else {
            c = null;

            //get toolbar
            toolbar = new ToolbarConfig(this, getString(R.string.new_contact));
        }

        //set far left button
        final Button flbutton = toolbar.getToolbarFarLeftButton();
        if (edit) {
            flbutton.setText(getString(R.string.delete));
            flbutton.setOnClickListener(new View.OnClickListener() {
                //go back, saving everything
                public void onClick(View v) {
                    delete();
                }
            });
        }
        else {
            flbutton.setVisibility(View.GONE);
        }

        //set left button
        final Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setText(getString(R.string.back));
        lbutton.setOnClickListener(new View.OnClickListener() {
            //go back, saving everything
            public void onClick(View v) {
                if (validateName(name.getText().toString())) {
                    goBack();
                }
            }
        });

        //set middle button
        final Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.save));
        mbutton.setOnClickListener(new View.OnClickListener() {
            //save everything but don't go back
            public void onClick(View v) {
                if (validateName(name.getText().toString())) {
                    save();
                }
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


    /**
     * delete and go back
     */
    private void delete() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        setResult(RESULT_OK, null);
                        datasource.deleteContact(c);
                        dbHelper.commit();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do Nothing
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirm_delete)).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }

    /**
     * validate that name can't be empty
     *
     * @param name_value
     * @return
     */
    private boolean validateName(String name_value) {
        if (name_value == null || name_value == "" || name_value.length() < 1) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error)).setMessage(getString(R.string.name_not_empty)).setPositiveButton(getString(R.string.ok), dialogClickListener).show();
            return false;
        }
        else {
            return true;
        }
    }
}