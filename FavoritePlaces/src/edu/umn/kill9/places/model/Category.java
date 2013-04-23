package edu.umn.kill9.places.model;

/**
 * User: drmaas
 * Date: 4/13/13
 */
public class Category {

    private long _id;
    private String _categoryName;

    //default id is -1, which means its not set yet
    public Category(String _categoryName) {
        this(new Long(-1), _categoryName);
    }

    public Category(Long id, String categoryName) {
        this._id = id;
        this._categoryName = categoryName;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String getCategoryName() {
        return _categoryName;
    }

    public void setCategoryName(String categoryName) {
        this._categoryName = categoryName;
    }
}
