package edu.umn.kill9.places.model;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class Location {

    private long _id;
    private String _locationName;

    //default id is -1, which means its not set yet
    public Location(String _categoryName) {
        this(new Long(-1), _categoryName);
    }

    public Location(Long id, String locationName) {
        this._id = id;
        this._locationName = locationName;
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
}
