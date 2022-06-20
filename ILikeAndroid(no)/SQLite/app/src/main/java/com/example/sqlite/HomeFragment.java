package com.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button addButton, seeButton, editButton, deleteButton;
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

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        seeButton = view.findViewById(R.id.see_button);
        seeButton.setOnClickListener(this);

        editButton = view.findViewById(R.id.edit_button);
        editButton.setOnClickListener(this);

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                onDatabaseOperationListener.databaseOperationPerformed(0);
                break;
            case R.id.see_button:
                onDatabaseOperationListener.databaseOperationPerformed(1);
                break;
            case R.id.edit_button:
                onDatabaseOperationListener.databaseOperationPerformed(2);
                break;
            case R.id.delete_button:
                onDatabaseOperationListener.databaseOperationPerformed(3);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onDatabaseOperationListener = (OnDatabaseOperationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "you should implement interface!");
        }
    }
}