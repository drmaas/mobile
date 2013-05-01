package edu.umn.kill9.places.activity.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.Location;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.model.data.SampleLocationList;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailInformationFragment extends Fragment {

    List<Category> categories;
    List<Boolean> selected;
    private Location _location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //refresh categories drop-down
        categories = SampleCategoryList.getCategories();
        selected = new ArrayList();
        for (int i = 0; i < categories.size(); i++) {
            selected.add(Boolean.valueOf(false));
        }

        String locationName = getActivity().getIntent().getStringExtra("locationName");
        _location = SampleLocationList.findByLocationName(locationName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.detail_information, null);
        EditText name = (EditText)item.findViewById(R.id.name_text);
        name.setText(_location.getLocationName());

        return item;
    }

    /**
     * listener for category drop-down
     * @param v
     */
    public void onDropdownClick(View v) {
        CategoryListPopupWrapper wrapper = new CategoryListPopupWrapper(getActivity());
        if (wrapper.isShowing()) {
            wrapper.dismiss();
        }
        else {
            wrapper.show(new CategoryAdapter(getActivity(), R.layout.categorylist_item, categories, selected));
        }
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
