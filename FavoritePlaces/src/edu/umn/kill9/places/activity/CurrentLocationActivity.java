package edu.umn.kill9.places.activity;

<<<<<<< HEAD
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
=======
import android.app.FragmentManager;
>>>>>>> 7d2b18f7fd7cd47308635ac0c14c754820459954
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.gms.maps.MapFragment;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.fragment.CurrLocListFragment;
import edu.umn.kill9.places.activity.fragment.MapSearchListFragment;
<<<<<<< HEAD
import edu.umn.kill9.places.activity.fragment.AddCurrentLocFragment;;
=======
>>>>>>> 7d2b18f7fd7cd47308635ac0c14c754820459954

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class CurrentLocationActivity extends BaseActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.addcurrentlocation);
        setContentView(R.layout.placelist_main);
       
        //show 'up' button next to home icon
        showHomeAsUp(true);
<<<<<<< HEAD
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment content = new AddCurrentLocFragment();

        ft.replace(R.id.contentview, content);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
              
        //content.getPlaces();

        
        //showCurrentLocations();
=======

        showCurrentLocations();
>>>>>>> 7d2b18f7fd7cd47308635ac0c14c754820459954
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
     */
    private void showCurrentLocations() {
        FragmentManager fm = getFragmentManager();
        MapFragment mf = (MapFragment)fm.findFragmentById(R.id.currlocmapfragment);
        CurrLocListFragment msf = (CurrLocListFragment)fm.findFragmentById(R.id.currloclistfragment);
    }
}