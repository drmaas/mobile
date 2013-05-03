package com.example.ParseData.model;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseModel {

    protected String _id;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
}
