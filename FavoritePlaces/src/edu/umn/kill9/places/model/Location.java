package edu.umn.kill9.places.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class Location {

    private long _id;
    private String locationName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String note;
    private LatLng locationPoint;

    //default id is -1, which means its not set yet
    public Location(String locationName, LatLng locationPoint) {
        this(Long.valueOf(-1), locationName, locationPoint);
    }

    public Location(Long id, String locationName, LatLng locationPoint) {
        this._id = id;
        this.locationName = locationName;
        this.locationPoint = locationPoint;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
