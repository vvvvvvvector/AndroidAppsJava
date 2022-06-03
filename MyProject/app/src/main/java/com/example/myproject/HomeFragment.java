package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText userEmail = view.findViewById(R.id.sign_in_email);
        EditText userPassword = view.findViewById(R.id.sign_in_password);
        TextView signUpButton = view.findViewById(R.id.sign_up_text);

        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail.getText().clear();
            }
        });

        userPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.getText().clear();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("doc", "hello world");
            }
        });

        return view;
    }
}