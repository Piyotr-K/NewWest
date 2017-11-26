package ca.bcit.newwest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 23, 2017
 */

public class PlaceList {
    private static List<Place> places = new ArrayList<>();

    public static void addPlace(Place place) {
        places.add(place);
    }

    public static List<Place> getPlaces() {
        return places;
    }

    public static Place getPlace(int index) {
        return places.get(index);
    }
}
