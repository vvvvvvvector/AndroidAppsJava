package com.example.fragments_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MessageFragment.OnMessageReadListener {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private final ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListViewID);

        adapter = new ArrayAdapter<>(this, R.layout.element, strings);
        listView.setAdapter(adapter);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            MessageFragment messageFragment = new MessageFragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, messageFragment, null);

            fragmentTransaction.commit();
        }
    }


    @Override
    public void onMessageRead(String message) {
        strings.add(message);
        adapter.notifyDataSetChanged();
    }
}