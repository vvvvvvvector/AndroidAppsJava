package com.example.moneymanager;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button addButton, seeButton, delButton;
    OnDatabaseOperationListener onDatabaseOperationListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface OnDatabaseOperationListener {
        void databaseOperationPerformed(int method);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addButton = view.findViewById(R.id.add_button_home);
        seeButton = view.findViewById(R.id.see_button_home);

        addButton.setOnClickListener(this);
        seeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button_home:
                onDatabaseOperationListener.databaseOperationPerformed(0);
                break;
            case R.id.see_button_home:
                onDatabaseOperationListener.databaseOperationPerformed(1);
                break;
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onDatabaseOperationListener = (OnDatabaseOperationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " you must implement interface!");
        }
    }
}