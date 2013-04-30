package edu.umn.kill9.places.activity.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/28/13
 */
@Deprecated
public class CurrLocListFragment extends ListFragment {

    /**
     * TODO change to use map search result lists instead of location names
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Populate list with our static array of titles.
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, SampleLocationList.getLocationStrings()));
    }

    /**
     * TODO create location object and pass to details activity
     *
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlaceDetailsActivity.class);
        intent.putExtra("locationName", (String)getListAdapter().getItem(position));
        startActivityForResult(intent, PlacesConstants.DETAILS);
    }

}
