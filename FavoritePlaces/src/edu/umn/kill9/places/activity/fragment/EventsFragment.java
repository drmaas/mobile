package edu.umn.kill9.places.activity.fragment;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.adapter.EventAdapter;
import edu.umn.kill9.places.model.Event;
import edu.umn.kill9.places.model.data.SampleLocationList;
import edu.umn.kill9.places.util.PlacesConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/18/13
 */
public class EventsFragment extends ListFragment {

    private List<Event> events;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //dummy data
        events = new ArrayList<Event>();
        events.add(new Event(new Long(0)));
        events.add(new Event(new Long(1)));
        events.add(new Event(new Long(2)));
        events.add(new Event(new Long(3)));

        setListAdapter(new EventAdapter(getActivity(), R.layout.eventslist_item, events));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        long eventID = events.get(position).getId();
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        Intent intent = new Intent(Intent.ACTION_EDIT)
                .setData(uri);
        startActivityForResult(intent, PlacesConstants.EVENTS);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_events_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.add_event:
                //handle adding event
                return true;
            case R.id.delete_event:
                //handle removing event here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
