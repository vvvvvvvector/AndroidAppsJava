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
import android.widget.LinearLayout;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAddNoteListener;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.example.myproject.customclasses.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewNoteFragment extends Fragment {

    OnAddNoteListener onAddNoteListener;
    OnBackButtonListener onBackButtonListener;

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

        LinearLayout backButton = view.findViewById(R.id.go_back_new_note);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonListener.onBackButtonClickListener();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNoteTitle = title.getText().toString();
                String newNoteText = text.getText().toString();

                Note newNote = new Note(newNoteTitle, newNoteText);

                FirebaseFirestore.getInstance()
                        .collection("users/"
                                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                + "/notes").document()
                        .set(newNote);

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
            onBackButtonListener = (OnBackButtonListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}