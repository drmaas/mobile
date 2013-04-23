package edu.umn.kill9.places.activity;

import android.os.Bundle;
import edu.umn.kill9.places.R;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class ExternalLocationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexternallocation);

        //show 'up' button next to home icon
        showHomeAsUp(true);
    }
}
