package edu.umn.kill9.places.model;

import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/8/13
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseModel {

    protected ParseObject _parseObject;

    public BaseModel() { }

    public BaseModel(String theClassName) {
        _parseObject = new ParseObject(theClassName);
    }

    public ParseObject getParseObject() {
        return _parseObject;
    }

    public void setParseObject(ParseObject parseObject) {
        _parseObject = parseObject;
    }

    public String getId() {
        return _parseObject.getObjectId();
    }

    public void setId(String id) {
        _parseObject.setObjectId(id);
    }

    public void save() throws ParseException {
        _parseObject.save();
    }

    public void delete() throws ParseException {
        _parseObject.delete();
    }
}
