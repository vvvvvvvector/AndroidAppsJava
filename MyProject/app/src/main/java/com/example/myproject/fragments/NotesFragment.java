package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myproject.R;
import com.example.myproject.adapters.NotesListAdapter;
import com.example.myproject.callbackinterfaces.OnViewNoteListener;
import com.example.myproject.customclasses.Note;
import com.example.myproject.callbackinterfaces.OnAddNoteListener;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    OnAddNoteListener onAddNoteListener;
    OnViewNoteListener onViewNoteListener;

    ArrayList<Note> notes = new ArrayList<>();

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

        notesNumber.setText(notes.size() + " notes");

        NotesListAdapter adapter = new NotesListAdapter(requireContext(), R.layout.notes_list_single_note, notes);

        notesList.setAdapter(adapter);

        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);

        ImageView menu = view.findViewById(R.id.menu_icon_notes);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ImageView newNote = view.findViewById(R.id.add_new_note_icon);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNoteListener.notesAddOperationPerformed("create note");
            }
        });

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", notes.get(i));
                onViewNoteListener.onViewOperationPerformed(bundle);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                AlertDialog.Builder communicate = new AlertDialog.Builder(getContext());
                communicate.setMessage("Do you really want to delete this note?");

                communicate.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(index);
                        notesNumber.setText(notes.size() + " notes");
                        adapter.notifyDataSetChanged();
                    }
                });

                communicate.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // closing alertdialog
                    }
                });

                AlertDialog alertDialog = communicate.create();
                alertDialog.show();

                return true;
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // fetch data from database

        Note note_1 = new Note("title 1", "text 1");
        Note note_2 = new Note("title 2", "text 2");
        Note note_3 = new Note("title 3", "text 3");
        Note note_4 = new Note("title 4", "text 4");
        Note note_5 = new Note("Note with long text", "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"");
        Note note_6 = new Note("title 6", "text 6");
        Note note_7 = new Note("title 7", "text 7");
        Note note_8 = new Note("title 8", "text 8");
        Note note_9 = new Note("title 9", "text 9");
        Note note_10 = new Note("title 10", "text 10");
        Note note_11 = new Note("title 11", "text 11");
        Note note_12 = new Note("title 12", "text 12");
        Note note_13 = new Note("title 13", "text 13");
        Note note_14 = new Note("title 14", "text 14");

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
        notes.add(note_12);
        notes.add(note_13);
        notes.add(note_14);

        Activity activity = (Activity) context;

        try {
            onAddNoteListener = (OnAddNoteListener) activity;
            onViewNoteListener = (OnViewNoteListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}