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
    public Place createPlace(String placeName, String address, String phone, String website, String hours, String userComments, PlaceUser placeUser) throws ParseException {
        //Simple Data
        Place place = new Place();
        place.setPlaceName(placeName);
        place.setAddress(address);
        place.setPhone(phone);
        place.setWebsite(website);
        place.setHours(hours);
        place.setUserComments(userComments);
        //place.setPlacePoint(placePoint);

        //Relational Data
        if(placeUser != null)
        {
            place.setPlaceUser(placeUser);
        }

        place.save();

        return place;
    }

    public Place createPlace(Place place) throws ParseException {
        place.save();
        return place;
    }

    public Place getPlace(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Place.TABLE_PLACE);
        return new Place(query.get(id));
    }

    public List<Place> getAllPlace() throws ParseException {
        List<Place> places = new ArrayList<Place>();

        ParseQuery query = new ParseQuery(Place.TABLE_PLACE);

        for (ParseObject parseObject : query.find()) {
            places.add(new Place(parseObject));
        }

        return places;
    }

    public List<Place> getAllUserPlaces(PlaceUser user) throws ParseException {
        List<Place> places = new ArrayList<Place>();

        ParseQuery query = new ParseQuery(Place.TABLE_PLACE).whereEqualTo(Place.COLUMN_PLACEUSER, user.getParseObject());

        for (ParseObject parseObject : query.find()) {
            places.add(new Place(parseObject));
        }

        return places;
    }

    public void updatePlace(Place place) throws ParseException {
        place.save();
    }

    public void deletePlace(Place place) throws ParseException {
        place.delete();
    }

    public void deletePlace(String placeId) throws ParseException {
        ParseObject parseObject = new ParseObject(Place.TABLE_PLACE);
        parseObject.setObjectId(placeId);
        parseObject.delete();
    }
}
