package edu.umn.kill9.places.model;

/**
 * User: drmaas
 * Date: 4/24/13
 */
public class Event {

    private long _id;

    public Event(long _id) {
        this._id = _id;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }
}
