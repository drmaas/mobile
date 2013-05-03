package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Event extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_EVENT = "Event";
    public static final String COLUMN_LOCATION = "LocationId";

    /***************************Attributes************************************/
    private Location _location;

    /**************************Constructors***********************************/
    public Event(){ }

    public  Event(Location location){
        this._location = location;
    }

    /*******************Getter and Setter methods*****************************/
    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        this._location = location;
    }

    /***************************Parse methods*********************************/
    public static Event ParseObjectToEvent(ParseObject parseObject) {
        Event event = new Event();

        //Simple Data
        event.setId(parseObject.getObjectId());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_LOCATION) != null)
        {
            Location location = Location.ParseObjectToLocation(parseObject.getParseObject(COLUMN_LOCATION));
            event.setLocation(location);
        }

        return event;
    }

    public static ParseObject EventToParseObject(Event event) {
        ParseObject parseObject = new ParseObject(TABLE_EVENT);

        //Simple Data

        //Relational Data
        if(event.getLocation() != null)
        {
            parseObject.put(COLUMN_LOCATION, Location.LocationToParseObject(event.getLocation()));
        }

        //Fill in the ID if it exists (for updates)
        String id = event.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(event.getId());
        }

        return parseObject;
    }
}
