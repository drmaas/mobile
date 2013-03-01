package edu.umn.kill9.contactviewer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import edu.umn.kill9.contactviewer.R;
import edu.umn.kill9.contactviewer.db.CVSQLiteOpenHelper;
import edu.umn.kill9.contactviewer.model.Contact;
import edu.umn.kill9.contactviewer.model.ContactDataSource;
import edu.umn.kill9.contactviewer.ui.ToolbarConfig;
import edu.umn.kill9.contactviewer.util.ContactUtils;

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

    private ImageView action_call;
    private ImageView action_text;
    private ImageView action_email;

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
        action_call = (ImageView)findViewById(R.id.phone_call_icon);
        action_text = (ImageView)findViewById(R.id.phone_text_icon);
        action_email = (ImageView)findViewById(R.id.phone_email_icon);

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

        //hide sorting icons
        final ImageView desc = toolbar.getDescIcon();
        desc.setVisibility(View.GONE);
        final ImageView asc = toolbar.getAscIcon();
        asc.setVisibility(View.GONE);

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
                if (validateContact(name.getText().toString(), email.getText().toString(), phone.getText().toString())) {
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
                if (validateContact(name.getText().toString(), email.getText().toString(), phone.getText().toString())) {
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

        action_call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				contact_call();
			}
		});

        action_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				contact_text();
			}
		});

        action_email.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				contact_email();
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

    	save();
    	
    	Toast.makeText(ContactActivity.this, getString(R.string.done_contact_toast), Toast.LENGTH_SHORT).show();

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

        String nameString = name.getText().toString();
        String titleString = title.getText().toString();
        String emailString = email.getText().toString();
        String phoneString = phone.getText().toString();
        String twitterString = twitter.getText().toString();
        
        if (c != null) {
            Long id = c.getId();
            datasource.editContact(id, nameString, titleString, emailString, phoneString, twitterString);
        }
        else {
        	c = datasource.createContact(nameString, titleString, emailString, phoneString, twitterString);
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
     * validate that name
     *
     * @param name_value
     * @return
     */
    private boolean validateName(String name_value) {
        return !ContactUtils.empty(name_value);
    }

    /**
     * validate the email
     *
     * @param name_value
     * @return
     */
    private boolean validateEmail(String email_value) {
    	boolean result;
    	
    	if (email_value == null)
    	{
    		result = false;
    	}
    	else
    	{
    		if ( email_value.equals("") )
    			result = true;
    		else
    			result = android.util.Patterns.EMAIL_ADDRESS.matcher(email_value).matches();
    	}
    	
    	return result;
    }

    /**
     * validate the phone number
     *
     * @param name_value
     * @return
     */
    private boolean validatePhone(String phone_value) {
    	boolean result;
    	
    	if (phone_value == null)
    	{
    		result = false;
    	}
    	else
    	{
    		if ( phone_value.equals("") )
    			result = true;
    		else
    			result = android.util.Patterns.PHONE.matcher(phone_value).matches();
    	}
    	
    	return result;
    }
    
    private void contact_call() {
    	if (c != null)
    	{
    		String contact_text = c.getPhone();
        	try {
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + contact_text)));
        	} catch (android.content.ActivityNotFoundException ex) {
        	    Toast.makeText(ContactActivity.this, getString(R.string.no_dialer_msg), Toast.LENGTH_SHORT).show();
        	}
    	}
	}
    
    private void contact_text() {
    	if (c != null)
    	{
    		String contact_text = c.getPhone();
        	try {
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + contact_text)));
        	} catch (android.content.ActivityNotFoundException ex) {
        	    Toast.makeText(ContactActivity.this, getString(R.string.no_sms_msg), Toast.LENGTH_SHORT).show();
        	}
    	}
	}
    
    private void contact_email() {
    	if (c != null)
    	{
    		String contact_email = c.getEmail();
        	try {
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contact_email)));
        	} catch (android.content.ActivityNotFoundException ex) {
        	    Toast.makeText(ContactActivity.this, getString(R.string.no_email_msg), Toast.LENGTH_SHORT).show();
        	}
    	}
	}
    
    private boolean validateContact (String name, String email, String phone) {

    	boolean name_valid = validateName(name);
    	boolean email_valid = validateEmail(email);
    	boolean phone_valid = validatePhone(phone);
    	
        boolean contact_valid = true;
        
        String messageText = "";
        
        if(!name_valid)
        {
        	contact_valid = false;
            messageText += getString(R.string.name_not_empty) + "\n";        	
        }
        
        if(!email_valid)
        {
        	contact_valid = false;
            messageText += getString(R.string.email_address_invalid) + "\n";        	
        }
        
        if(!phone_valid)
        {
        	contact_valid = false;
            messageText += getString(R.string.phone_num_invalid) + "\n";        	
        }
        
        if (!contact_valid) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error)).setMessage(messageText).setPositiveButton(getString(R.string.ok), dialogClickListener).show();
        }
    	
    	return contact_valid;
    }
    
}