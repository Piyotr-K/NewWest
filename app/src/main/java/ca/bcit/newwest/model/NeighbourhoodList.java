package ca.bcit.newwest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 24, 2017
 */

public class NeighbourhoodList {
    private static List<Neighbourhood> neighbourhoods = new ArrayList<>();

    public static void addNeighbourhood(Neighbourhood neighbourhood) {
        neighbourhoods.add(neighbourhood);
    }

    public static List<Neighbourhood> getNeighbourhoods() {
        return neighbourhoods;
    }

    public static Neighbourhood getNeighbourhood(int index) {
        return neighbourhoods.get(index);
    }

    public static Neighbourhood getNeighbourhood(String name) {
        for (int i = 0; i < neighbourhoods.size(); i++) {
            if (neighbourhoods.get(i).getName().equalsIgnoreCase(name)) {
                return neighbourhoods.get(i);
            }
        }

        return null;
    }
}
