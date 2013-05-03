package com.example.ParseData.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationUserDataSource {
    public LocationUser createLocationUser(String homeAddress) throws ParseException {
        ParseObject parseObject = new ParseObject(LocationUser.TABLE_LOCATIONUSER);
        parseObject.put(LocationUser.COLUMN_HOMEADDRESS, homeAddress);

        parseObject.save();

        return LocationUser.ParseObjectToLocationUser(parseObject);
    }

    public LocationUser createLocationUser(LocationUser locationUser) throws ParseException {
        ParseObject parseObject = LocationUser.LocationUserToParseObject(locationUser);
        parseObject.save();

        locationUser.setId(parseObject.getObjectId());
        return locationUser;
    }

    public LocationUser getLocationUser(String id) throws ParseException {
        ParseQuery query = new ParseQuery(LocationUser.TABLE_LOCATIONUSER);

        LocationUser locationUser = LocationUser.ParseObjectToLocationUser(query.get(id));

        return locationUser;
    }

    public List<LocationUser> getAllLocationUser() throws ParseException {
        List<LocationUser> locationUsers = new ArrayList<LocationUser>();

        ParseQuery query = new ParseQuery(LocationUser.TABLE_LOCATIONUSER);

        for (ParseObject parseObject : query.find()) {
            locationUsers.add(LocationUser.ParseObjectToLocationUser(parseObject));
        }

        return locationUsers;
    }

    public void updateLocationUser(LocationUser locationUser) throws ParseException {
        ParseObject parseObject = LocationUser.LocationUserToParseObject(locationUser);
        parseObject.save();
    }

    public void deleteLocationUser(LocationUser locationUser) throws ParseException {
        ParseObject parseObject = LocationUser.LocationUserToParseObject(locationUser);
        parseObject.delete();
    }

    public void deleteLocationUser(String locationUserId) throws ParseException {
        ParseObject parseObject = new ParseObject(LocationUser.TABLE_LOCATIONUSER);
        parseObject.setObjectId(locationUserId);
        parseObject.delete();
    }
}
