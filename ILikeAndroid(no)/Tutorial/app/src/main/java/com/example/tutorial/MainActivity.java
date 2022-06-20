package com.example.tutorial;

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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        String marki[] = {"Mercedes", "Citroen", "Mazda", "Fiat", "Dacia", "Audi", "BMW"};

        ArrayList<String> samochody = new ArrayList<>(); // remove, add, replace!!!
        samochody.addAll(Arrays.asList(marki));

        adapter = new ArrayAdapter<>(this, R.layout.element, samochody);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(klikniecie);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Proces uspiony w tle", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void close(View view) {
        AlertDialog.Builder komunikat = new AlertDialog.Builder(this);
        komunikat.setMessage("jestes pewien, ze chcesz wyjsc?");

        komunikat.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // finish(); sleep
                finishAndRemoveTask();
            }
        });

        komunikat.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Super, ze zastales!", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = komunikat.create();
        alertDialog.show();
    }

    private AdapterView.OnItemClickListener klikniecie = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            String itemValue = (String) listView.getItemAtPosition(index);
            Toast.makeText(getApplicationContext(), "Skopiowano " + itemValue, Toast.LENGTH_SHORT).show();

            ClipboardManager kopiuj = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Skopiowana wartosc", itemValue);
            kopiuj.setPrimaryClip(clip);

            // samochody.add("dadas");
            // samochody.set(index, "daseqweq");
            // samochody.remove(index);

            // adapter.notifyDataSetChanged();
        }
    };
}