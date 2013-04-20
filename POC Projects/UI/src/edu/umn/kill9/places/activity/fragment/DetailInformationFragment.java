package edu.umn.kill9.places.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import edu.umn.kill9.places.R;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailInformationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView text = new TextView(getActivity());
        text.setText("Information");

        return text;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_info_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.edit_location_details:
                //add edit location details logic - just make certain items editable
                return true;
            case R.id.save_location_details:
                //add save locaton details logic - make all items uneditable and save
                return true;
            case R.id.delete_location:
                //add delete location logic
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
