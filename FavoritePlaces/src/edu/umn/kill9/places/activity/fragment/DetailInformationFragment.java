package edu.umn.kill9.places.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;

import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.application.PlacesApplication;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.model.PlaceDataSource;
import edu.umn.kill9.places.model.data.SampleCategoryList;
import edu.umn.kill9.places.util.PlacesConstants;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class DetailInformationFragment extends Fragment {

    List<Category> categories;
    List<Boolean> selected;
    private Place _location;

    private EditText name;
    private EditText address;
    private EditText phone;
    private EditText website;
    private EditText hours;
    private EditText comments;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View item = inflater.inflate(R.layout.detail_information, null);

        name = (EditText)item.findViewById(R.id.name_text);
        address = (EditText)item.findViewById(R.id.address_text);
        phone = (EditText)item.findViewById(R.id.phone_text);
        website = (EditText)item.findViewById(R.id.website_text);
        hours = (EditText)item.findViewById(R.id.hours_text);
        comments = (EditText)item.findViewById(R.id.comments_text);

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

        //set view only
        setWriteable(false);

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
            wrapper.show(new CategoryAdapter(getActivity(), R.layout.categorylist_item, categories, selected, ((PlacesApplication)getActivity().getApplication()).getUser()));
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
        switch (item.getItemId()) {
            case R.id.edit_location_details:
                //add edit location details logic - just make certain items editable
                setWriteable(true);
                return true;
            case R.id.save_location_details:
                //add save locaton details logic - make all items uneditable and save
                _location.setPlaceName(name.getText().toString());
                _location.setAddress(address.getText().toString());
                _location.setPhone(phone.getText().toString());
                _location.setWebsite(website.getText().toString());
                _location.setHours(hours.getText().toString());
                _location.setUserComments(comments.getText().toString());

                //make sure to set current user
                _location.setPlaceUser(((PlacesApplication)getActivity().getApplication()).getUser());

                PlaceDataSource ds = new PlaceDataSource();
                try {
                    ds.updatePlace(_location);
                    Toast.makeText(getActivity().getApplicationContext(), "Saved location: " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                //set view only
                setWriteable(false);

                return true;
            case R.id.delete_location:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                PlaceDataSource ds = new PlaceDataSource();
                                try {
                                    ds.deletePlace(_location);
                                    Toast.makeText(getActivity().getApplicationContext(), "Deleted location: " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            	
                            	getActivity().setResult(Activity.RESULT_OK, null);
                                getActivity().finish();

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do Nothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.confirm_delete) + name.getText().toString() + "?")
                	.setPositiveButton(getString(R.string.yes), dialogClickListener)
                    .setNegativeButton(getString(R.string.no), dialogClickListener)
                    .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param readOnly
     */
    private void setWriteable(boolean readOnly) {
        name.setEnabled(readOnly);
        address.setEnabled(readOnly);
        phone.setEnabled(readOnly);
        website.setEnabled(readOnly);
        hours.setEnabled(readOnly);
        comments.setEnabled(readOnly);
    }
}
