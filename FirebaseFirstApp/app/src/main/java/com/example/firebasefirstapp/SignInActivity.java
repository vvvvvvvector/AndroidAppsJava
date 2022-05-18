package com.example.firebasefirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signIn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.signIn_email_id);
        password = findViewById(R.id.signIn_password_id);
        signIn = findViewById(R.id.signIn_button_id);

        auth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextEmail = email.getText().toString();
                String editTextPassword = password.getText().toString();

                if (TextUtils.isEmpty(editTextEmail) || TextUtils.isEmpty(editTextPassword)) {
                    Toast.makeText(SignInActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(editTextEmail, editTextPassword);
                }
            }
        });
    }

    private void signInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Sign in failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}