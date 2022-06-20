package com.example.appwithpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePasswordActivity extends AppCompatActivity {

    EditText newPassword, confirmPassword;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        newPassword = (EditText) findViewById(R.id.new_password_editText_id);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_editText_id);
        confirm = (Button) findViewById(R.id.confirm_button_id);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password = newPassword.getText().toString();
                String confirm_password = confirmPassword.getText().toString();

                if (new_password.equals("") || confirm_password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Write a password!", Toast.LENGTH_SHORT).show();
                } else {
                    if (new_password.equals(confirm_password)) {
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", new_password);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords are different!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}