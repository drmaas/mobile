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
public class Place implements Parcelable{
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
    private String _id;
    private String _placeName;
    private String _address;
    private String _phone;
    private String _website;
    private String _hours;
    private String _userComments; //users can add their own comments or notes
    private LatLng _placePoint;
    private PlaceUser _placeUser;

    //transient data
    private String _reference;
    private String _vicinity;
    private String _distance;

    /**************************Constructors***********************************/
    public Place() {
        this("",null);
        this._address = "n/a";
        this._phone = "n/a";
        this._website = "n/a";
        this._hours = "n/a";
        this._userComments = "n/a";
        this._placeUser = null;
    }

    public Place(String placeName, String address, String phone, String website, String hours, String userComments, LatLng placePoint, PlaceUser placeUser){
        this._placeName = placeName;
        this._address = address;
        this._phone = phone;
        this._website = website;
        this._hours = hours;
        this._userComments = userComments;
        this._placePoint = placePoint;
        this._placeUser = placeUser;
    }

    public Place(String placeName, LatLng placePoint) {
        this._placeName = placeName;
        this._placePoint = placePoint;
    }

    private Place(Parcel in) {
        this._id = in.readString();
        this._placeName = in.readString();
        this._address = in.readString();
        this._phone = in.readString();
        this._website = in.readString();
        this._hours = in.readString();
        this._userComments = in.readString();
        this._placePoint = new LatLng(in.readDouble(), in.readDouble());
    }

    /*******************Getter and Setter methods*****************************/
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

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

    public String getUserComments() {
        return _userComments;
    }

    public void setUserComments(String userComments) {
        this._userComments = userComments;
    }

    public String getHours() {
        return _hours;
    }

    public void setHours(String hours) {
        this._hours = hours;
    }

    public String getWebsite() {
        return _website;
    }

    public void setWebsite(String website) {
        this._website = website;
    }

    public LatLng getPlacePoint() {
        return _placePoint;
    }

    public void setPlacePoint(LatLng placePoint) {
        this._placePoint = placePoint;
    }

    public PlaceUser getPlaceUser() {
        return _placeUser;
    }

    public void setPlaceUser(PlaceUser placeUser) {
        this._placeUser = placeUser;
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
        dest.writeString(_id);
        dest.writeString(_placeName);
        dest.writeString(_address);
        dest.writeString(_phone);
        dest.writeString(_website);
        dest.writeString(_hours);
        dest.writeString(_userComments);
        dest.writeDouble(_placePoint.latitude);
        dest.writeDouble(_placePoint.longitude);
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
        return _placeName;
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

        //Convert ParseGeoPoint to LatLng
        ParseGeoPoint geoPoint = parseObject.getParseGeoPoint(COLUMN_PLACEPOINT);
        place.setPlacePoint(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()));

        //Relational Data
        PlaceUser placeUser = null;
        if(parseObject.getParseObject(COLUMN_PLACEUSER) != null)
        {
            try {
                placeUser = PlaceUser.ParseObjectToPlaceUser(parseObject.getParseObject(COLUMN_PLACEUSER).fetchIfNeeded());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

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

        //Convert LatLng to ParseGeoPoint
        LatLng placePoint = place.getPlacePoint();
        parseObject.put(COLUMN_PLACEPOINT, new ParseGeoPoint(placePoint.latitude, placePoint.longitude));

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
