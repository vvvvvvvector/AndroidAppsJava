package com.example.androidjetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button addButton, resetButton;
    RecyclerView recyclerView;

    List<MainData> mainDataList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text_id);
        addButton = findViewById(R.id.add_button_id);
        resetButton = findViewById(R.id.reset_button_id);
        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);

        mainDataList = database.mainDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MainAdapter(mainDataList, MainActivity.this);

        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (!text.equals("")) {
                    MainData data = new MainData(); // create record
                    data.setText(text); // set record text
                    database.mainDao().insert(data); // insert record in db
                    editText.setText("");
                    mainDataList.clear();
                    mainDataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(mainDataList);
                mainDataList.clear();
                mainDataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}