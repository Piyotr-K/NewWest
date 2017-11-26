package ca.bcit.newwest.database;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 25, 2017
 */

public interface IPlace {
    // place table name
    String PLACE_TABLE_NAME = "Place";

    // place column names
    String PLACE_ID_COLUMN = "placeId";
    String PLACE_NAME_COLUMN = "name";
    String PLACE_CATEGORY_COLUMN = "category";
    String PLACE_NEIGHBOURHOOD_COLUMN = "neighbourhood";
    String PLACE_COORDINATE_X_COLUMN = "coordinateX";
    String PLACE_COORDINATE_Y_COLUMN = "coordinateY";

    // create place table
    String CREATE_PLACE_TABLE = "CREATE TABLE " + PLACE_TABLE_NAME + "("
            + PLACE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLACE_NAME_COLUMN + " TEXT, "
            + PLACE_CATEGORY_COLUMN + " TEXT, "
            + PLACE_NEIGHBOURHOOD_COLUMN + " TEXT, "
            + PLACE_COORDINATE_X_COLUMN + " REAL, "
            + PLACE_COORDINATE_Y_COLUMN + " REAL);";

    // drop place table
    String DROP_PLACE_TABLE = "DROP IF EXISTS " + PLACE_TABLE_NAME;
}
