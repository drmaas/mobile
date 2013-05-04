package edu.umn.kill9.places.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventDataSource {
    public Event createEvent(Place place) throws ParseException {
        ParseObject parseObject = new ParseObject(Event.TABLE_EVENT);

        if(place != null)
        {
            parseObject.put(Event.COLUMN_PLACE, Place.PlaceToParseObject(place));
        }

        parseObject.save();

        return Event.ParseObjectToEvent(parseObject);
    }

    public Event createEvent(Event event) throws ParseException {
        ParseObject parseObject = Event.EventToParseObject(event);
        parseObject.save();

        event.setId(parseObject.getObjectId());
        return event;
    }

    public Event getEvent(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Event.TABLE_EVENT);

        Event event = Event.ParseObjectToEvent(query.get(id));

        return event;
    }

    public List<Event> getAllEvent() throws ParseException {
        List<Event> events = new ArrayList<Event>();

        ParseQuery query = new ParseQuery(Event.TABLE_EVENT);

        for (ParseObject parseObject : query.find()) {
            events.add(Event.ParseObjectToEvent(parseObject));
        }

        return events;
    }

    public void updateEvent(Event event) throws ParseException {
        ParseObject parseObject = Event.EventToParseObject(event);
        parseObject.save();
    }

    public void deleteEvent(Event event) throws ParseException {
        ParseObject parseObject = Event.EventToParseObject(event);
        parseObject.delete();
    }

    public void deleteEvent(String eventId) throws ParseException {
        ParseObject parseObject = new ParseObject(Event.TABLE_EVENT);
        parseObject.setObjectId(eventId);
        parseObject.delete();
    }
}
