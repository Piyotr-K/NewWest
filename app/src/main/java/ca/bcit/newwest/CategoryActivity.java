package ca.bcit.newwest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ca.bcit.newwest.Dao.PlaceDao;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setListView();
    }

    private void setListView() {
        final ListView listView = (ListView) findViewById(R.id.listView_category);
        final String name = getIntent().getStringExtra("neighbourhood");
        PlaceDao placeDao = new PlaceDao(this);
        List<String> categories = placeDao.findCategoryByNeigh(name);
        placeDao.close();
        categories.add("Show Area");
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String category = (String) adapterView.getItemAtPosition(position);
                Intent i = new Intent(CategoryActivity.this, MapsActivity.class);
                i.putExtra("category", category);
                i.putExtra("neighbourhood", name);
                startActivity(i);
            }
        });
    }
}
