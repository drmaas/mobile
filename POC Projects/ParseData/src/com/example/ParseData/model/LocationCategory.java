package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationCategory extends BaseModel  {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_LOCATIONCATEGORY = "LocationCategory";
    public static final String COLUMN_LOCATION = "LocationId";
    public static final String COLUMN_CATEGORY = "CategoryId";

    /***************************Attributes************************************/
    private Location _location;
    private Category _category;

    /**************************Constructors***********************************/
    public LocationCategory(){ }

    public  LocationCategory(Location location, Category category){
        this._location = location;
        this._category = category;
    }

    /*******************Getter and Setter methods*****************************/
    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        this._location = location;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(Category category) {
        this._category = category;
    }

    /***************************Parse methods*********************************/
    public static LocationCategory ParseObjectToLocationCategory(ParseObject parseObject) {
        LocationCategory locationCategory = new LocationCategory();

        //Simple Data
        locationCategory.setId(parseObject.getObjectId());

        //Relational Data
        if(parseObject.getParseObject(COLUMN_LOCATION) != null)
        {
            Location location = Location.ParseObjectToLocation(parseObject.getParseObject(COLUMN_LOCATION));
            locationCategory.setLocation(location);
        }
        if(parseObject.getParseObject(COLUMN_CATEGORY) != null)
        {
            Category category = Category.ParseObjectToCategory(parseObject.getParseObject(COLUMN_CATEGORY));
            locationCategory.setCategory(category);
        }

        return locationCategory;
    }

    public static ParseObject LocationCategoryToParseObject(LocationCategory locationCategory) {
        ParseObject parseObject = new ParseObject(TABLE_LOCATIONCATEGORY);

        //Simple Data

        //Relational Data
        if(locationCategory.getLocation() != null)
        {
            parseObject.put(COLUMN_LOCATION, Location.LocationToParseObject(locationCategory.getLocation()));
        }
        if(locationCategory.getCategory() != null)
        {
            parseObject.put(COLUMN_CATEGORY, Category.CategoryToParseObject(locationCategory.getCategory()));
        }

        //Fill in the ID if it exists (for updates)
        String id = locationCategory.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(locationCategory.getId());
        }

        return parseObject;
    }
}
