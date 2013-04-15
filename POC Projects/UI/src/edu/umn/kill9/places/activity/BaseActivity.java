package edu.umn.kill9.places.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Category;

import java.util.List;

/**
 * User: drmaas
 * Date: 4/12/13
 */
public abstract class BaseActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * if true, enable home icon with 'up' (back) next to it
     * otherwise just show home icon
     *
     * @param up
     */
    protected void showHomeAsUp(boolean up) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(up);
    }

    /**
     * Go to home activity or up one in hierarchy
     */
    protected void goHome(Class clazz) {
        // app icon in action bar clicked; go home
        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Enable navigation mode for top action bar
     */
    protected void enableNavigationMode() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }

    /**
     * Define what happens when home or settings item selected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                //go home
                goHome(PlacesActivity.class);
                return true;
            case R.id.settings:
                //go to settings
                return true;
            case R.id.add_this_location:
                //go to add current location screen
                return true;
            case R.id.add_external_location:
                //got to search for external location screen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CategoryAdapter extends ArrayAdapter<Category> {

        List<Boolean> selected;

        public CategoryAdapter(Context context, int textViewResourceId, List<Category> categories, List<Boolean> selected) {
            super(context, textViewResourceId, categories);
            this.selected = selected;
        }

        /**
         * return a textview for each item in category list
         *
         * TODO add in ref to the textbox of dropdown so it can be updated on click
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View item = inflater.inflate(R.layout.categorylist_item, parent, false);

            //set text of list item
            final Category c = getItem(position);
            final TextView text = (TextView)item.findViewById(R.id.itemtext);
            text.setText(c.getCategoryName());

            //setup checkbox
            CheckBox box = (CheckBox)item.findViewById(R.id.itemcheckbox);
            box.setChecked(selected.get(position));
            final int pos = position;
            box.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //toggle saved state
                    selected.set(pos, !selected.get(pos));
                }
            });

            return item;
        }

        @Override
        public void add(Category category) {
            super.add(category);
            selected.add(new Boolean(false));
        }

        /**
         * select all options
         */
        public void selectAll() {
            for (int i = 0; i < selected.size(); i++) {
                selected.set(i, true);
            }
            notifyDataSetChanged();
        }

        /**
         * select no options
         */
        public void selectNone() {
            for (int i = 0; i < selected.size(); i++) {
                selected.set(i, false);
            }
            notifyDataSetChanged();
        }
    }
}