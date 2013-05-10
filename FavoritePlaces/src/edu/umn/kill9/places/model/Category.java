package edu.umn.kill9.places.model;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * User: drmaas
 * Date: 4/13/13
 */
public class Category extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_CATEGORY = "Category";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PLACEUSER = "UserId";

    /**************************Constructors***********************************/
    public Category(){
        super(TABLE_CATEGORY);
    }

    public  Category(String name, PlaceUser placeUser){
        super(TABLE_CATEGORY);
        setName(name);
        setPlaceUser(placeUser);
    }

    public  Category(ParseObject parseObject){
        setParseObject(parseObject);
    }

    /*******************Getter and Setter methods*****************************/
    public String getName() {
        return _parseObject.getString(COLUMN_NAME);
    }

    public void setName(String name) {
        _parseObject.put(COLUMN_NAME, name);
    }

    public PlaceUser getPlaceUser() {
        return new PlaceUser(_parseObject.getParseObject(COLUMN_PLACEUSER));
    }

    public void setPlaceUser(PlaceUser placeUser) {
        _parseObject.put(COLUMN_PLACEUSER, placeUser.getParseObject());
    }
}
