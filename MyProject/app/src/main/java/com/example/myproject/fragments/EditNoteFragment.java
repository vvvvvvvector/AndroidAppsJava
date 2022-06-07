package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.example.myproject.callbackinterfaces.OnEditNoteListener;
import com.example.myproject.customclasses.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNoteFragment extends Fragment {

    Note receivedNote;
    String noteId;

    OnBackButtonListener onBackButtonListener;
    OnEditNoteListener onEditNoteListener;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        receivedNote = (Note) bundle.getSerializable("note");
        noteId = (String) bundle.getString("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        EditText noteTitle = view.findViewById(R.id.edit_note_title);
        EditText noteText = view.findViewById(R.id.edit_note_text);

        LinearLayout backButton = view.findViewById(R.id.go_back_edit_note);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonListener.onBackButtonClickListener();
            }
        });

        noteTitle.setText(receivedNote.getTitle());
        noteText.setText(receivedNote.getText());

        Button saveButton = view.findViewById(R.id.save_edited_note);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedNoteTitle = noteTitle.getText().toString();
                String updatedNoteText = noteText.getText().toString();

                Note updatedNote = new Note(updatedNoteTitle, updatedNoteText);

                FirebaseFirestore.getInstance()
                        .collection("users/"
                                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                + "/notes")
                        .document(noteId)
                        .set(updatedNote);

                onEditNoteListener.onSaveOperationPerformed();
            }
        });

        saveButton.setEnabled(false);

        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
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
            onEditNoteListener = (OnEditNoteListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}