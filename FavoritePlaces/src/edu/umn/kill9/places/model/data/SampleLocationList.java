package edu.umn.kill9.places.model.data;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

import edu.umn.kill9.places.model.DRMLocation;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class SampleLocationList {

    private static List<DRMLocation> locations;

    public static List<DRMLocation> getLocations() {
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
        
        for ( DRMLocation loc : locations )
        {
        	strings.add( loc.getLocationName() );
        }
        
        return strings;
    }
    
    // Not sure how or if we want to look up a location based on the name this way or not, but adding here for now
    public static DRMLocation findByLocationName(String locationName)
    {
    	DRMLocation retLocation = null;
    	
    	for ( DRMLocation loc : locations )
    	{
    		if ( loc.getLocationName().equalsIgnoreCase( locationName ) )
    		{
    			retLocation = loc;
    			break;
    		}
    	}
    	
    	return retLocation;
    }
    
    
    private static void createLocations()
    {
        locations = new ArrayList<DRMLocation>();
        
        // These were generated manually by looking at the JSON file by using the following address:
        //    http://maps.googleapis.com/maps/api/geocode/json?address=put+address+here,+minneapolis,+mn&sensor=true

        //    http://maps.googleapis.com/maps/api/geocode/json?address=110+1st+ave+ne+minneapolis+mn&sensor=true
        locations.add(new DRMLocation("Kyle's apartment", new LatLng(44.988217, -93.25924099999999)));

        //    http://maps.googleapis.com/maps/api/geocode/json?address=712+Washington+Ave+SE,+Minneapolis,+MN&sensor=true
        locations.add(new DRMLocation("Sally's", new LatLng(44.9736132, -93.2284641)));

        //    http://maps.googleapis.com/maps/api/geocode/json?address=227+Southeast+Oak+Street,+Minneapolis,+MN&sensor=true
        locations.add(new DRMLocation("Stub and Herb's", new LatLng(44.9738307, -93.22695309999999)));

        //    http://maps.googleapis.com/maps/api/geocode/json?address=1301+4th+Street+Southeast,+Minneapolis,+MN&sensor=true
        locations.add(new DRMLocation("The Library Bar", new LatLng(44.981194, -93.23668699999999)));

        //    http://maps.googleapis.com/maps/api/geocode/json?address=800+Washington+Ave+SE,+Minneapolis,+MN&sensor=true
        locations.add(new DRMLocation("Chipotle Campus", new LatLng(44.97348059999999, -93.22697430000001)));

        //    http://maps.googleapis.com/maps/api/geocode/json?address=225+E+Hennepin+Ave,+Minneapolis,+MN&sensor=true
        locations.add(new DRMLocation("Chipotle Home", new LatLng(44.987855, -93.25697400000001)));
    }
}
