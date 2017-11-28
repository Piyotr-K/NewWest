package ca.bcit.newwest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ca.bcit.newwest.Dao.PlaceDao;
import ca.bcit.newwest.model.Neighbourhood;
import ca.bcit.newwest.model.NeighbourhoodList;
import ca.bcit.newwest.model.Place;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = LoadingActivity.class.getSimpleName();
    private static final String NEIGHBOURHOODS_URL = "http://opendata.newwestcity.ca/downloads/neighbourhood-boundaries-ocp-2017/NEIGHBOURHOOD_2017.json";
    private static final String PARKS_URL = "http://opendata.newwestcity.ca/downloads/parks/PARKS.json";
    private static final String SCHOOLS_URL = "http://opendata.newwestcity.ca/downloads/school-sites/SCHOOL_SITES.json";
    private static final String SKYTRAINS_STATION_URL = "http://opendata.newwestcity.ca/downloads/skytrain-stations-points/SKYTRAIN_STATIONS_PTS.json";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        tv = (TextView) findViewById(R.id.loading_text);

        new DataRequest().execute();
    }

    private class DataRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                pullNeighbourhoodsData();
                pullParksData();
                pullSchoolsData();
                pullSkytrainsData();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }

            return null;
        }

        private void pullNeighbourhoodsData() throws JSONException {
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(NEIGHBOURHOODS_URL);

            if (jsonStr == null) {
                return;
            }

            JSONArray neighbourJsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < neighbourJsonArray.length(); i++) {
                JSONObject jsonObject = neighbourJsonArray.getJSONObject(i);
                Neighbourhood neighbourhood = new Neighbourhood();
                neighbourhood.setName(jsonObject.getString("NEIGH_NAME"));
                neighbourhood.setNeighNum(jsonObject.getString("NEIGHNUM"));
                JSONArray points = jsonObject.getJSONObject("json_geometry").getJSONArray("coordinates").getJSONArray(0);
                for (int j = 0; j < points.length(); j++) {
                    neighbourhood.addLatLng(points.getJSONArray(j).getDouble(0), points.getJSONArray(j).getDouble(1));
                }
                NeighbourhoodList.addNeighbourhood(neighbourhood);
            }
        }

        private void pullParksData() throws JSONException {
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(PARKS_URL);

            if (jsonStr == null) {
                return;
            }

            JSONArray parkJsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < parkJsonArray.length(); i++) {
                JSONObject jsonObject = parkJsonArray.getJSONObject(i);
                Place place = new Place();
                place.setObjectId(jsonObject.getInt("OBJECTID"));
                place.setName(jsonObject.getString("Name"));
                place.setCategory("Park");
                place.setNeighbourhood("Neighbourhood");
                String type = jsonObject.getJSONObject("json_geometry").getString("type");
                JSONArray points = jsonObject.getJSONObject("json_geometry").getJSONArray("coordinates");
                if (type.equalsIgnoreCase("MultiPolygon")) {
                    points = points.getJSONArray(0).getJSONArray(0);
                } else {
                    points = points.getJSONArray(0);
                }
                place.setX(points.getJSONArray(0).getDouble(0));
                place.setY(points.getJSONArray(0).getDouble(1));
                PlaceDao placeDao = new PlaceDao(LoadingActivity.this);
                placeDao.insertOrUpdate(place);
                placeDao.close();
            }
        }

        private void pullSchoolsData() throws JSONException {
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(SCHOOLS_URL);

            if (jsonStr == null) {
                return;
            }

            JSONArray schoolJsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < schoolJsonArray.length(); i++) {
                JSONObject jsonObject = schoolJsonArray.getJSONObject(i);
                Place place = new Place();
                place.setObjectId(jsonObject.getInt("OBJECTID"));
                place.setName(jsonObject.getString("Name"));
                place.setCategory("School");
                place.setNeighbourhood("Neighbourhood");
                JSONArray points = jsonObject.getJSONObject("json_geometry").getJSONArray("coordinates").getJSONArray(0);
                place.setX(points.getJSONArray(0).getDouble(0));
                place.setY(points.getJSONArray(0).getDouble(1));
                PlaceDao placeDao = new PlaceDao(LoadingActivity.this);
                placeDao.insertOrUpdate(place);
                placeDao.close();
            }
        }

        private void pullSkytrainsData() throws JSONException {
            // TODO: use Ray Casting Algorithm to find the neighbourhood
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(SKYTRAINS_STATION_URL);

            if (jsonStr == null) {
                return;
            }

            JSONArray skytrainJsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < skytrainJsonArray.length(); i++) {
                JSONObject jsonObject = skytrainJsonArray.getJSONObject(i);
                Place place = new Place();
                place.setObjectId(jsonObject.getInt("ID"));
                place.setName(jsonObject.getString("NAME"));
                place.setCategory("Skytrain");
                //place.setNeighbourhood("Neighbourhood");
                place.setX(jsonObject.getDouble("X"));
                place.setY(jsonObject.getDouble("Y"));
                for (Neighbourhood n : NeighbourhoodList.getNeighbourhoods()) {
                    if (isPointInArea(place.getX(), place.getY(), n.getLatLngs())) {
                        System.out.println(n.getName());
                        place.setNeighbourhood(n.getName());
                        break;
                    }
                }
                PlaceDao placeDao = new PlaceDao(LoadingActivity.this);
                placeDao.insertOrUpdate(place);
                placeDao.close();
            }
        }

        private boolean isPointInArea(double x, double y, List<LatLng> vertices) {
            int count = 0;
            for (int i = 0; i < vertices.size() - 1; i++) {
                if (rayCastIntersect(x, y, vertices.get(i), vertices.get(i + 1))) {
                    count++;
                }
            }

            return ((count % 2) == 1);
        }

        private boolean rayCastIntersect(double x, double y, LatLng vertA, LatLng vertB) {
            double aY = vertA.latitude;
            double bY = vertB.latitude;
            double aX = vertA.longitude;
            double bX = vertB.longitude;

            if ((aY > y && bY > y) || (aY < y && bY < y)
                    || (aX < x && bX < x)) {
                return false; // a and b can't both be above or below pt.y, and a or
                // b must be east of pt.x
            }

            double m = (aY - bY) / (aX - bX); // Rise over run
            double bee = (-aX) * m + aY; // y = mx + b
            double temp = (y - bee) / m; // algebra is neat!

            return temp > x;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LoadingActivity.this.finish();
        }
    }
}
