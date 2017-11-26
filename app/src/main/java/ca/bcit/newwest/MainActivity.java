package ca.bcit.newwest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String NEIGHBOURHOOD_URL = "http://opendata.newwestcity.ca/downloads/neighbourhood-boundaries-ocp-2017/NEIGHBOURHOOD_2017.json";
    private static final String PARK_RECREATION_URL = "http://opendata.newwestcity.ca/downloads/community-programming/PARKS_RECREATION_AND_COMMUNITY_SCHOOL_PROGRAMMING.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new DataRequest().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Floating Action Bar Clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);

            }
        });
    }

    private class DataRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(NEIGHBOURHOOD_URL);
            if (jsonStr == null) {
                return null;
            }

            try {
                JSONArray jsonArray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONArray array = jsonObject.getJSONObject("json_geometry").getJSONArray("coordinates").getJSONArray(0);
                    Neighbourhood neighbourhood = new Neighbourhood();
                    neighbourhood.setName(jsonObject.getString("NEIGH_NAME"));
                    neighbourhood.setNeighNum(jsonObject.getString("NEIGHNUM"));
                    for (int j = 0; j < array.length(); j++) {
                        neighbourhood.addLatLng(array.getJSONArray(j).getDouble(0), array.getJSONArray(j).getDouble(1));
                    }

                    NeighbourhoodList.addNeighbourhood(neighbourhood);
                }

            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }

            return null;
        }
    }

}
