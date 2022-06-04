package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myproject.R;
import com.example.myproject.adapters.NotesListAdapter;
import com.example.myproject.customclasses.Note;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    public NotesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        ListView notesList = view.findViewById(R.id.notes_list);
        TextView notesNumber = view.findViewById(R.id.notes_number);

        Note note_1 = new Note("title 1", "text 1");
        Note note_2 = new Note("title 2", "text 2");
        Note note_3 = new Note("title 3", "text 3");
        Note note_4 = new Note("title 4", "text 4");
        Note note_5 = new Note("title 5", "text 5");
        Note note_6 = new Note("title 6", "text 6");
        Note note_7 = new Note("title 7", "text 7");
        Note note_8 = new Note("title 8", "text 8");
        Note note_9 = new Note("title 9", "text 9");
        Note note_10 = new Note("title 10", "text 10");
        Note note_11 = new Note("title 11", "text 11");

        ArrayList<Note> notes = new ArrayList<>();
        notes.add(note_1);
        notes.add(note_2);
        notes.add(note_3);
        notes.add(note_4);
        notes.add(note_5);
        notes.add(note_6);
        notes.add(note_7);
        notes.add(note_8);
        notes.add(note_9);
        notes.add(note_10);
        notes.add(note_11);

        notesNumber.setText(notes.size() + " notes");

        NotesListAdapter adapter = new NotesListAdapter(requireContext(), R.layout.notes_list_single_note, notes);

        notesList.setAdapter(adapter);

        return view;
    }
}