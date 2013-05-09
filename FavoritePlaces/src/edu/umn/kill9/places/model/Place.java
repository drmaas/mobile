package edu.umn.kill9.places.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class Place extends BaseModel implements Parcelable{
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
    //transient data
    private String _reference;
    private String _vicinity;
    private String _distance;

    /**************************Constructors***********************************/
    public Place() {
        this("",null);
        setAddress("n/a");
        setPhone("n/a");
        setWebsite("n/a");
        setHours("n/a");
        setUserComments("n/a");
        //this._placeUser = null;
    }

    public Place(String placeName, String address, String phone, String website, String hours, String userComments, LatLng placePoint, PlaceUser placeUser){
        super(TABLE_PLACE);
        setPlaceName(placeName);
        setAddress(address);
        setPhone(phone);
        setWebsite(website);
        setHours(hours);
        setUserComments(userComments);
        setPlacePoint(placePoint);
        setPlaceUser(placeUser);
    }

    public Place(String placeName, LatLng placePoint) {
        super(TABLE_PLACE);
        setPlaceName(placeName);
        setPlacePoint(placePoint);
    }

    private Place(Parcel in) {
        super(TABLE_PLACE);
        setId(in.readString());
        setPlaceName(in.readString());
        setAddress(in.readString());
        setPhone(in.readString());
        setWebsite(in.readString());
        setHours(in.readString());
        setUserComments(in.readString());
        setPlacePoint(new LatLng(in.readDouble(), in.readDouble()));
    }

    public  Place(ParseObject parseObject){
        setParseObject(parseObject);
    }

    /*******************Getter and Setter methods*****************************/
    public String getPlaceName() {
        return _parseObject.getString(COLUMN_PLACENAME);
    }

    public void setPlaceName(String placeName) {
        _parseObject.put(COLUMN_PLACENAME, placeName);
    }

    public String getAddress() {
        return _parseObject.getString(COLUMN_ADDRESS);
    }

    public void setAddress(String address) {
        _parseObject.put(COLUMN_ADDRESS, address);
    }

    public String getPhone() {
        return _parseObject.getString(COLUMN_PHONE);
    }

    public void setPhone(String phone) {
        _parseObject.put(COLUMN_PHONE, phone);
    }

    public String getUserComments() {
        return _parseObject.getString(COLUMN_USERCOMMENTS);
    }

    public void setUserComments(String userComments) {
        _parseObject.put(COLUMN_USERCOMMENTS, userComments);
    }

    public String getHours() {
        return _parseObject.getString(COLUMN_HOURS);
    }

    public void setHours(String hours) {
        _parseObject.put(COLUMN_HOURS, hours);
    }

    public String getWebsite() {
        return _parseObject.getString(COLUMN_WEBSITE);
    }

    public void setWebsite(String website) {
        _parseObject.put(COLUMN_WEBSITE, website);
    }

    public LatLng getPlacePoint() {
        //Convert from GeoPoint to LatLng
        ParseGeoPoint parseGeoPoint = _parseObject.getParseGeoPoint(COLUMN_PLACEPOINT);
        return new LatLng(parseGeoPoint.getLatitude(), parseGeoPoint.getLongitude());
    }

    public void setPlacePoint(LatLng placePoint) {
        //Convert from LatLng to GeoPoint
        if(placePoint != null) {
            _parseObject.put(COLUMN_PLACEPOINT, new ParseGeoPoint(placePoint.latitude, placePoint.longitude));
        }
    }

    public void setPlacePoint(ParseGeoPoint placePoint) {
        _parseObject.put(COLUMN_PLACEPOINT, placePoint);
    }

    public PlaceUser getPlaceUser() {
        return new PlaceUser(_parseObject.getParseObject(COLUMN_PLACEUSER));
    }

    public void setPlaceUser(PlaceUser placeUser) {
        _parseObject.put(COLUMN_PLACEUSER, placeUser.getParseObject());
    }

    public String getReference() {
        return _reference;
    }

    public void setReference(String reference) {
        this._reference = reference;
    }

    public String getVicinity() {
        return _vicinity;
    }

    public void setVicinity(String vicinity) {
        this._vicinity = vicinity;
    }

    public String getDistance() {
        return _distance;
    }

    public void setDistance(String distance) {
        this._distance = distance;
    }

    public void setDistance(float distance) {
        // convert the distance from meters to miles
        double distanceInMiles = (distance/1000)*0.62137;
        this._distance = String.format("%.2f miles", distanceInMiles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getPlaceName());
        dest.writeString(getAddress());
        dest.writeString(getPhone());
        dest.writeString(getWebsite());
        dest.writeString(getHours());
        dest.writeString(getUserComments());
        dest.writeDouble(getPlacePoint().latitude);
        dest.writeDouble(getPlacePoint().longitude);
    }

     public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
            public Place createFromParcel(Parcel in) {
                return new Place(in);
            }

            public Place[] newArray(int size) {
                return new Place[size];
            }
        };

    /**
     * needed for filtering
     *
     * @return
     */
    public String toString() {
        return getPlaceName();
    }
}
