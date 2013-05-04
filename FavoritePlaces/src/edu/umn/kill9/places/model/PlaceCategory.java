package edu.umn.kill9.places.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceCategory {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_PLACECATEGORY = "PlaceCategory";
    public static final String COLUMN_PLACE = "PlaceId";
    public static final String COLUMN_CATEGORY = "CategoryId";

    /***************************Attributes************************************/
    private String _id;
    private Place _place;
    private Category _category;

    /**************************Constructors***********************************/
    public PlaceCategory(){ }

    public PlaceCategory(Place place, Category category){
        this._place = place;
        this._category = category;
    }

    /*******************Getter and Setter methods*****************************/
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Place getPlace() {
        return _place;
    }

    public void setPlace(Place place) {
        this._place = place;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(Category category) {
        this._category = category;
    }

    /***************************Parse methods*********************************/
    public static PlaceCategory ParseObjectToPlaceCategory(ParseObject parseObject) {
        PlaceCategory placeCategory = new PlaceCategory();

        //Simple Data
        placeCategory.setId(parseObject.getObjectId());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_PLACE) != null)
        {
            Place place = Place.ParseObjectToPlace(parseObject.getParseObject(COLUMN_PLACE));
            placeCategory.setPlace(place);
        }
        if(parseObject.getParseObject(COLUMN_CATEGORY) != null)
        {
            Category category = Category.ParseObjectToCategory(parseObject.getParseObject(COLUMN_CATEGORY));
            placeCategory.setCategory(category);
        }

        return placeCategory;
    }

    public static ParseObject PlaceCategoryToParseObject(PlaceCategory placeCategory) {
        ParseObject parseObject = new ParseObject(TABLE_PLACECATEGORY);

        //Simple Data

        //Relational Data
        if(placeCategory.getPlace() != null)
        {
            parseObject.put(COLUMN_PLACE, Place.PlaceToParseObject(placeCategory.getPlace()));
        }
        if(placeCategory.getCategory() != null)
        {
            parseObject.put(COLUMN_CATEGORY, Category.CategoryToParseObject(placeCategory.getCategory()));
        }

        //Fill in the ID if it exists (for updates)
        String id = placeCategory.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(placeCategory.getId());
        }

        return parseObject;
    }
}
