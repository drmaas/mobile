package edu.umn.kill9.places.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class Location {

    private long _id;
    private String _locationName;
    private LatLng _locationPoint; // We may want to switch to address?

    //default id is -1, which means its not set yet
    public Location(String locationName, LatLng locationPoint) {
        this(Long.valueOf(-1), locationName, locationPoint);
    }

    public Location(Long id, String locationName, LatLng locationPoint) {
        this._id = id;
        this._locationName = locationName;
        this._locationPoint = locationPoint;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getLocationName() {
        return _locationName;
    }

    public void setLocationName(String locationName) {
        this._locationName = locationName;
    }

    public LatLng getLocationPoint() {
        return _locationPoint;
    }

    public void setLocationPoint(LatLng locationPoint) {
        this._locationPoint = locationPoint;
    }
}
