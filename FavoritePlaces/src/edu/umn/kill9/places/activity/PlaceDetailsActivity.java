package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.DetailInformationFragment;
import edu.umn.kill9.places.activity.fragment.DetailMapFragment;
import edu.umn.kill9.places.activity.fragment.EventsFragment;
import edu.umn.kill9.places.tab.TabListener;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class PlaceDetailsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showHomeAsUp(true);

        setTitle(getIntent().getStringExtra("locationName") + " Details");

        // setup action bar for tabs
        //we will have 3 tabs - information (including call action), map (including navigation action), and events
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tab_details)
                .setTabListener(new TabListener<DetailInformationFragment>(
                        this, "details", DetailInformationFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_map)
                .setTabListener(new TabListener<DetailMapFragment>(
                        this, "map", DetailMapFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.tab_events)
                .setTabListener(new TabListener<EventsFragment>(
                        this, "events", EventsFragment.class));
        actionBar.addTab(tab);
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