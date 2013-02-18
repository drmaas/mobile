package edu.umn.contactviewer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * User: drmaas
 * Date: 2/17/13
 */
public class EditContactActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact);
        Contact c = getIntent().getExtras().getParcelable("contact");

        EditText name = (EditText)findViewById(R.id.name_label);
        name.setText(c.getName());

        EditText title = (EditText)findViewById(R.id.title_label);
        title.setText(c.getTitle());

        EditText email = (EditText)findViewById(R.id.email_label);
        email.setText(c.getEmail());

        EditText phone = (EditText)findViewById(R.id.phone_label);
        phone.setText(c.getPhone());

        EditText twitter = (EditText)findViewById(R.id.twitter_label);
        twitter.setText(c.getTwitterId());

    }
}