package edu.umn.kill9.places.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceCategory extends BaseModel  {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACECATEGORY = "PlaceCategory";
    public static final String COLUMN_PLACE = "PlaceId";
    public static final String COLUMN_CATEGORY = "CategoryId";

    /**************************Constructors***********************************/
    public PlaceCategory(){
        super(TABLE_PLACECATEGORY);
    }

    public PlaceCategory(Place place, Category category){
        super(TABLE_PLACECATEGORY);
        setPlace(place);
        setCategory(category);
    }

    public  PlaceCategory(ParseObject parseObject){
        setParseObject(parseObject);
    }

    /*******************Getter and Setter methods*****************************/
    public Place getPlace() {
        return new Place(_parseObject.getParseObject(COLUMN_PLACE));
    }

    public void setPlace(Place place) {
        _parseObject.put(COLUMN_PLACE, place.getParseObject());
    }

    public Category getCategory() {
        return new Category(_parseObject.getParseObject(COLUMN_CATEGORY));
    }

    public void setCategory(Category category) {
        _parseObject.put(COLUMN_CATEGORY, category.getParseObject());
    }
}
