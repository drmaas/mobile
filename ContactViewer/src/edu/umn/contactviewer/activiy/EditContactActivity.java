package edu.umn.contactviewer.activiy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import edu.umn.contactviewer.R;
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

        //get toolbar
        ToolbarConfig toolbar = new ToolbarConfig(this, getString(R.string.edit));

        //set left button
        Button lbutton = toolbar.getToolbarLeftButton();
        lbutton.setText(getString(R.string.back));

        //set middle button
        Button mbutton = toolbar.getToolbarMiddleButton();
        mbutton.setText(getString(R.string.save));

        //set right button
        Button rbutton = toolbar.getToolbarRightButton();
        rbutton.setText(getString(R.string.cancel));

        Contact c = getIntent().getExtras().getParcelable("contact");

        EditText name = (EditText)findViewById(R.id.name_text);
        name.setText(c.getName());

        EditText title = (EditText)findViewById(R.id.title_text);
        title.setText(c.getTitle());

        EditText email = (EditText)findViewById(R.id.email_text);
        email.setText(c.getEmail());

        EditText phone = (EditText)findViewById(R.id.phone_text);
        phone.setText(c.getPhone());

        EditText twitter = (EditText)findViewById(R.id.twitter_text);
        twitter.setText(c.getTwitterId());

    }
}