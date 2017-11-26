package ca.bcit.newwest.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import ca.bcit.newwest.Place;
import ca.bcit.newwest.database.IPlace;

/**
 * PlaceDao class is a data access object class which
 * accesses and modifies data from the Place table
 *
 * @author Jason, Tzu Hsiang Chen
 * @since November 25, 2017
 */

public class PlaceDao extends Dao {
    private static final String TAG = PlaceDao.class.getSimpleName();

    protected PlaceDao(Context context) {
        super(context, IPlace.PLACE_TABLE_NAME);
    }

    /**
     * Insert a place
     * @param place - place object
     */
    public void insert(Place place) {
        ContentValues values = new ContentValues();
        values.put(IPlace.PLACE_NAME_COLUMN, place.getName());
        values.put(IPlace.PLACE_CATEGORY_COLUMN, place.getCategory());
        values.put(IPlace.PLACE_NEIGHBOURHOOD_COLUMN, place.getNeighbourhood());
        values.put(IPlace.PLACE_COORDINATE_X_COLUMN, place.getX());
        values.put(IPlace.PLACE_COORDINATE_Y_COLUMN, place.getY());
        Log.i(TAG, "Inserting " + place.getName());
        super.insert(values);
    }

    /**
     * Update a place
     * @param place - place object
     */
    public void update(Place place) {
        ContentValues values = new ContentValues();
        values.put(IPlace.PLACE_NAME_COLUMN, place.getName());
        values.put(IPlace.PLACE_CATEGORY_COLUMN, place.getCategory());
        values.put(IPlace.PLACE_NEIGHBOURHOOD_COLUMN, place.getNeighbourhood());
        values.put(IPlace.PLACE_COORDINATE_X_COLUMN, place.getX());
        values.put(IPlace.PLACE_COORDINATE_Y_COLUMN, place.getY());
        String[] args = {String.valueOf(place.getId())};
        Log.i(TAG, "Updating " + place.getName());
        super.update(IPlace.PLACE_ID_COLUMN, args, values);
    }

    /**
     * Delete a place
     * @param placeId - place ID
     */
    public void delete(int placeId) {
        String[] args = {String.valueOf(placeId)};
        Log.i(TAG, "Deleting placeId " + placeId);
        super.delete(IPlace.PLACE_ID_COLUMN, args);
    }
}
