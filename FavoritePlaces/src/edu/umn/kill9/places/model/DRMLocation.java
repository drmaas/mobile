package edu.umn.kill9.places.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class DRMLocation implements Parcelable{

    private long _id;
    private String locationName;
    private String address;
    private String phone;
    private String website;
    private String hours;
    private String userComments; //users can add their own comments or notes
    private LatLng locationPoint;

    //transient data
    private String reference;
    private String vicinity;
    private String distance;

    public DRMLocation() {
        this("",null);
    }

    //default id is -1, which means its not set yet
    public DRMLocation(String locationName, LatLng locationPoint) {
        this(Long.valueOf(-1), locationName, locationPoint);
    }

    public DRMLocation(Long id, String locationName, LatLng locationPoint) {
        this._id = id;
        this.locationName = locationName;
        this.locationPoint = locationPoint;
    }

    private DRMLocation(Parcel in) {
    	_id = in.readLong();
    	locationName = in.readString();
    	address = in.readString();
    	phone = in.readString();
    	website = in.readString();
    	hours = in.readString();
    	userComments = in.readString();
    	locationPoint = new LatLng(in.readDouble(), in.readDouble());
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LatLng getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(LatLng locationPoint) {
        this.locationPoint = locationPoint;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }


    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDistance(float distance) {
        // convert the distance from meters to miles
        double distanceInMiles = (distance/1000)*0.62137;
        this.distance = String.format("%.2f miles", distanceInMiles);
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(_id);
		dest.writeString(locationName);
		dest.writeString(address);
		dest.writeString(phone);
		dest.writeString(website);
		dest.writeString(hours);
		dest.writeString(userComments);
		dest.writeDouble(locationPoint.latitude);
		dest.writeDouble(locationPoint.longitude);
	}
	
	 public static final Parcelable.Creator<DRMLocation> CREATOR = new Parcelable.Creator<DRMLocation>() {
	        public DRMLocation createFromParcel(Parcel in) {
	            return new DRMLocation(in);
	        }

	        public DRMLocation[] newArray(int size) {
	            return new DRMLocation[size];
	        }
	    };

    /**
     * needed for filtering
     *
     * @return
     */
    public String toString() {
        return locationName + " " + address;
    }

}
