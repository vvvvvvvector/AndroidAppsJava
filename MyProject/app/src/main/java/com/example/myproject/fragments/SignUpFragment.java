package com.example.myproject.fragments;

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
import android.widget.LinearLayout;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAuthenticationListener;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    OnAuthenticationListener onAuthenticationListener;
    OnBackButtonListener onBackButtonListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        EditText userEmail = view.findViewById(R.id.sign_up_user_email);
        EditText userPassword = view.findViewById(R.id.sign_up_password);
        EditText userConfirmPassword = view.findViewById(R.id.sign_up_confirm_password);
        Button signUpButton = view.findViewById(R.id.sign_up_button);

        LinearLayout backButton = view.findViewById(R.id.go_back_sign_up);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonListener.onClickListener();
            }
        });

        signUpButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sign_up_button) {
            onAuthenticationListener.authenticationOperationPerformed("Sign up button");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onAuthenticationListener = (OnAuthenticationListener) activity;
            onBackButtonListener = (OnBackButtonListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity.toString() + " you must implement interface!");
        }
    }
}