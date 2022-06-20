package com.example.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public final static String key = "key";

    private final String[] cities = {
            "Warszawa",
            "Kraków",
            "Łódź",
            "Wrocław",
            "Poznań",
            "Szczecin",
            "Lublin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.ListViewID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, cities);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listElementClick);
    }

    public void showCityInfo(int i) {
        Intent intent = new Intent(this, CityInfo.class);

        intent.putExtra(key, cities[i]);

        startActivity(intent);
    }

    private final AdapterView.OnItemClickListener listElementClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            showCityInfo(index);
        }
    };
}