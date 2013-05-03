package com.example.ParseData.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/1/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationCategoryDataSource {
    public LocationCategory createLocationCategory(Location location, Category category) throws ParseException {
        ParseObject parseObject = new ParseObject(LocationCategory.TABLE_LOCATIONCATEGORY);

        //Relational Data
        if(location != null)
        {
            parseObject.put(LocationCategory.COLUMN_LOCATION, Location.LocationToParseObject(location));
        }
        if(category != null)
        {
            parseObject.put(LocationCategory.COLUMN_CATEGORY, Category.CategoryToParseObject(category));
        }

        parseObject.save();

        return LocationCategory.ParseObjectToLocationCategory(parseObject);
    }

    public LocationCategory createLocationCategory(LocationCategory locationCategory) throws ParseException {
        ParseObject parseObject = LocationCategory.LocationCategoryToParseObject(locationCategory);
        parseObject.save();

        locationCategory.setId(parseObject.getObjectId());
        return locationCategory;
    }

    public LocationCategory getLocationCategory(String id) throws ParseException {
        ParseQuery query = new ParseQuery(LocationCategory.TABLE_LOCATIONCATEGORY);

        LocationCategory locationCategory = LocationCategory.ParseObjectToLocationCategory(query.get(id));

        return locationCategory;
    }

    public List<LocationCategory> getAllLocationCategory() throws ParseException {
        List<LocationCategory> locationCategories = new ArrayList<LocationCategory>();

        ParseQuery query = new ParseQuery(LocationCategory.TABLE_LOCATIONCATEGORY);

        for (ParseObject parseObject : query.find()) {
            locationCategories.add(LocationCategory.ParseObjectToLocationCategory(parseObject));
        }

        return locationCategories;
    }

    public void updateLocationCategory(LocationCategory locationCategory) throws ParseException {
        ParseObject parseObject = LocationCategory.LocationCategoryToParseObject(locationCategory);
        parseObject.save();
    }

    public void deleteLocationCategory(LocationCategory locationCategory) throws ParseException {
        ParseObject parseObject = LocationCategory.LocationCategoryToParseObject(locationCategory);
        parseObject.delete();
    }

    public void deleteLocationCategory(String locationCategoryId) throws ParseException {
        ParseObject parseObject = new ParseObject(LocationCategory.TABLE_LOCATIONCATEGORY);
        parseObject.setObjectId(locationCategoryId);
        parseObject.delete();
    }
}
