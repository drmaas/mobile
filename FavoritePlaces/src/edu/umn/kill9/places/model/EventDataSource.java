package edu.umn.kill9.places.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventDataSource {
    public Event createEvent(Place place, int calendarId) throws ParseException {
        Event event = new Event();
        event.setCalendarId(calendarId);

        if(place != null)
        {
            event.setPlace(place);
        }

        event.save();

        return event;
    }

    public Event createEvent(Event event) throws ParseException {
        event.save();
        return event;
    }

    public Event getEvent(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Event.TABLE_EVENT);
        return new Event(query.get(id));
    }

    public List<Event> getAllEvent() throws ParseException {
        List<Event> events = new ArrayList<Event>();

        ParseQuery query = new ParseQuery(Event.TABLE_EVENT);

        for (ParseObject parseObject : query.find()) {
            events.add(new Event(parseObject));
        }

        return events;
    }

    public List<Event> getAllPlaceEvents(String placeId) throws ParseException {
        List<Event> events = new ArrayList<Event>();

        ParseQuery query = new ParseQuery(Event.TABLE_EVENT).whereEqualTo(Event.COLUMN_PLACE, placeId);

        for (ParseObject parseObject : query.find()) {

            events.add(new Event(parseObject));
        }

        return events;
    }

    public void updateEvent(Event event) throws ParseException {
        event.save();
    }

    public void deleteEvent(Event event) throws ParseException {
        event.delete();
    }

    public void deleteEvent(String eventId) throws ParseException {
        ParseObject parseObject = new ParseObject(Event.TABLE_EVENT);
        parseObject.setObjectId(eventId);
        parseObject.delete();
    }
}
