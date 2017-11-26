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

        // Add a marker in Sydney and move the camera
        LatLng newWest = new LatLng(49.2056288, -122.9113522);
        mMap.addMarker(new MarkerOptions().position(newWest).title("Marker in New West"));
        for (int i = 0; i < NeighbourhoodList.getNeighbourhoods().size(); i++) {
            Neighbourhood neighbourhood = NeighbourhoodList.getNeighbourhood(i);
            PolygonOptions rectOptions = new PolygonOptions().addAll(neighbourhood.getLatLngs());
            rectOptions.strokeColor(Color.RED);
            Polygon polygon = mMap.addPolygon(rectOptions);
            polygon.setStrokeWidth(2);
            polygon.setClickable(true);
            polygon.setTag(neighbourhood.getNeighNum());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newWest, 12.0f));
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
}
