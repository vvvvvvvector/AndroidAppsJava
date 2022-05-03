package com.example.passwordvalidation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText password;
    TextView small, big, numbers, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = findViewById(R.id.enter_password_editText_id);
        small = findViewById(R.id.text_view_2);
        big = findViewById(R.id.text_view_3);
        numbers = findViewById(R.id.text_view_4);
        amount = findViewById(R.id.text_view_5);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String get_password = password.getText().toString();
                validate(get_password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void validate(String password) {
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern digits = Pattern.compile("[0-9]");

        if (!lowerCase.matcher(password).find()) {
            small.setTextColor(Color.RED);
        } else {
            small.setTextColor(Color.GREEN);
        }

        if (!upperCase.matcher(password).find()) {
            big.setTextColor(Color.RED);
        } else {
            big.setTextColor(Color.GREEN);
        }

        if (!digits.matcher(password).find()) {
            numbers.setTextColor(Color.RED);
        } else {
            numbers.setTextColor(Color.GREEN);
        }

        if (password.length() < 8) {
            amount.setTextColor(Color.RED);
        } else {
            amount.setTextColor(Color.GREEN);
        }
    }
}