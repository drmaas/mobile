package com.example.ParseData;

import android.app.Activity;
import android.os.Bundle;
import com.example.ParseData.model.*;
import com.parse.*;

import java.util.List;

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
            /********************LocationUser**************************/
            LocationUserDataSource luds = new LocationUserDataSource();

//            //First Create
//            LocationUser locationUser = luds.createLocationUser("First Test");
//
//            //Second Create
//            LocationUser locationUser2 = new LocationUser("Second Test");
//            luds.createLocationUser(locationUser2);

            //Retrieve
            LocationUser locationUser3 = luds.getLocationUser("1Kp46acn0h");
//
//            //RetrieveAll
//            List<LocationUser> locationUsers = luds.getAllLocationUser();
//
//            //Update
//            locationUser.setHomeAddress("Modified First Test");
//            luds.updateLocationUser(locationUser);
//
//            //First Delete
//            luds.deleteLocationUser(locationUser);
//
//            //Second Delete
//            luds.deleteLocationUser(locationUser2.getId());


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

            /********************Location**************************/
            LocationDataSource lds = new LocationDataSource();

            //First Create
            Location location = lds.createLocation("First Test", "First Test", "First Test", "First Test", "First Test", "First Test", locationUser3);

//            //Second Create
//            Location location2 = new Location("Second Test", "Second Test", "Second Test", "Second Test", "Second Test", "Second Test", locationUser3);
//            lds.createLocation(location2);
//
//            //Retrieve
//            Location location3 = lds.getLocation("qRrHTvXiPg");
//
//            //RetrieveAll
//            List<Location> locations = lds.getAllLocation();
//
//            //Update
//            location.setLocationName("Modified First Test");
//            lds.updateLocation(location);
//
//            //First Delete
//            lds.deleteLocation(location);
//
//            //Second Delete
//            lds.deleteLocation(location2.getId());
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
