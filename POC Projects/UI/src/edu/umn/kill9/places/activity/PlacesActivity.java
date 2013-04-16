package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.adapter.NavigationAdapter;
import edu.umn.kill9.places.data.SampleCategoryList;
import edu.umn.kill9.places.dialog.AddCategoryDialogFragment;
import edu.umn.kill9.places.dialog.CategoryListPopupWrapper;
import edu.umn.kill9.places.model.Category;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends BaseActivity {

    private static final String LISTVIEW = "List View";
    private static final String MAPVIEW = "Map View";

    private String currentView;

    private List<Category> categories;
    private List<Boolean> selected;
    //private PopupWindow pw;

    boolean selectAllState = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_list);
        currentView = LISTVIEW;

        //don't show 'up' button next to home icon
        showHomeAsUp(false);

        //enable navigation mode in action bar
        enableNavigationMode();

        //setup navigation menu to switch between list and map view
        final SpinnerAdapter viewSpinnerAdapter = new NavigationAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.locations_navigation_view), currentView);
        getActionBar().setListNavigationCallbacks(viewSpinnerAdapter, new ActionBar.OnNavigationListener() {
            // Get the same strings provided for the drop-down's ArrayAdapter
            String[] strings = getResources().getStringArray(R.array.locations_navigation_view);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                String item = strings[position];
                if (item.equals(LISTVIEW)) {
                    //show list view
                    setContentView(R.layout.locations_list);
                    ((NavigationAdapter)viewSpinnerAdapter).setCurrentView(LISTVIEW);
                }
                else if (item.equals(MAPVIEW)) {
                    //show map view
                    setContentView(R.layout.locations_map);
                    ((NavigationAdapter)viewSpinnerAdapter).setCurrentView(MAPVIEW);
                }
                return true;
            }
        });

        //refresh categories drop-down
        categories = SampleCategoryList.getCategories();
        selected = new ArrayList();
        for (int i = 0; i < categories.size(); i++) {
            selected.add(new Boolean(false));
        }
    }

    /**
     * listener for category drop-down
     * @param v
     */
    public void onDropdownClick(View v) {
        CategoryListPopupWrapper wrapper = new CategoryListPopupWrapper(this);
        if (wrapper.isShowing()) {
            wrapper.dismiss();
        }
        else {
            wrapper.show(new CategoryAdapter(PlacesActivity.this, R.layout.categorylist_item, categories, selected));
        }
    }

    /**
     * Show menu items
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    /**
     * Add logic here to add/remove menu items if needed
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}
