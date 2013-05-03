package com.example.ParseData.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/1/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceDataSource {
    public Place createPlace(String placeName, String address, String phone, String website, String hours, String userComments, PlaceUser placeUser) throws ParseException {
        //Simple Data
        ParseObject parseObject = new ParseObject(Place.TABLE_PLACE);
        parseObject.put(Place.COLUMN_PLACENAME, placeName);
        parseObject.put(Place.COLUMN_ADDRESS, address);
        parseObject.put(Place.COLUMN_PHONE, phone);
        parseObject.put(Place.COLUMN_WEBSITE, website);
        parseObject.put(Place.COLUMN_HOURS, hours);
        parseObject.put(Place.COLUMN_USERCOMMENTS, userComments);
        //parseObject.put(Place.COLUMN_PLACEPOINT, placePoint);

        //Relational Data
        if(placeUser != null)
        {
            parseObject.put(Place.COLUMN_PLACEUSER, PlaceUser.PlaceUserToParseObject(placeUser));
        }

        parseObject.save();

        return Place.ParseObjectToPlace(parseObject);
    }

    public Place createPlace(Place place) throws ParseException {
        ParseObject parseObject = Place.PlaceToParseObject(place);
        parseObject.save();

        place.setId(parseObject.getObjectId());
        return place;
    }

    public Place getPlace(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Place.TABLE_PLACE);

        Place place = Place.ParseObjectToPlace(query.get(id));

        return place;
    }

    public List<Place> getAllPlace() throws ParseException {
        List<Place> places = new ArrayList<Place>();

        ParseQuery query = new ParseQuery(Place.TABLE_PLACE);

        for (ParseObject parseObject : query.find()) {
            places.add(Place.ParseObjectToPlace(parseObject));
        }

        return places;
    }

    public void updatePlace(Place place) throws ParseException {
        ParseObject parseObject = Place.PlaceToParseObject(place);
        parseObject.save();
    }

    public void deletePlace(Place place) throws ParseException {
        ParseObject parseObject = Place.PlaceToParseObject(place);
        parseObject.delete();
    }

    public void deletePlace(String placeId) throws ParseException {
        ParseObject parseObject = new ParseObject(Place.TABLE_PLACE);
        parseObject.setObjectId(placeId);
        parseObject.delete();
    }
}
