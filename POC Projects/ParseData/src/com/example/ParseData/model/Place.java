package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Place extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACE = "Place";
    public static final String COLUMN_PLACENAME = "Name";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_PHONE = "Phone";
    public static final String COLUMN_WEBSITE = "Website";
    public static final String COLUMN_HOURS = "Hours";
    public static final String COLUMN_USERCOMMENTS = "UserComments";
    public static final String COLUMN_PLACEPOINT = "Point";
    public static final String COLUMN_PLACEUSER = "UserId";

    /***************************Attributes************************************/
    private String _placeName;
    private String _address;
    private String _phone;
    private String _website;
    private String _hours;
    private String _userComments;
    //private LatLng _placePoint;
    private PlaceUser _placeUser;

    /**************************Constructors***********************************/
    public Place(){ }

    public Place(String placeName, String address, String phone, String website, String hours, String userComments, PlaceUser placeUser){
        this._placeName = placeName;
        this._address = address;
        this._phone = phone;
        this._website = website;
        this._hours = hours;
        this._userComments = userComments;
        //this._placePoint = placePoint;
        this._placeUser = placeUser;
    }

    /*******************Getter and Setter methods*****************************/
    public String getPlaceName() {
        return _placeName;
    }

    public void setPlaceName(String placeName) {
        this._placeName = placeName;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String address) {
        this._address = address;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String phone) {
        this._phone = phone;
    }

    public String getWebsite() {
        return _website;
    }

    public void setWebsite(String website) {
        this._website = website;
    }

    public String getHours() {
        return _hours;
    }

    public void setHours(String hours) {
        this._hours = hours;
    }

    public String getUserComments() {
        return _userComments;
    }

    public void setUserComments(String userComments) {
        this._userComments = userComments;
    }

//    public LatLng getPlacePoint() {
//        return _placePoint;
//    }
//
//    public void setPlacePoint(LatLng placePoint) {
//        this._placePoint = placePoint;
//    }

    public PlaceUser getPlaceUser() {
        return _placeUser;
    }

    public void setPlaceUser(PlaceUser placeUser) {
        this._placeUser = placeUser;
    }

    /***************************Parse methods*********************************/
    public static Place ParseObjectToPlace(ParseObject parseObject) {
        Place place = new Place();

        //Simple Data
        place.setId(parseObject.getObjectId());
        place.setPlaceName(parseObject.get(COLUMN_PLACENAME).toString());
        place.setAddress(parseObject.get(COLUMN_ADDRESS).toString());
        place.setPhone(parseObject.get(COLUMN_PHONE).toString());
        place.setWebsite(parseObject.get(COLUMN_WEBSITE).toString());
        place.setHours(parseObject.get(COLUMN_HOURS).toString());
        place.setUserComments(parseObject.get(COLUMN_USERCOMMENTS).toString());
        //place.setPlacePoint(parseObject.get(COLUMN_PLACEPOINT).toString());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_PLACEUSER) != null)
        {
            PlaceUser placeUser = PlaceUser.ParseObjectToPlaceUser(parseObject.getParseObject(COLUMN_PLACEUSER));
            place.setPlaceUser(placeUser);
        }

        return place;
    }

    public static ParseObject PlaceToParseObject(Place place) {
        ParseObject parseObject = new ParseObject(TABLE_PLACE);

        //Simple Data
        parseObject.put(COLUMN_PLACENAME, place.getPlaceName());
        parseObject.put(COLUMN_ADDRESS, place.getAddress());
        parseObject.put(COLUMN_PHONE, place.getPhone());
        parseObject.put(COLUMN_WEBSITE, place.getWebsite());
        parseObject.put(COLUMN_HOURS, place.getHours());
        parseObject.put(COLUMN_USERCOMMENTS, place.getUserComments());
        //parseObject.put(COLUMN_PLACEPOINT, place.getPlacePoint());

        //Relational Data
        if(place.getPlaceUser() != null)
        {
            parseObject.put(COLUMN_PLACEUSER, PlaceUser.PlaceUserToParseObject(place.getPlaceUser()));
        }

        //Fill in the ID if it exists (for updates)
        String id = place.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(place.getId());
        }

        return parseObject;
    }
}
