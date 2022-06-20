package com.example.power;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PowerThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_three);

        Intent intent = getIntent();
        String number = intent.getStringExtra(MainActivity.key_2);

        int num = Integer.parseInt(number);
        number = Integer.toString(num * num * num);

        TextView textView = (TextView) findViewById(R.id.PowerThreeID);
        textView.setText(number);
    }
}