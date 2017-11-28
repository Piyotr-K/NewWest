package ca.bcit.newwest.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.bcit.newwest.database.IPlace;
import ca.bcit.newwest.model.Place;

/**
 * PlaceDao class is a data access object class which
 * accesses and modifies data from the Place table
 *
 * @author Jason, Tzu Hsiang Chen
 * @since November 25, 2017
 */

public class PlaceDao extends Dao {
    private static final String TAG = PlaceDao.class.getSimpleName();

    public PlaceDao(Context context) {
        super(context, IPlace.PLACE_TABLE_NAME);
    }

    /**
     * Insert a place
     * @param place - place object
     */
    public void insert(Place place) {
        ContentValues values = new ContentValues();
        values.put(IPlace.PLACE_OBJECT_ID_COLUMN, place.getObjectId());
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
        values.put(IPlace.PLACE_OBJECT_ID_COLUMN, place.getObjectId());
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

    /**
     * Insert or update a place
     * @param place - place object
     */
    public void insertOrUpdate(Place place) {
        ContentValues values = new ContentValues();
        values.put(IPlace.PLACE_NAME_COLUMN, place.getName());
        values.put(IPlace.PLACE_CATEGORY_COLUMN, place.getCategory());
        values.put(IPlace.PLACE_NEIGHBOURHOOD_COLUMN, place.getNeighbourhood());
        values.put(IPlace.PLACE_COORDINATE_X_COLUMN, place.getX());
        values.put(IPlace.PLACE_COORDINATE_Y_COLUMN, place.getY());
        String[] args = {String.valueOf(place.getObjectId())};
        boolean success = super.update(IPlace.PLACE_OBJECT_ID_COLUMN, args, values);
        if (!success) {
            values.put(IPlace.PLACE_OBJECT_ID_COLUMN, place.getObjectId());
            super.insert(values);
        }
    }

    public List<Place> findAllPlaces() {
        List<Place> places = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + IPlace.PLACE_TABLE_NAME + ";", null);
            int count = cursor.getCount();
            Log.d(TAG, "Found places " + count + " rows");
            if (count > 0 && cursor.moveToFirst()) {
                places = new ArrayList<>(count);
                do {
                    Place place = new Place();
                    place.setId(cursor.getInt(0));
                    place.setObjectId(cursor.getInt(1));
                    place.setName(cursor.getString(2));
                    place.setCategory(cursor.getString(3));
                    place.setNeighbourhood(cursor.getString(4));
                    place.setX(cursor.getDouble(5));
                    place.setY(cursor.getDouble(6));
                    places.add(place);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }

        return places;
    }

    public List<String> findCategoryByNeigh(String name) {
        List<String> categories = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT DISTINCT " + IPlace.PLACE_CATEGORY_COLUMN + " FROM " + IPlace.PLACE_TABLE_NAME
                    + " WHERE " + IPlace.PLACE_NEIGHBOURHOOD_COLUMN + " = '" + name + "';", null);
            int count = cursor.getCount();
            Log.d(TAG, "Found places " + count + " rows");
            if (count > 0 && cursor.moveToFirst()) {
                categories = new ArrayList<>(count);
                do {
                    categories.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }

        return categories;
    }

    public List<Place> findPlacesByNeigh(String name) {
        List<Place> places = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " + IPlace.PLACE_TABLE_NAME
                    + " WHERE " + IPlace.PLACE_NEIGHBOURHOOD_COLUMN + " = '" + name + "';", null);
            int count = cursor.getCount();
            Log.d(TAG, "Found places " + count + " rows");
            if (count > 0 && cursor.moveToFirst()) {
                places = new ArrayList<>(count);
                do {
                    Place place = new Place();
                    place.setId(cursor.getInt(0));
                    place.setObjectId(cursor.getInt(1));
                    place.setName(cursor.getString(2));
                    place.setCategory(cursor.getString(3));
                    place.setNeighbourhood(cursor.getString(4));
                    place.setX(cursor.getDouble(5));
                    place.setY(cursor.getDouble(6));
                    places.add(place);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }

        return places;
    }
}
