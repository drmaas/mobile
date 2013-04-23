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
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Category;

/**
 * User: drmaas
 * Date: 4/14/13
 */
public class AddCategoryDialogFragment extends DialogFragment {

    private ArrayAdapter adapter;

    private AddCategoryDialogFragment(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    public static AddCategoryDialogFragment newInstance(String title, ArrayAdapter categoryAdapter) {
        AddCategoryDialogFragment frag = new AddCategoryDialogFragment(categoryAdapter);
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
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_newcategory, (ViewGroup)getActivity().findViewById(R.id.category_dialog_layout));
        final TextView text = (TextView)layout.findViewById(R.id.categoryname);

        builder.setTitle(title);
        builder.setView(layout); //inflater.inflate(R.layout.dialog_newcategory, null));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                adapter.add(new Category(text.getText().toString()));
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
