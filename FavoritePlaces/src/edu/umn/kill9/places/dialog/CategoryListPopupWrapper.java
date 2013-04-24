package edu.umn.kill9.places.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.model.Category;

import java.util.List;

/**
 * User: drmaas
 * Date: 4/15/13
 */
public class CategoryListPopupWrapper {

    private Activity activity;
    private PopupWindow pw;

    /**
     *
     * @param activity
     */
    public CategoryListPopupWrapper(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     */
    public void dismiss() {
        if (pw != null) {
            pw.dismiss();
        }
    }

    /**
     *
     * @return
     */
    public boolean isShowing() {
        if (pw != null) {
            return pw.isShowing();
        }
        else {
            return false;
        }

    }

    /**
     * Function to set up the pop-up window which acts as drop-down list
     *
     * @param adapter
     */
    public void show(final ArrayAdapter adapter) {
        final LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get the pop-up window i.e. drop-down layout
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.categorylist, (ViewGroup)activity.findViewById(R.id.dropdownlayout));

        //get the view to which drop-down layout is to be anchored
        RelativeLayout pwanchor = (RelativeLayout)activity.findViewById(R.id.pwanchor);

        //create popup
        pw = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new ColorDrawable());

        //let pop-up be informed about touch events outside its window. This should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);

        //anchor the drop-down to bottom-left corner of dropdown
        pw.showAsDropDown(pwanchor);

        //setup select all option
        final CheckBox selectall = (CheckBox)layout.findViewById(R.id.selectallcheckbox);
        selectall.setChecked(((CategoryAdapter) adapter).areAllSelected());
        selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectall.isChecked()) {
                    ((CategoryAdapter) adapter).selectAll();
                } else {
                    ((CategoryAdapter) adapter).selectNone();
                }
            }
        });

        //setup new category option
        final TextView newtext = (TextView)layout.findViewById(R.id.newcategory);
        newtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog asking for new category
                AddCategoryDialogFragment.newInstance(activity.getString(R.string.newcategory), adapter).show(activity.getFragmentManager(), "dialog");
            }
        });

        //populate the drop-down list
        final ListView list = (ListView)layout.findViewById(R.id.dropdownlist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Boolean> selected = ((CategoryAdapter) adapter).getSelected();
                selected.set(position, !selected.get(position));
                CheckBox box = (CheckBox)view.findViewById(R.id.itemcheckbox);
                box.setChecked(selected.get(position));
            }
        });
    }
}
