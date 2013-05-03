package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationUser extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_LOCATIONUSER = "LocationUser";
    public static final String COLUMN_HOMEADDRESS = "HomeAddress";

    /***************************Attributes************************************/
    private String _homeAddress;

    /**************************Constructors***********************************/
    public LocationUser(){ }

    public  LocationUser(String homeAddress){
        this._homeAddress = homeAddress;
    }

    /*******************Getter and Setter methods*****************************/
    public String getHomeAddress() {
        return _homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this._homeAddress = homeAddress;
    }

    /***************************Parse methods*********************************/
    public static LocationUser ParseObjectToLocationUser(ParseObject parseObject) {
        LocationUser locationUser = new LocationUser();

        //Simple Data
        locationUser.setId(parseObject.getObjectId());
        locationUser.setHomeAddress(parseObject.getString(COLUMN_HOMEADDRESS));

        //Relational Data

        return locationUser;
    }

    public static ParseObject LocationUserToParseObject(LocationUser locationUser) {
        ParseObject parseObject = new ParseObject(TABLE_LOCATIONUSER);

        //Simple Data
        parseObject.put(COLUMN_HOMEADDRESS, locationUser.getHomeAddress());

        //Relational Data

        //Fill in the ID if it exists (for updates)
        String id = locationUser.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(locationUser.getId());
        }

        return parseObject;
    }
}
