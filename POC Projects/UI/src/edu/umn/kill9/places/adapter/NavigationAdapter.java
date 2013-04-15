package edu.umn.kill9.places.adapter;

/**
 * User: drmaas
 * Date: 4/15/13
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umn.kill9.places.R;

/**
 * This class provides behavior to hide/alter items in the drop-down list
 */
public class NavigationAdapter extends ArrayAdapter<String> {

    private final String[] strings;
    private String currentView;

    public NavigationAdapter(Context context, int textViewResourceId, String[] objects, String currentView) {
        super(context, textViewResourceId, objects);
        strings = getContext().getResources().getStringArray(R.array.locations_navigation_view);
        this.currentView = currentView;
    }

    /**
     * Hide current item if it's the currently applied view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View v = null;
        String item = strings[position];

        if (currentView.equals(item)) {
            TextView tv = new TextView(getContext());
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            v = tv;
        }
        else {
            v = super.getDropDownView(position, null, parent);
        }

        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
        parent.setVerticalScrollBarEnabled(false);

        return v;

    }

    /**
     *
     * @param currentView
     */
    public void setCurrentView(String currentView) {
        this.currentView = currentView;
    }

}
