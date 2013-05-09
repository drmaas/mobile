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
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceUserDataSource {
    public PlaceUser createPlaceUser(String homeAddress, String deviceId) throws ParseException {
        PlaceUser placeUser = new PlaceUser();
        placeUser.setHomeAddress(homeAddress);
        placeUser.setDeviceId(deviceId);

        placeUser.save();

        return placeUser;
    }

    public PlaceUser createPlaceUser(PlaceUser placeUser) throws ParseException {
        placeUser.save();
        return placeUser;
    }

    public PlaceUser getPlaceUser(String id) throws ParseException {
        ParseQuery query = new ParseQuery(PlaceUser.TABLE_PLACEUSER);
        return new PlaceUser(query.get(id));
    }

    public PlaceUser getPlaceUserByDeviceId(String deviceId) throws ParseException {
        ParseQuery query = new ParseQuery(PlaceUser.TABLE_PLACEUSER);

        return new PlaceUser(query.whereEqualTo(PlaceUser.COLUMN_DEVICEID, deviceId).getFirst());
    }

    public List<PlaceUser> getAllPlaceUser() throws ParseException {
        List<PlaceUser> placeUsers = new ArrayList<PlaceUser>();

        ParseQuery query = new ParseQuery(PlaceUser.TABLE_PLACEUSER);

        for (ParseObject parseObject : query.find()) {
            placeUsers.add(new PlaceUser(parseObject));
        }

        return placeUsers;
    }

    public void updatePlaceUser(PlaceUser placeUser) throws ParseException {
        placeUser.save();
    }

    public void deletePlaceUser(PlaceUser placeUser) throws ParseException {
        placeUser.delete();
    }

    public void deletePlaceUser(String placeUserId) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceUser.TABLE_PLACEUSER);
        parseObject.setObjectId(placeUserId);
        parseObject.delete();
    }
}
