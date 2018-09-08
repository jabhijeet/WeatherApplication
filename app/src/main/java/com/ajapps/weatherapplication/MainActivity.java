package com.ajapps.weatherapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.locationList);
        objects = new ArrayList<>();
        objects.add("Singapore");
        objects.add("Kuala Lumpur");
        objects.add("Manila");
        objects.add("Bali");
        objects.add("Sydney");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, objects);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String location = textView.getText().toString();
                Intent intent = new Intent(getApplicationContext(), WeatherInfoActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.button_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addlocation) {
            Intent i = new Intent(this, AddLocationActivity.class);
            int requestCode = 1;
            startActivityForResult(i, requestCode);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String text = data.getStringExtra("newLocation");
                Toast.makeText(getApplicationContext(), "Added Location:" + text, Toast.LENGTH_LONG).show();
                objects.add(text);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
