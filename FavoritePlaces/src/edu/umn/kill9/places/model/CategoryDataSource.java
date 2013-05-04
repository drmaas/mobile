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
    public Category createCategory(String name) throws ParseException {
        ParseObject parseObject = new ParseObject(Category.TABLE_CATEGORY);
        parseObject.put(Category.COLUMN_NAME, name);

        parseObject.save();

        return Category.ParseObjectToCategory(parseObject);
    }

    public Category createCategory(Category category) throws ParseException {
        ParseObject parseObject = Category.CategoryToParseObject(category);
        parseObject.save();

        category.setId(parseObject.getObjectId());
        return category;
    }

    public Category getCategory(String id) throws ParseException {
        ParseQuery query = new ParseQuery(Category.TABLE_CATEGORY);

        Category category = Category.ParseObjectToCategory(query.get(id));

        return category;
    }

    public List<Category> getAllCategory() throws ParseException {
        List<Category> categorys = new ArrayList<Category>();

        ParseQuery query = new ParseQuery(Category.TABLE_CATEGORY);

        for (ParseObject parseObject : query.find()) {
            categorys.add(Category.ParseObjectToCategory(parseObject));
        }

        return categorys;
    }

    public void updateCategory(Category category) throws ParseException {
        ParseObject parseObject = Category.CategoryToParseObject(category);
        parseObject.save();
    }

    public void deleteCategory(Category category) throws ParseException {
        ParseObject parseObject = Category.CategoryToParseObject(category);
        parseObject.delete();
    }

    public void deleteCategory(String categoryId) throws ParseException {
        ParseObject parseObject = new ParseObject(Category.TABLE_CATEGORY);
        parseObject.setObjectId(categoryId);
        parseObject.delete();
    }
}
