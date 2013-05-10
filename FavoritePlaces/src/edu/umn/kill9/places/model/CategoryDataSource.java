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
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class CategoryDataSource {

    public Category createCategory(String name, PlaceUser placeUser) throws ParseException {
        Category category = new Category();
        category.setName(name);
        category.setPlaceUser(placeUser);

        category.save();

        return category;
    }

    public Category createCategory(Category category) throws ParseException {
        category.save();
        return category;
    }

    public Category getCategory(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Category.TABLE_CATEGORY);
        return new Category(query.get(id));
    }

    public List<Category> getAllCategory() throws ParseException {
        List<Category> categories = new ArrayList<Category>();

        ParseQuery query = new ParseQuery(Category.TABLE_CATEGORY);

        for (ParseObject parseObject : query.find()) {
            categories.add(new Category(parseObject));
        }

        return categories;
    }

    public List<Category> getAllUserCategories(PlaceUser user) throws ParseException {
        List<Category> categories = new ArrayList<Category>();

        ParseQuery query = new ParseQuery(Category.TABLE_CATEGORY).whereEqualTo(Place.COLUMN_PLACEUSER, user.getParseObject());;

        for (ParseObject parseObject : query.find()) {
            categories.add(new Category(parseObject));
        }

        return categories;
    }

    public void updateCategory(Category category) throws ParseException {
        category.save();
    }

    public void deleteCategory(Category category) throws ParseException {
        category.delete();
    }

    public void deleteCategory(String categoryId) throws ParseException {
        ParseObject parseObject = new ParseObject(Category.TABLE_CATEGORY);
        parseObject.setObjectId(categoryId);
        parseObject.delete();
    }
}
