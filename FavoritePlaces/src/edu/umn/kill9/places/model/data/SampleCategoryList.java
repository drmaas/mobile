package edu.umn.kill9.places.model.data;

import edu.umn.kill9.places.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/13/13
 */
public class SampleCategoryList {

    private static List<Category> categories;

    public static List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<Category>();
            for (int i = 0; i < 8; i++) {
                categories.add(new Category("category"+i));
            }
        }

        return categories;
    }

}
