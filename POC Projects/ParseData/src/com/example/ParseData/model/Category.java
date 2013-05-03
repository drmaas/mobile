package com.example.ParseData.model;

import com.parse.ParseObject;

/**
 * Created with IntelliJ IDEA.
 * User: cspronk
 * Date: 4/29/13
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Category extends BaseModel {
    /*********************Table and Column Constants**************************/
    public static final String TABLE_CATEGORY = "Category";
    public static final String COLUMN_NAME = "Name";

    /***************************Attributes************************************/
    private String _name;

    /**************************Constructors***********************************/
    public Category(){ }

    public  Category(String name){
        this._name = name;
    }

    /*******************Getter and Setter methods*****************************/
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    /***************************Parse methods*********************************/
    public static Category ParseObjectToCategory(ParseObject parseObject) {
        Category category = new Category();

        //Simple Data
        category.setId(parseObject.getObjectId());
        category.setName(parseObject.getString(COLUMN_NAME));

        //Relational Data

        return category;
    }

    public static ParseObject CategoryToParseObject(Category category) {
        ParseObject parseObject = new ParseObject(TABLE_CATEGORY);

        //Simple Data
        parseObject.put(COLUMN_NAME, category.getName());

        //Relational Data

        //Fill in the ID if it exists (for updates)
        String id = category.getId();
        if(id != null && !id.isEmpty()) {
            parseObject.setObjectId(category.getId());
        }

        return parseObject;
    }
}
