package com.example.firebasefirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText userText;
    private Button addTextToDatabase;

    private ListView listView;

    private Button signOut;

    private ArrayAdapter<String> adapter;
    private final ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("DatabaseData");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String language = ds.getValue(String.class);
                    list.add(language);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "error while reading data!", Toast.LENGTH_SHORT).show();
            }
        });

        userText = findViewById(R.id.text_editText_id);
        addTextToDatabase = findViewById(R.id.add_button_id);
        listView = findViewById(R.id.list_view_id);
        signOut = findViewById(R.id.signOut_button_id);

        adapter = new ArrayAdapter<>(this, R.layout.listview_row, list);
        listView.setAdapter(adapter);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Sign out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });

        addTextToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_text = userText.getText().toString();
                if (user_text.isEmpty()) {
                    Toast.makeText(MainActivity.this, "empty text field!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Android 2022").push().child("Text").setValue(user_text);
                    Toast.makeText(MainActivity.this, "successfully added!", Toast.LENGTH_SHORT).show();
                    userText.setText("");
                }
            }
        });
    }
}