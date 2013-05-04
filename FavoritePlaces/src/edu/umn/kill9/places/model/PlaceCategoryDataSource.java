package edu.umn.kill9.places.model;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 5/3/13
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceCategoryDataSource {
    public PlaceCategory createPlaceCategory(Place place, Category category) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceCategory.TABLE_PLACECATEGORY);

        //Relational Data
        if(place != null)
        {
            parseObject.put(PlaceCategory.COLUMN_PLACE, Place.PlaceToParseObject(place));
        }
        if(category != null)
        {
            parseObject.put(PlaceCategory.COLUMN_CATEGORY, Category.CategoryToParseObject(category));
        }

        parseObject.save();

        return PlaceCategory.ParseObjectToPlaceCategory(parseObject);
    }

    public PlaceCategory createPlaceCategory(PlaceCategory placeCategory) throws ParseException {
        ParseObject parseObject = PlaceCategory.PlaceCategoryToParseObject(placeCategory);
        parseObject.save();

        placeCategory.setId(parseObject.getObjectId());
        return placeCategory;
    }

    public PlaceCategory getPlaceCategory(String id) throws ParseException {
        ParseQuery query = new ParseQuery(PlaceCategory.TABLE_PLACECATEGORY);

        PlaceCategory placeCategory = PlaceCategory.ParseObjectToPlaceCategory(query.get(id));

        return placeCategory;
    }

    public List<PlaceCategory> getAllPlaceCategory() throws ParseException {
        List<PlaceCategory> placeCategories = new ArrayList<PlaceCategory>();

        ParseQuery query = new ParseQuery(PlaceCategory.TABLE_PLACECATEGORY);

        for (ParseObject parseObject : query.find()) {
            placeCategories.add(PlaceCategory.ParseObjectToPlaceCategory(parseObject));
        }

        return placeCategories;
    }

    public void updatePlaceCategory(PlaceCategory placeCategory) throws ParseException {
        ParseObject parseObject = PlaceCategory.PlaceCategoryToParseObject(placeCategory);
        parseObject.save();
    }

    public void deletePlaceCategory(PlaceCategory placeCategory) throws ParseException {
        ParseObject parseObject = PlaceCategory.PlaceCategoryToParseObject(placeCategory);
        parseObject.delete();
    }

    public void deletePlaceCategory(String placeCategoryId) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceCategory.TABLE_PLACECATEGORY);
        parseObject.setObjectId(placeCategoryId);
        parseObject.delete();
    }
}
