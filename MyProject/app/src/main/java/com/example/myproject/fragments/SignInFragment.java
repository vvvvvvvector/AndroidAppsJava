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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAuthenticationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {

    OnAuthenticationListener onAuthenticationListener;

    FirebaseAuth auth;

    public SignInFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        auth = FirebaseAuth.getInstance();

        EditText userEmail = view.findViewById(R.id.sign_in_email);
        EditText userPassword = view.findViewById(R.id.sign_in_password);

        Button signInButton = view.findViewById(R.id.sign_in_button);
        TextView signUpText = view.findViewById(R.id.sign_up_text);

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

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailString = userEmail.getText().toString();
                String userPasswordString = userPassword.getText().toString();

                if (TextUtils.isEmpty(userEmailString) || TextUtils.isEmpty(userPasswordString)) {
                    Toast.makeText(getActivity(), "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(userEmailString, userPasswordString)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        onAuthenticationListener.authenticationOperationPerformed("Sign in button");
                                    } else {
                                        Toast.makeText(getActivity(), "Bad credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAuthenticationListener.authenticationOperationPerformed("Sign up text");
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
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}