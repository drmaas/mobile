package edu.umn.kill9.places.model;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceDataSource {
    public Place createPlace(String placeName, String address, String phone, String website, String hours, String userComments, LatLng placePoint, PlaceUser placeUser) throws ParseException {
        //Simple Data
        ParseObject parseObject = new ParseObject(Place.TABLE_PLACE);
        parseObject.put(Place.COLUMN_PLACENAME, placeName);
        parseObject.put(Place.COLUMN_ADDRESS, address);
        parseObject.put(Place.COLUMN_PHONE, phone);
        parseObject.put(Place.COLUMN_WEBSITE, website);
        parseObject.put(Place.COLUMN_HOURS, hours);
        parseObject.put(Place.COLUMN_USERCOMMENTS, userComments);

        //Convert LatLng to ParseGeoPoint
        parseObject.put(Place.COLUMN_PLACEPOINT, new ParseGeoPoint(placePoint.latitude, placePoint.longitude));

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

    public List<Place> getAllUserPlaces(PlaceUser user) throws ParseException {
        List<Place> places = new ArrayList<Place>();

        ParseQuery query = new ParseQuery(Place.TABLE_PLACE).whereEqualTo(Place.COLUMN_PLACEUSER, PlaceUser.PlaceUserToParseObject(user));

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
