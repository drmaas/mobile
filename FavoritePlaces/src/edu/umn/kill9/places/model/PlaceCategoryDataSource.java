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
        PlaceCategory placeCategory = new PlaceCategory();

        //Relational Data
        if(place != null)
        {
            placeCategory.setPlace(place);
        }
        if(category != null)
        {
            placeCategory.setCategory(category);
        }

        placeCategory.save();

        return placeCategory;
    }

    public PlaceCategory createPlaceCategory(PlaceCategory placeCategory) throws ParseException {
        placeCategory.save();
        return placeCategory;
    }

    public PlaceCategory getPlaceCategory(String id) throws ParseException {
        ParseQuery query = new ParseQuery(PlaceCategory.TABLE_PLACECATEGORY);

        return new PlaceCategory(query.get(id));
    }

    public List<PlaceCategory> getAllPlaceCategory() throws ParseException {
        List<PlaceCategory> placeCategories = new ArrayList<PlaceCategory>();

        ParseQuery query = new ParseQuery(PlaceCategory.TABLE_PLACECATEGORY);

        for (ParseObject parseObject : query.find()) {
            placeCategories.add(new PlaceCategory(parseObject));
        }

        return placeCategories;
    }

    public void updatePlaceCategory(PlaceCategory placeCategory) throws ParseException {
        placeCategory.save();
    }

    public void deletePlaceCategory(PlaceCategory placeCategory) throws ParseException {
        placeCategory.delete();
    }

    public void deletePlaceCategory(String placeCategoryId) throws ParseException {
        ParseObject parseObject = new ParseObject(PlaceCategory.TABLE_PLACECATEGORY);
        parseObject.setObjectId(placeCategoryId);
        parseObject.delete();
    }
}
