package edu.umn.kill9.places.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
