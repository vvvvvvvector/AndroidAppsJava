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

        String city = intent.getStringExtra(MainActivity.key);

        switch (city) {
            case "Warszawa":
                textView.setText(R.string.warsaw_info);
                break;
            case "Kraków":
                textView.setText(R.string.krakow_info);
                break;
            case "Łódź":
                textView.setText(R.string.lodz_info);
                break;
            case "Wrocław":
                textView.setText(R.string.wroclaw_info);
                break;
            case "Poznań":
                textView.setText(R.string.poznan_info);
                break;
            case "Szczecin":
                textView.setText(R.string.szczecin_info);
                break;
            case "Lublin":
                textView.setText(R.string.lublin_info);
                break;
        }
    }
}