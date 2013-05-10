package edu.umn.kill9.places.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.parse.ParseException;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.adapter.CategoryAdapter;
import edu.umn.kill9.places.model.Category;
import edu.umn.kill9.places.model.CategoryDataSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: drmaas
 * Date: 5/9/13
 */
public class DeleteCategoryDialogFragment extends DialogFragment {

    private ArrayAdapter adapter;

    private DeleteCategoryDialogFragment(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    public static DeleteCategoryDialogFragment newInstance(String title, ArrayAdapter categoryAdapter) {
        DeleteCategoryDialogFragment frag = new DeleteCategoryDialogFragment(categoryAdapter);
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedBundleInstance) {
        String title = getArguments().getString("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_deletecategory, (ViewGroup)getActivity().findViewById(R.id.delete_category_dialog_layout));
        //final TextView text = (TextView)layout.findViewById(R.id.categoryname);

        final List<Boolean> selected = ((CategoryAdapter)adapter).getSelected();

        builder.setTitle(title);
        builder.setView(layout); //inflater.inflate(R.layout.dialog_newcategory, null));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CategoryDataSource ds = new CategoryDataSource();
                Category c;
                List<Category> tempList = new ArrayList<Category>();
                for (int i = 0; i < selected.size(); i++) {
                    if (selected.get(i) == Boolean.TRUE) {
                        c = ((CategoryAdapter) adapter).getItem(i);
                        tempList.add(c);
                        try {
                            ds.deleteCategory(c);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Iterator<Category> it = tempList.iterator();
                while (it.hasNext()) {
                    c = it.next();
                    adapter.remove(c);
                }
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //User cancelled the dialog, do nothing
            }
        });

        return builder.create();
    }
}
