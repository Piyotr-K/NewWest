package ca.bcit.newwest;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 23, 2017
 */

public class Place {
    private String mName;
    private String mLocation;
    private String mPostalCode;
    private double mX;
    private double mY;

    public Place(String name, double x, double y) {
        this.mName = name;
        this.mX = x;
        this.mY = y;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        this.mPostalCode = postalCode;
    }

    public double getX() {
        return mX;
    }

    public void setX(double x) {
        this.mX = x;
    }

    public double getY() {
        return mY;
    }

    public void setY(double y) {
        this.mY = y;
    }
}
