package edu.umn.kill9.places.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.model.Event;

import java.util.List;

/**
 * User: drmaas
 * Date: 4/24/13
 */
public class EventAdapter extends ArrayAdapter {

    /**
     * constructor
     *
     * @param context
     * @param textViewResourceId
     * @param events
     */
    public EventAdapter(Context context, int textViewResourceId, List<Event> events) {
        super(context, textViewResourceId, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event e = (Event)getItem(position);

        //get event date and title from event content provider

        //update values in view
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.eventslist_item, parent, false);
        TextView date = (TextView)item.findViewById(R.id.eventdate);
        date.setText("sample date");
        TextView title = (TextView)item.findViewById(R.id.eventtitle);
        title.setText("sample title");

        return item;
    }

}
