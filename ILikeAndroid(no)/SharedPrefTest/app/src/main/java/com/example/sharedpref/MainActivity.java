package com.example.sharedpref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    CheckBox checkBox;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
        editText = findViewById(R.id.editText);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        String text = sharedPreferences.getString("editText", "");
        editText.setText(text);

        boolean check = sharedPreferences.getBoolean("checkBox", false);
        checkBox.setChecked(check);
    }

    public void onButtonClick(View view) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = sharedPreferences.edit();

        String text = editText.getText().toString();
        prefsEdit.putString("editText", text);

        boolean check = checkBox.isChecked();
        prefsEdit.putBoolean("checkBox", check);

        prefsEdit.apply();
    }
}