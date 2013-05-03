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
public class PlaceUserDataSource {
    public PlaceUser createPlaceUser(String homeAddress) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceUser.TABLE_PLACEUSER);
        parseObject.put(PlaceUser.COLUMN_HOMEADDRESS, homeAddress);

        parseObject.save();

        return PlaceUser.ParseObjectToPlaceUser(parseObject);
    }

    public PlaceUser createPlaceUser(PlaceUser placeUser) throws ParseException {
        ParseObject parseObject = PlaceUser.PlaceUserToParseObject(placeUser);
        parseObject.save();

        placeUser.setId(parseObject.getObjectId());
        return placeUser;
    }

    public PlaceUser getPlaceUser(String id) throws ParseException {
        ParseQuery query = new ParseQuery(PlaceUser.TABLE_PLACEUSER);

        PlaceUser placeUser = PlaceUser.ParseObjectToPlaceUser(query.get(id));

        return placeUser;
    }

    public List<PlaceUser> getAllPlaceUser() throws ParseException {
        List<PlaceUser> placeUsers = new ArrayList<PlaceUser>();

        ParseQuery query = new ParseQuery(PlaceUser.TABLE_PLACEUSER);

        for (ParseObject parseObject : query.find()) {
            placeUsers.add(PlaceUser.ParseObjectToPlaceUser(parseObject));
        }

        return placeUsers;
    }

    public void updatePlaceUser(PlaceUser placeUser) throws ParseException {
        ParseObject parseObject = PlaceUser.PlaceUserToParseObject(placeUser);
        parseObject.save();
    }

    public void deletePlaceUser(PlaceUser placeUser) throws ParseException {
        ParseObject parseObject = PlaceUser.PlaceUserToParseObject(placeUser);
        parseObject.delete();
    }

    public void deletePlaceUser(String placeUserId) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceUser.TABLE_PLACEUSER);
        parseObject.setObjectId(placeUserId);
        parseObject.delete();
    }
}
