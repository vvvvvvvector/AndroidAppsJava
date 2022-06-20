package com.example.appwithpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (password.equals("")) {
                    intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), EnterPasswordActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}