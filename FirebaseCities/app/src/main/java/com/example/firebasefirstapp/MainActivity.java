package com.example.firebasefirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button addCityToDatabase;

    private ListView listView;

    private Button signOut;

    private ArrayAdapter<String> adapter;
    private final ArrayList<String> list = new ArrayList<>();

    final ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore.getInstance().collection("cities")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> city = doc.getData();

                                Boolean capital = (Boolean) city.get("capital");
                                String country = (String) city.get("country");
                                String name = (String) city.get("name");
                                Long population = (Long) city.get("population");

                                cities.add(new City(capital, country, name, population));
                                list.add(name);

                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        addCityToDatabase = findViewById(R.id.add_button_id);
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

        addCityToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialog_city_add);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                EditText cityName = dialog.findViewById(R.id.edit_text_city_name);
                EditText cityCountry = dialog.findViewById(R.id.edit_text_city_country);
                EditText cityIsCapital = dialog.findViewById(R.id.edit_text_city_is_capital);
                EditText cityPopulation = dialog.findViewById(R.id.edit_text_city_population);
                Button addCity = dialog.findViewById(R.id.add_city);

                dialog.show();

                addCity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String city_name = cityName.getText().toString();
                        String city_country = cityCountry.getText().toString();
                        Boolean city_is_capital = Boolean.parseBoolean(cityIsCapital.getText().toString());
                        Long city_population = Long.parseLong(cityPopulation.getText().toString(), 10);

                        City city = new City(city_is_capital, city_country, city_name, city_population);

                        cities.add(city);
                        list.add(city_name);

                        adapter.notifyDataSetChanged();

                        FirebaseFirestore.getInstance().collection("cities")
                                .document(city_name).set(city);

                        Toast.makeText(MainActivity.this, "City was successfully added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialog_city_info);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                TextView cityName = dialog.findViewById(R.id.city_name);
                TextView cityCountry = dialog.findViewById(R.id.city_country);
                TextView cityIsCapital = dialog.findViewById(R.id.city_is_capital);
                TextView cityPopulation = dialog.findViewById(R.id.city_population);

                cityName.setText("name: " + cities.get(i).getName());
                cityCountry.setText("country: " + cities.get(i).getCountry());
                cityIsCapital.setText("is capital: " + cities.get(i).getCapital().toString());
                cityPopulation.setText("population: " + cities.get(i).getPopulation().toString());

                dialog.show();
            }
        });
    }
}