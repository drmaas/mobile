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
public class LocationDataSource {
    public Location createLocation(String locationName, String address, String phone, String website, String hours, String userComments, LocationUser locationUser) throws ParseException {
        //Simple Data
        ParseObject parseObject = new ParseObject(Location.TABLE_LOCATION);
        parseObject.put(Location.COLUMN_LOCATIONNAME, locationName);
        parseObject.put(Location.COLUMN_ADDRESS, address);
        parseObject.put(Location.COLUMN_PHONE, phone);
        parseObject.put(Location.COLUMN_WEBSITE, website);
        parseObject.put(Location.COLUMN_HOURS, hours);
        parseObject.put(Location.COLUMN_USERCOMMENTS, userComments);
        //parseObject.put(Location.COLUMN_LOCATIONPOINT, locationPoint);

        //Relational Data
        if(locationUser != null)
        {
            parseObject.put(Location.COLUMN_LOCATIONUSER, LocationUser.LocationUserToParseObject(locationUser));
        }

        parseObject.save();

        return Location.ParseObjectToLocation(parseObject);
    }

    public Location createLocation(Location location) throws ParseException {
        ParseObject parseObject = Location.LocationToParseObject(location);
        parseObject.save();

        location.setId(parseObject.getObjectId());
        return location;
    }

    public Location getLocation(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Location.TABLE_LOCATION);

        Location location = Location.ParseObjectToLocation(query.get(id));

        return location;
    }

    public List<Location> getAllLocation() throws ParseException {
        List<Location> locations = new ArrayList<Location>();

        ParseQuery query = new ParseQuery(Location.TABLE_LOCATION);

        for (ParseObject parseObject : query.find()) {
            locations.add(Location.ParseObjectToLocation(parseObject));
        }

        return locations;
    }

    public void updateLocation(Location location) throws ParseException {
        ParseObject parseObject = Location.LocationToParseObject(location);
        parseObject.save();
    }

    public void deleteLocation(Location location) throws ParseException {
        ParseObject parseObject = Location.LocationToParseObject(location);
        parseObject.delete();
    }

    public void deleteLocation(String locationId) throws ParseException {
        ParseObject parseObject = new ParseObject(Location.TABLE_LOCATION);
        parseObject.setObjectId(locationId);
        parseObject.delete();
    }
}
