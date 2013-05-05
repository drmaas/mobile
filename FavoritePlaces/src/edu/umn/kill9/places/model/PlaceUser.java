package edu.umn.kill9.places.model;

import com.parse.ParseObject;

/**
 * User: cspronk
 * Date: 5/2/13
 */
public class PlaceUser {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACEUSER = "PlaceUser";
    public static final String COLUMN_HOMEADDRESS = "HomeAddress";
    public static final String DEVICE_ID = "DeviceId";

    /***************************Attributes************************************/
    private String _id;
    private String _homeAddress;
    private String _deviceId;

    /**************************Constructors***********************************/
    public PlaceUser(){ }

    public PlaceUser(String homeAddress){
        this._homeAddress = homeAddress;
    }

    /*******************Getter and Setter methods*****************************/
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getHomeAddress() {
        return _homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this._homeAddress = homeAddress;
    }

    public String getDeviceId() {
        return _deviceId;
    }

    public void setDeviceId(String deviceId) {
        this._deviceId = deviceId;
    }

    /***************************Parse methods*********************************/
    public static PlaceUser ParseObjectToPlaceUser(ParseObject parseObject) {
        PlaceUser placeUser = new PlaceUser();

        //Simple Data
        placeUser.setId(parseObject.getObjectId());
        placeUser.setHomeAddress(parseObject.getString(COLUMN_HOMEADDRESS));

        //Relational Data

        return placeUser;
    }

    public static ParseObject PlaceUserToParseObject(PlaceUser placeUser) {
        ParseObject parseObject = new ParseObject(TABLE_PLACEUSER);

        //Simple Data
        parseObject.put(COLUMN_HOMEADDRESS, placeUser.getHomeAddress());

        //Relational Data

        //Fill in the ID if it exists (for updates)
        String id = placeUser.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(placeUser.getId());
        }

        return parseObject;
    }
}
