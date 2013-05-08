package edu.umn.kill9.places.activity.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.*;
import android.widget.ListView;
import com.parse.ParseException;
import edu.umn.kill9.places.R;
import edu.umn.kill9.places.activity.PlaceDetailsActivity;
import edu.umn.kill9.places.adapter.EventAdapter;
import edu.umn.kill9.places.model.Event;
import edu.umn.kill9.places.model.EventDataSource;
import edu.umn.kill9.places.model.Place;
import edu.umn.kill9.places.util.PlacesConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 05/07/2013 - disabling events. Using intents to navigate to calendar apps really messes things up.
 * When you return from the calendar you don't get appropriate data to integrate with our app.
 * The ideal solution would be to interact directly with the calendar DB. There is not enough time to implement
 * this right now, unfortunately.
 *
 * User: drmaas
 * Date: 4/18/13
 */
public class EventsFragment extends ListFragment {

    private List<Event> events;
    EventDataSource ds;
    Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //get events for this location
        refreshEventsList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        event = events.get(position);
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.getCalendarId());
        Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
        startActivityForResult(intent, PlacesConstants.EDIT_EVENT);
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
                Place place = ((PlaceDetailsActivity)getActivity()).getPlace();
                try {
                    //use dummy eventID since we don't have it yet from calendar
                    event = ds.createEvent(place, -1);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                navigateToCalendar(place, event);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //check if we need to delete event from our data
        if (resultCode == Activity.RESULT_CANCELED && requestCode == PlacesConstants.EDIT_EVENT) {
            deleteEventIfRemoved();
            refreshEventsList();
        }
        //check if we need to add event to our data
        else if (resultCode == Activity.RESULT_CANCELED && requestCode == PlacesConstants.ADD_EVENT) {
            Bundle extras = data.getExtras();
            //String title = extras.getString("title");

        }
    }

    /**
     *
     * @param place
     */
    private void navigateToCalendar(Place place, Event e) {
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        Uri uri = CalendarContract.Events.CONTENT_URI; //ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, e.getCalendarId());
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(uri)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, getString(R.string.default_title) + " " + place.getPlaceName())
                .putExtra(CalendarContract.Events.DESCRIPTION, getString(R.string.default_description) + " " + place.getWebsite())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, place.getAddress())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_PHONE_NUMBER, place.getPhone());
        startActivityForResult(intent, PlacesConstants.ADD_EVENT);
    }

    /**
     *
     */
    private void deleteEventIfRemoved() {
        //check if event was deleted, if so, delete from our data
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID
        };

        ContentResolver cr = getActivity().getContentResolver();
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.getCalendarId());
        Cursor cursor = cr.query(uri, INSTANCE_PROJECTION, null, null, null);
        boolean exists = false;
        while (cursor.moveToNext()) {
            exists = true;
        }
        if (!exists) {
            try {
                ds.deleteEvent(event.getId());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void refreshEventsList() {
        String placeId = ((PlaceDetailsActivity)getActivity()).getPlace().getId();
        ds = new EventDataSource();
        try {
            events = ds.getAllPlaceEvents(placeId);
        }
        catch (ParseException e) {
            events = new ArrayList<Event>();
        }

        setListAdapter(new EventAdapter(getActivity(), R.layout.eventslist_item, events));
    }

}
