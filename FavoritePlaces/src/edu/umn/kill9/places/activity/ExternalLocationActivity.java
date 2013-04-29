package edu.umn.kill9.places.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.MapFragment;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.MapSearchListFragment;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class ExternalLocationActivity extends BaseActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexternallocation);

        //show 'up' button next to home icon
        showHomeAsUp(true);

        //setup listener on search button pressed on keyboard
        EditText editText = (EditText)findViewById(R.id.mapsearchtext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(v.getText().toString());
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

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

    /**
     * TODO perform google places search and show the results in the map and list fragments
     *
     * @param searchText
     */
    private void performSearch(String searchText) {
        FragmentManager fm = getFragmentManager();
        MapFragment mf = (MapFragment)fm.findFragmentById(R.id.mapsearchmapfragment);
        MapSearchListFragment msf = (MapSearchListFragment)fm.findFragmentById(R.id.mapsearchlistfragment);
    }
}
