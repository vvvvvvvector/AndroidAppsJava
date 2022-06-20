package com.example.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showRGB(View view) {
        Intent intent = new Intent(this, RGB.class);
        startActivity(intent);
    }

    public void showCMYK(View view) {
        Intent intent = new Intent(this, CMYK.class);
        startActivity(intent);
    }
}