package edu.umn.kill9.places.model;

import com.parse.ParseObject;

/**
 * User: drmaas
 * Date: 4/24/13
 */
public class Event extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_EVENT = "Event";
    public static final String COLUMN_PLACE = "PlaceId";
    public static final String COLUMN_CALENDARID = "CalendarId";

    /**************************Constructors***********************************/
    public Event(){
        super(TABLE_EVENT);
    }

    public Event(Place place, int calendarId){
        super(TABLE_EVENT);
        setPlace(place);
        setCalendarId(calendarId);
    }

    public  Event(ParseObject parseObject){
        setParseObject(parseObject);
    }

    /*******************Getter and Setter methods*****************************/
    public Place getPlace() {
        return new Place(_parseObject.getParseObject(COLUMN_PLACE));
    }

    public void setPlace(Place place) {
        _parseObject.put(COLUMN_PLACE, place.getParseObject());
    }

    public int getCalendarId() {
        return _parseObject.getInt(COLUMN_CALENDARID);
    }

    public void setCalendarId(int calendarId) {
        _parseObject.put(COLUMN_CALENDARID, calendarId);
    }
}
