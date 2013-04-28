package edu.umn.kill9.places.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.MapSearchFragment;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                intent = new Intent(this, PlacesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
