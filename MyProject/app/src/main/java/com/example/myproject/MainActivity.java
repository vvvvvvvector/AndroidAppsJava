package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.myproject.fragments.HomeFragment;
import com.example.myproject.fragments.NotesFragment;
import com.example.myproject.fragments.SignUpFragment;
import com.example.myproject.interfaces.OnAuthenticationListener;

public class MainActivity extends AppCompatActivity implements OnAuthenticationListener {

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
    public void authenticationOperationPerformed(String operation) {
        switch (operation) {
            case "Sign in button":
                Log.d("doc", "sign in logic");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Sign up text":
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Sign up button":
                Log.d("doc", "sign up logic");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}