package edu.umn.kill9.places.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Category;

import java.util.List;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    private List<Boolean> selected;

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

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.categorylist_item, parent, false);

        //set text of list item
        final Category c = getItem(position);
        CheckedTextView ctext = (CheckedTextView)item.findViewById(R.id.itemtext);
        ctext.setText(c.getName());
        ctext.setChecked(selected.get(position));

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

    /**
     *
     * @return
     */
    public boolean areAllSelected() {
        for (int i = 0; i < selected.size(); i++) {
           if (selected.get(i) == true) {
               continue;
           }
           else {
               return false;
           }
        }
        return true;
    }

    public List<Boolean> getSelected() {
        return selected;
    }

}
