package com.example.myproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAuthenticationListener;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    OnAuthenticationListener onAuthenticationListener;
    OnBackButtonListener onBackButtonListener;

    FirebaseAuth auth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        auth = FirebaseAuth.getInstance();

        EditText userEmail = view.findViewById(R.id.sign_up_user_email);
        EditText userPassword = view.findViewById(R.id.sign_up_password);
        EditText userConfirmPassword = view.findViewById(R.id.sign_up_confirm_password);

        Button signUpButton = view.findViewById(R.id.sign_up_button);
        LinearLayout backButton = view.findViewById(R.id.go_back_sign_up);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackButtonListener.onClickListener();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailString = userEmail.getText().toString();
                String userPasswordString = userPassword.getText().toString();
                String userConfirmPasswordString = userConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(userEmailString) || TextUtils.isEmpty(userPasswordString) || TextUtils.isEmpty(userConfirmPasswordString)) {
                    Toast.makeText(getActivity(), "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (userConfirmPassword.length() < 6) {
                    Toast.makeText(getActivity(), "Password too short!", Toast.LENGTH_SHORT).show();
                } else if (!userPasswordString.equals(userConfirmPasswordString)) {
                    Toast.makeText(getActivity(), "Passwords aren't equal!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(userEmailString, userPasswordString)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        onAuthenticationListener.authenticationOperationPerformed("Sign up button");
                                    } else {
                                        Toast.makeText(getActivity(), "Error while creating account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onAuthenticationListener = (OnAuthenticationListener) activity;
            onBackButtonListener = (OnBackButtonListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}