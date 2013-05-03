package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Location extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_LOCATION = "Location";
    public static final String COLUMN_LOCATIONNAME = "Name";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_PHONE = "Phone";
    public static final String COLUMN_WEBSITE = "Website";
    public static final String COLUMN_HOURS = "Hours";
    public static final String COLUMN_USERCOMMENTS = "UserComments";
    public static final String COLUMN_LOCATIONPOINT = "Point";
    public static final String COLUMN_LOCATIONUSER = "UserId";

    /***************************Attributes************************************/
    private String _locationName;
    private String _address;
    private String _phone;
    private String _website;
    private String _hours;
    private String _userComments;
    //private LatLng _locationPoint;
    private LocationUser _locationUser;

    /**************************Constructors***********************************/
    public Location(){ }

    public  Location(String locationName, String address, String phone, String website, String hours, String userComments, LocationUser locationUser){
        this._locationName = locationName;
        this._address = address;
        this._phone = phone;
        this._website = website;
        this._hours = hours;
        this._userComments = userComments;
        //this._locationPoint = locationPoint;
        this._locationUser = locationUser;
    }

    /*******************Getter and Setter methods*****************************/
    public String getLocationName() {
        return _locationName;
    }

    public void setLocationName(String locationName) {
        this._locationName = locationName;
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

//    public LatLng getLocationPoint() {
//        return _locationPoint;
//    }
//
//    public void setLocationPoint(LatLng locationPoint) {
//        this._locationPoint = locationPoint;
//    }

    public LocationUser getLocationUser() {
        return _locationUser;
    }

    public void setLocationUser(LocationUser locationUser) {
        this._locationUser = locationUser;
    }

    /***************************Parse methods*********************************/
    public static Location ParseObjectToLocation(ParseObject parseObject) {
        Location location = new Location();

        //Simple Data
        location.setId(parseObject.getObjectId());
        location.setLocationName(parseObject.get(COLUMN_LOCATIONNAME).toString());
        location.setAddress(parseObject.get(COLUMN_ADDRESS).toString());
        location.setPhone(parseObject.get(COLUMN_PHONE).toString());
        location.setWebsite(parseObject.get(COLUMN_WEBSITE).toString());
        location.setHours(parseObject.get(COLUMN_HOURS).toString());
        location.setUserComments(parseObject.get(COLUMN_USERCOMMENTS).toString());
        //location.setLocationPoint(parseObject.get(COLUMN_LOCATIONPOINT).toString());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_LOCATIONUSER) != null)
        {
            LocationUser locationUser = LocationUser.ParseObjectToLocationUser(parseObject.getParseObject(COLUMN_LOCATIONUSER));
            location.setLocationUser(locationUser);
        }

        return location;
    }

    public static ParseObject LocationToParseObject(Location location) {
        ParseObject parseObject = new ParseObject(TABLE_LOCATION);

        //Simple Data
        parseObject.put(COLUMN_LOCATIONNAME, location.getLocationName());
        parseObject.put(COLUMN_ADDRESS, location.getAddress());
        parseObject.put(COLUMN_PHONE, location.getPhone());
        parseObject.put(COLUMN_WEBSITE, location.getWebsite());
        parseObject.put(COLUMN_HOURS, location.getHours());
        parseObject.put(COLUMN_USERCOMMENTS, location.getUserComments());
        //parseObject.put(COLUMN_LOCATIONPOINT, location.getLocationPoint());

        //Relational Data
        if(location.getLocationUser() != null)
        {
            parseObject.put(COLUMN_LOCATIONUSER, LocationUser.LocationUserToParseObject(location.getLocationUser()));
        }

        //Fill in the ID if it exists (for updates)
        String id = location.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(location.getId());
        }

        return parseObject;
    }
}
