package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    OnAuthenticationListener onAuthenticationListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnAuthenticationListener {
        void authenticationOperationPerformed(String method);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText userEmail = view.findViewById(R.id.sign_in_email);
        EditText userPassword = view.findViewById(R.id.sign_in_password);
        Button signInButton = view.findViewById(R.id.sign_in_button);
        TextView signUpText = view.findViewById(R.id.sign_up_text);

        signInButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);

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

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sign_in_button) {
            onAuthenticationListener.authenticationOperationPerformed("Sign in");
        } else if (id == R.id.sign_up_text) {
            onAuthenticationListener.authenticationOperationPerformed("Sign up");
        }
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onAuthenticationListener = (OnAuthenticationListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity.toString() + " you must implement interface!");
        }
    }
}