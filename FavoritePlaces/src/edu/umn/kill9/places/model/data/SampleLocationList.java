package edu.umn.kill9.places.model.data;

import edu.umn.kill9.places.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * User: drmaas
 * Date: 4/17/13
 */
public class SampleLocationList {

    private static List<String> locations;

    public static List<String> getLocations() {
        if (locations == null) {
            locations = new ArrayList<String>();
            for (int i = 0; i < 8; i++) {
                locations.add("location"+i);
            }
        }

        return locations;
    }
}
