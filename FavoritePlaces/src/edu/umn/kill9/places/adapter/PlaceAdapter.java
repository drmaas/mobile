package edu.umn.kill9.places.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Place;

import java.util.List;

/**
 * User: drmaas
 * Date: 4/30/13
 */
public class PlaceAdapter extends ArrayAdapter<Place> {
    public PlaceAdapter(Context context, int textViewResourceId, List<Place> places) {
        super(context, textViewResourceId, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.place_item, parent, false);
        Place place = getItem(position);
        ((TextView)item.findViewById(R.id.place_name)).setText(place.getName());
        ((TextView)item.findViewById(R.id.place_vicinity)).setText(place.getVicinity());
        ((TextView)item.findViewById(R.id.place_distance)).setText(place.getDistance());

        return item;
    }
}