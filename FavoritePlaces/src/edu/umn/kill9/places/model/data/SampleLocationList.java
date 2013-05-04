package edu.umn.kill9.places.model.data;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import edu.umn.kill9.places.model.Place;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class SampleLocationList {

    private static List<Place> locations;

    public static List<Place> getLocations() {
        if (locations == null) {
        	createLocations();
        }

        return locations;
    }

    public static List<String> getLocationStrings() {
        if (locations == null) {
        	createLocations();
        }

        List<String> strings = new ArrayList<String>( locations.size() );
        
        for ( Place loc : locations )
        {
        	strings.add( loc.getPlaceName() );
        }
        
        return strings;
    }
    
    // Not sure how or if we want to look up a location based on the name this way or not, but adding here for now
    public static Place findByLocationName(String locationName)
    {
    	Place retLocation = null;
    	
    	for ( Place loc : locations )
    	{
    		if ( loc.getPlaceName().equalsIgnoreCase( locationName ) )
    		{
    			retLocation = loc;
    			break;
    		}
    	}
    	
    	return retLocation;
    }
    
    
    private static void createLocations()
    {
    	Place loc;
        locations = new ArrayList<Place>();
        
        // These were generated manually by looking at the JSON file by using the following address:
        //    http://maps.googleapis.com/maps/api/geocode/json?address=put+address+here,+minneapolis,+mn&sensor=true

        //    http://maps.googleapis.com/maps/api/geocode/json?address=110+1st+ave+ne+minneapolis+mn&sensor=true
        loc = new Place("Kyle's apartment", new LatLng(44.988217, -93.25924099999999));
        loc.setAddress("110 1st Ave NE, Minneapolis, MN");
        locations.add(loc);

        //    http://maps.googleapis.com/maps/api/geocode/json?address=712+Washington+Ave+SE,+Minneapolis,+MN&sensor=true
        loc = new Place("Sally's", new LatLng(44.9736132, -93.2284641));
        loc.setAddress("712 Washington Ave SE, Minneapolis, MN");
        locations.add(loc);

        //    http://maps.googleapis.com/maps/api/geocode/json?address=227+Southeast+Oak+Street,+Minneapolis,+MN&sensor=true
        loc = new Place("Stub and Herb's", new LatLng(44.9738307, -93.22695309999999));
        loc.setAddress("227 Southeast Oak Street, Minneapolis, MN");
        locations.add(loc);

        //    http://maps.googleapis.com/maps/api/geocode/json?address=1301+4th+Street+Southeast,+Minneapolis,+MN&sensor=true
        loc = new Place("The Library Bar", new LatLng(44.981194, -93.23668699999999));
        loc.setAddress("1301 4th Street Southeast, Minneapolis, MN");
        locations.add(loc);

        //    http://maps.googleapis.com/maps/api/geocode/json?address=800+Washington+Ave+SE,+Minneapolis,+MN&sensor=true
        loc = new Place("Chipotle Campus", new LatLng(44.97348059999999, -93.22697430000001));
        loc.setAddress("800 Washington Ave SE, Minneapolis, MN");
        locations.add(loc);

        //    http://maps.googleapis.com/maps/api/geocode/json?address=225+E+Hennepin+Ave,+Minneapolis,+MN&sensor=true
        loc = new Place("Chipotle Home", new LatLng(44.987855, -93.25697400000001));
        loc.setAddress("225 E Hennepin Ave, Minneapolis, MN");
        locations.add(loc);
    }
}
