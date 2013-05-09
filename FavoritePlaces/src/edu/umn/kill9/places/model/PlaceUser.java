package edu.umn.kill9.places.model;

import com.parse.ParseObject;

/**
 * User: cspronk
 * Date: 5/2/13
 */
public class PlaceUser extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACEUSER = "PlaceUser";
    public static final String COLUMN_HOMEADDRESS = "HomeAddress";
    public static final String COLUMN_DEVICEID = "DeviceId";

    /**************************Constructors***********************************/
    public PlaceUser(){
        super(TABLE_PLACEUSER);
    }

    public PlaceUser(String homeAddress, String deviceId){
        super(TABLE_PLACEUSER);
        setHomeAddress(homeAddress);
        setDeviceId(deviceId);
    }

    public PlaceUser(ParseObject parseObject){
        setParseObject(parseObject);
    }

    /*******************Getter and Setter methods*****************************/
    public String getHomeAddress() {
        return _parseObject.getString(COLUMN_HOMEADDRESS);
    }

    public void setHomeAddress(String homeAddress) {
        _parseObject.put(COLUMN_HOMEADDRESS, homeAddress);
    }

    public String getDeviceId() {
        return _parseObject.getString(COLUMN_DEVICEID);
    }

    public void setDeviceId(String deviceId) {
        _parseObject.put(COLUMN_DEVICEID, deviceId);
    }
}
