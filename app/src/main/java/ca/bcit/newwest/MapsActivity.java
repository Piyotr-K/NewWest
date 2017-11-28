package ca.bcit.newwest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

import ca.bcit.newwest.Dao.PlaceDao;
import ca.bcit.newwest.model.Neighbourhood;
import ca.bcit.newwest.model.NeighbourhoodList;
import ca.bcit.newwest.model.Place;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean isLoadingAll = getIntent().getBooleanExtra("loadAll", false);
        if (isLoadingAll) {
            loadAll();
        } else {
            loadArea();
        }
        clickArea();
    }

    private void clickArea() {
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                System.out.println(polygon.getTag());
            }
        });
    }

    private void loadAll() {
        for (int i = 0; i < NeighbourhoodList.getNeighbourhoods().size(); i++) {
            Neighbourhood neighbourhood = NeighbourhoodList.getNeighbourhood(i);
            PolygonOptions rectOptions = new PolygonOptions().addAll(neighbourhood.getLatLngs());
            rectOptions.strokeColor(Color.RED);
            Polygon polygon = mMap.addPolygon(rectOptions);
            polygon.setStrokeWidth(5);
            polygon.setClickable(true);
            polygon.setTag(neighbourhood.getNeighNum());
        }
        PlaceDao placeDao = new PlaceDao(this);
        List<Place> places = placeDao.findAllPlaces();
        for (Place place : places) {
            LatLng point = new LatLng(place.getY(), place.getX());
            mMap.addMarker(new MarkerOptions().position(point).title(place.getName()));
        }
        // Add a marker in New Westminster and move the camera
        LatLng newWest = new LatLng(49.2056288, -122.9113522);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newWest, 12.0f));
    }

    private void loadArea() {
        String name = getIntent().getStringExtra("neighbourhood");
        String category = getIntent().getStringExtra("category");
        Neighbourhood neighbourhood = NeighbourhoodList.getNeighbourhood(name);
        if (neighbourhood != null) {
            PolygonOptions rectOptions = new PolygonOptions().addAll(neighbourhood.getLatLngs());
            rectOptions.strokeColor(Color.RED);
            Polygon polygon = mMap.addPolygon(rectOptions);
            polygon.setStrokeWidth(5);
            polygon.setClickable(true);
            polygon.setTag(neighbourhood.getNeighNum());
            LatLng latLng = neighbourhood.getLatLngs().get(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.5f));
        }
        PlaceDao placeDao  = new PlaceDao(this);
        List<Place> places = placeDao.findPlacesByNeigh(name);
        if (category.equalsIgnoreCase("Show Area")) {
            for (Place place : places) {
                LatLng point = new LatLng(place.getY(), place.getX());
                mMap.addMarker(new MarkerOptions().position(point).title(place.getName()));
            }
        } else {
            for (Place place : places) {
                if (place.getCategory().equalsIgnoreCase(category)) {
                    LatLng point = new LatLng(place.getY(), place.getX());
                    mMap.addMarker(new MarkerOptions().position(point).title(place.getName()));
                }
            }
        }
    }
}
