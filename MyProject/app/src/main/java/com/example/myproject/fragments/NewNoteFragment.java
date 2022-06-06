package com.example.myproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAddNoteListener;

public class NewNoteFragment extends Fragment {

    OnAddNoteListener onAddNoteListener;

    public NewNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);

        EditText title = view.findViewById(R.id.new_note_title);
        EditText text = view.findViewById(R.id.new_note_text);
        Button add = view.findViewById(R.id.add_new_note);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here add to database logic
                onAddNoteListener.notesAddOperationPerformed("new note added");
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onAddNoteListener = (OnAddNoteListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity.toString() + " you must implement interface!");
        }
    }
}