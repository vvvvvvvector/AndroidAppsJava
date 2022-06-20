package com.example.randomnumbers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Integer> adapter;
    private final ArrayList<Integer> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListViewID);

        adapter = new ArrayAdapter<>(this, R.layout.element, numbers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(itemClick);
    }

    private final AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            AlertDialog.Builder communicate = new AlertDialog.Builder(MainActivity.this);
            communicate.setMessage("Do you want to copy a number?");

            communicate.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Copied: " + numbers.get(index), Toast.LENGTH_SHORT).show();

                    ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("Copied value: ", Integer.toString(numbers.get(index)));
                    copy.setPrimaryClip(data);
                }
            });

            communicate.setNeutralButton("Randomize new num", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Random rn = new Random();
                    numbers.set(index, rn.nextInt(1_000_001));
                    adapter.notifyDataSetChanged();
                }
            });

            communicate.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // do nothing
                }
            });

            AlertDialog alertDialog = communicate.create();
            alertDialog.show();
        }
    };

    public void randomize(View view) {
        Random rn = new Random();

        if (numbers.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                numbers.add(rn.nextInt(1_000_001));
            }
        } else {
            int size = numbers.size();
            for (int i = 0; i < size; i++) {
                numbers.set(i, rn.nextInt(1_000_001));
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void randomizeNext(View view) {
        Random rn = new Random();

        for (int i = 0; i < 10; i++) {
            numbers.add(rn.nextInt(1_000_001));
        }

        adapter.notifyDataSetChanged();
    }
}