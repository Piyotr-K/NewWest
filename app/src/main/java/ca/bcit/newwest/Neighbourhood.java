package ca.bcit.newwest;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason, Tzu Hsiang Chen
 * @since November 24, 2017
 */

public class Neighbourhood {
    public static final String NAME_ATTRIBUTE = "NEIGH_NAME";
    public static final String NUM_ATTRIBUTE = "NEIGHNUM";

    private String mNeighNum;
    private String mName;
    private List<LatLng> mLatLngs;

    public Neighbourhood() {
        mLatLngs = new ArrayList<>();
    }

    public void addLatLng(double x, double y) {
        LatLng latLng = new LatLng(y, x);
        mLatLngs.add(latLng);
    }

    public String getNeighNum() {
        return mNeighNum;
    }

    public void setNeighNum(String neighNum) {
        this.mNeighNum = neighNum;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<LatLng> getLatLngs() {
        return mLatLngs;
    }

    public void setLatLngs(List<LatLng> latLngs) {
        this.mLatLngs = latLngs;
    }
}
