package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceUser extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACEUSER = "PlaceUser";
    public static final String COLUMN_HOMEADDRESS = "HomeAddress";

    /***************************Attributes************************************/
    private String _homeAddress;

    /**************************Constructors***********************************/
    public PlaceUser(){ }

    public PlaceUser(String homeAddress){
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
