package com.example.cities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CityInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(intent.getStringExtra(MainActivity.key));
    }
}