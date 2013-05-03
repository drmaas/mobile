package edu.umn.kill9.places.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.DRMLocation;

/**
 * User: drmaas
 * Date: 4/30/13
 */
public class ExternalPlaceAdapter extends ArrayAdapter<DRMLocation> {

    public ExternalPlaceAdapter(Context context, int textViewResourceId, List<DRMLocation> places) {
        super(context, textViewResourceId, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.external_place_item, parent, false);
        DRMLocation place = getItem(position);
        ((TextView)item.findViewById(R.id.ext_place_name)).setText(place.getLocationName());
        ((TextView)item.findViewById(R.id.ext_place_address)).setText(place.getAddress());

        return item;
    }
}