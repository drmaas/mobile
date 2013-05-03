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
    public static final String COLUMN_PLACE = "PlaceId";

    /***************************Attributes************************************/
    private Place _place;

    /**************************Constructors***********************************/
    public Event(){ }

    public  Event(Place place){
        this._place = place;
    }

    /*******************Getter and Setter methods*****************************/
    public Place getPlace() {
        return _place;
    }

    public void setPlace(Place place) {
        this._place = place;
    }

    /***************************Parse methods*********************************/
    public static Event ParseObjectToEvent(ParseObject parseObject) {
        Event event = new Event();

        //Simple Data
        event.setId(parseObject.getObjectId());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_PLACE) != null)
        {
            Place place = Place.ParseObjectToPlace(parseObject.getParseObject(COLUMN_PLACE));
            event.setPlace(place);
        }

        return event;
    }

    public static ParseObject EventToParseObject(Event event) {
        ParseObject parseObject = new ParseObject(TABLE_EVENT);

        //Simple Data

        //Relational Data
        if(event.getPlace() != null)
        {
            parseObject.put(COLUMN_PLACE, Place.PlaceToParseObject(event.getPlace()));
        }

        //Fill in the ID if it exists (for updates)
        String id = event.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(event.getId());
        }

        return parseObject;
    }
}
