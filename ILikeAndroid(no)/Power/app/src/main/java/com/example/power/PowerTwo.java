package com.example.power;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PowerTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_two);

        Intent intent = getIntent();
        String number = intent.getStringExtra(MainActivity.key_1);

        int num = Integer.parseInt(number);
        number = Integer.toString(num * num);

        TextView textView = (TextView) findViewById(R.id.PowerTwoID);
        textView.setText(number);
    }
}