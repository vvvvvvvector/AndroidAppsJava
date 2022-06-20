package com.example.power;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String key_1 = "pow2";
    public final static String key_2 = "pow3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pow2(View view) {
        Intent intent = new Intent(this, PowerTwo.class);

        EditText editText = (EditText) findViewById(R.id.numberStrID);
        String number = editText.getText().toString();

        intent.putExtra(key_1, number);

        startActivity(intent);
    }

    public void pow3(View view) {
        Intent intent = new Intent(this, PowerThree.class);

        EditText editText = (EditText) findViewById(R.id.numberStrID);
        String number = editText.getText().toString();

        intent.putExtra(key_2, number);

        startActivity(intent);
    }
}