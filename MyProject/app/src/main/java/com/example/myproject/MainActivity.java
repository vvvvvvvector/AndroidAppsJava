package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnAuthenticationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit();
    }

    @Override
    public void authenticationOperationPerformed(String method) {
        switch (method) {
            case "Sign in":
                Log.d("doc", "sign in operation");
                break;
            case "Sign up":
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}