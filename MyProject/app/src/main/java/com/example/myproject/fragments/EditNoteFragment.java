package com.example.myproject.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myproject.R;
import com.example.myproject.customclasses.Note;

public class EditNoteFragment extends Fragment {

    Note receivedNote;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        receivedNote = (Note) bundle.getSerializable("note");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        EditText noteTitle = view.findViewById(R.id.edit_note_title);
        EditText noteText = view.findViewById(R.id.edit_note_text);

        noteTitle.setText(receivedNote.getTitle());
        noteText.setText(receivedNote.getText());

        return view;
    }
}