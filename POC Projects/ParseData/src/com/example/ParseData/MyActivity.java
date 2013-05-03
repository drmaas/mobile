package com.example.ParseData;

import android.app.Activity;
import android.os.Bundle;
import com.example.ParseData.model.*;
import com.parse.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Parse.initialize(this, "r84BaA4aJmPGFtx3wqYlfduv6InfkbX28MDvGUNu", "ZDG9HP5hagZryeaMZqwSPWlnBlB4R5whol2pXv67");

        ParseAnalytics.trackAppOpened(getIntent());

//        ParseGeoPoint point = new ParseGeoPoint(20, 14);
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("test", "yup");
//        testObject.put("coordinates", point);
//        //testObject.put("foo", "bar");
//        testObject.saveInBackground();




        try {
            /********************PlaceUser**************************/
            PlaceUserDataSource luds = new PlaceUserDataSource();

//            //First Create
//            PlaceUser locationUser = luds.createPlaceUser("First Test");
//
//            //Second Create
//            PlaceUser locationUser2 = new PlaceUser("Second Test");
//            luds.createPlaceUser(locationUser2);

            //Retrieve
            PlaceUser placeUser3 = luds.getPlaceUser("1Kp46acn0h");
//
//            //RetrieveAll
//            List<PlaceUser> locationUsers = luds.getAllPlaceUser();
//
//            //Update
//            locationUser.setHomeAddress("Modified First Test");
//            luds.updatePlaceUser(locationUser);
//
//            //First Delete
//            luds.deletePlaceUser(locationUser);
//
//            //Second Delete
//            luds.deletePlaceUser(locationUser2.getId());


//            /********************Category**************************/
//            CategoryDataSource cds = new CategoryDataSource();
//
//            //First Create
//            Category category = cds.createCategory("First Test");
//
//            //Second Create
//            Category category2 = new Category("Second Test");
//            cds.createCategory(category2);
//
//            //Retrieve
//            Category category3 = cds.getCategory("rzmpQARw9j");
//
//            //RetrieveAll
//            List<Category> categories = cds.getAllCategory();
//
//            //Update
//            category.setName("Modified First Test");
//            cds.updateCategory(category);
//
//            //First Delete
//            cds.deleteCategory(category);
//
//            //Second Delete
//            cds.deleteCategory(category2.getId());

            /********************Place**************************/
            PlaceDataSource lds = new PlaceDataSource();

            //First Create
            Place place = lds.createPlace("First Test", "First Test", "First Test", "First Test", "First Test", "First Test", placeUser3);

//            //Second Create
//            Place location2 = new Place("Second Test", "Second Test", "Second Test", "Second Test", "Second Test", "Second Test", placeUser3);
//            lds.createPlace(location2);
//
//            //Retrieve
//            Place location3 = lds.getPlace("qRrHTvXiPg");
//
//            //RetrieveAll
//            List<Place> locations = lds.getAllPlace();
//
//            //Update
//            place.setPlaceName("Modified First Test");
//            lds.updatePlace(place);
//
//            //First Delete
//            lds.deletePlace(place);
//
//            //Second Delete
//            lds.deletePlace(location2.getId());
//
//
//            /********************Event**************************/
//            EventDataSource eds = new EventDataSource();
//
//            //First Create
//
//            Event event = eds.createEvent("First Test");
//
//            //Second Create
//            Event event2 = new Event();
//            eds.createEvent(event2);
//
//            //Retrieve
//            Event event3 = eds.getEvent("rzmpQARw9j");
//
//            //RetrieveAll
//            List<Event> events = eds.getAllEvent();
//
//            //Update
//            category.setName("Modified First Test");
//            eds.updateEvent(event);
//
//            //First Delete
//            eds.deleteEvent(event);
//
//            //Second Delete
//            eds.deleteEvent(event2.getId());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
