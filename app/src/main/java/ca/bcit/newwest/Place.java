package ca.bcit.newwest;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 23, 2017
 */

public class Place {
    private int mId;
    private String mName;
    private String mCategory;
    private String mNeighbourhood;
    private double mX;
    private double mY;

    /**
     * Default Constructor
     */
    public Place() {
        this.mId = 0;
        this.mName = "";
        this.mCategory = "";
        this.mNeighbourhood = "";
        this.mX = 0.0;
        this.mY = 0.0;
    }

    /**
     * Construct a place with name, category, neighbourhood, x, and y
     * @param name - name of the place
     * @param category - category of the place
     * @param neighbourhood - neighbourhood of the place
     * @param x - coordinate x
     * @param y - coordinate y
     */
    public Place(String name, String category, String neighbourhood, double x, double y) {
        this.mName = name;
        this.mCategory = category;
        this.mNeighbourhood = neighbourhood;
        this.mX = x;
        this.mY = y;
    }

    /**
     * Get place Id
     *
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * Set place Id
     * @param id
     */
    public void setId(int id) {
        this.mId = id;
    }

    /**
     * Get place name
     * @return name of the place
     */
    public String getName() {
        return mName;
    }

    /**
     * Set place name
     * @param name - name of the place
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Get place Category
     * @return category
     */
    public String getCategory() {
        return mCategory;
    }

    /**
     * Set place Category
     * @param category - name of the category
     */
    public void setCategory(String category) {
        this.mCategory = category;
    }
      
    /**
     * Get neighbourhood of the place
     * @return name of the neighbourhood
     */
    public String getNeighbourhood() {
        return mNeighbourhood;
    }

    /**
     * Set neighbourhood of the place
     * @param neighbourhood - name of the neighbourhood
     */
    public void setNeighbourhood(String neighbourhood) {
        this.mNeighbourhood = neighbourhood;
    }

    /**
     * Get coordinate X of the place
     * @return coordinate X
     */
    public double getX() {
        return mX;
    }

    /**
     * Set coordinate X of the place
     * @param x - coordinate X
     */
    public void setX(double x) {
        this.mX = x;
    }

    /**
     * Get coordinate Y of the place
     * @return coordinate Y
     */
    public double getY() {
        return mY;
    }

    /**
     * Set coordinate Y of the place
     * @param y - coordinate Y
     */
    public void setY(double y) {
        this.mY = y;
    }
}
