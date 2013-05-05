package edu.umn.kill9.places.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import com.parse.ParseException;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.model.PlaceDataSource;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.model.data.SampleLocationList;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailInformationFragment extends Fragment {

    List<Category> categories;
    List<Boolean> selected;
    private Place _location;

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
        
        Intent actIntent = getActivity().getIntent();

        //String locationName = actIntent.getStringExtra("locationName");
        //_location = SampleLocationList.findByLocationName(locationName);
        _location = actIntent.getParcelableExtra(PlacesConstants.LOCATION_KEY);
        
        if ( _location == null )
        {
        	// The location doesn't exist in the database, this is a new location (maybe set some other flag here)
        	
        	// Get the point
            double latitude = actIntent.getDoubleExtra("latitude", 0);
            double longitude = actIntent.getDoubleExtra("longitude", 0);
        	_location = new Place("My Location", new LatLng(latitude, longitude));
        	
        	// Get other info
        	//String address = actIntent.getStringExtra("address");
        	//String hours = actIntent.getStringExtra("hours");
        	//String phone = actIntent.getStringExtra("phone");
        	//String vicinity = actIntent.getStringExtra("vicinity");
        	//String website = actIntent.getStringExtra("website");
        	
        	//_location.setAddress(vicinity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.detail_information, null);

        EditText name = (EditText)item.findViewById(R.id.name_text);
        EditText address = (EditText)item.findViewById(R.id.address_text);
        EditText phone = (EditText)item.findViewById(R.id.phone_text);
        EditText website = (EditText)item.findViewById(R.id.website_text);
        EditText hours = (EditText)item.findViewById(R.id.hours_text);
        EditText comments = (EditText)item.findViewById(R.id.comments_text);

        name.setText(_location.getPlaceName());
        if ( _location.getAddress() != null ) {
        	address.setText(_location.getAddress());
        }
        if (_location.getPhone() != null) {
            phone.setText(_location.getPhone());
        }
        if (_location.getWebsite() != null) {
            website.setText(_location.getWebsite());
        }
        if (_location.getHours() != null) {
            hours.setText(_location.getHours());
        }
        if (_location.getUserComments() != null) {
            comments.setText(_location.getUserComments());
        }

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

                //make sure to set current user
                _location.setPlaceUser(((PlacesApplication)getActivity().getApplication()).getUser());

                PlaceDataSource ds = new PlaceDataSource();
                try {
                    ds.updatePlace(_location);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.delete_location:
                //add delete location logic
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
