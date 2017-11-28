package ca.bcit.newwest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.bcit.newwest.model.Neighbourhood;
import ca.bcit.newwest.model.NeighbourhoodList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, LoadingActivity.class);
        startActivity(i);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Floating Action Bar Clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("loadAll", true);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }

    private void setListView() {
        final ListView listView = (ListView) findViewById(R.id.listView_main_neighbourhoods);
        NeighbourhoodAdapter adapter = new NeighbourhoodAdapter(this, NeighbourhoodList.getNeighbourhoods());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Neighbourhood neighbourhood = (Neighbourhood) adapterView.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, CategoryActivity.class);
                i.putExtra("neighbourhood", neighbourhood.getName());
                startActivity(i);
            }
        });
    }
}
