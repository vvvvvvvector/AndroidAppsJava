package com.example.myproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;

public class NewTaskFragment extends Fragment {

    OnBackButtonListener onBackButtonListener;

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        LinearLayout backButton = view.findViewById(R.id.go_back_new_task);

        Button addTaskButton = view.findViewById(R.id.add_new_task_button);

        TimePicker timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonListener.onBackButtonClickListener();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("doc", timePicker.getHour() + " " + timePicker.getMinute());
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onBackButtonListener = (OnBackButtonListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}