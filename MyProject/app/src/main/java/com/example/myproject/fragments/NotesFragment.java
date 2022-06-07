package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class NotesFragment extends Fragment {

    OnAddNoteListener onAddNoteListener;
    OnViewNoteListener onViewNoteListener;

    ArrayList<Note> notes;
    NotesListAdapter adapter;

    ListView notesList;
    TextView notesNumber;

    public NotesFragment() {
        // Required empty public constructor
    }

    private interface FireBaseCallback {
        void onCallback(ArrayList<Note> fetchedNotes);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notes = new ArrayList<>();
        adapter = new NotesListAdapter(requireContext(), R.layout.notes_list_single_note, notes);

        readUserNotesFromDatabase(new FireBaseCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCallback(ArrayList<Note> fetchedNotes) {
                notes.addAll(fetchedNotes);

                notesNumber.setText(notes.size() + " notes");

                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        notesList = view.findViewById(R.id.notes_list);
        notesList.setAdapter(adapter);

        notesNumber = view.findViewById(R.id.notes_number);
        notesNumber.setText(notes.size() + " notes");

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

    public void readUserNotesFromDatabase(FireBaseCallback fireBaseCallback) {
        ArrayList<Note> fetchedNotes = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> note = doc.getData();

                                String title = (String) note.get("title");
                                String text = (String) note.get("text");

                                Note fetchedNote = new Note(title, text);

                                Log.d("data", text);

                                fetchedNotes.add(fetchedNote);
                            }
                            fireBaseCallback.onCallback(fetchedNotes);
                        }
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onAddNoteListener = (OnAddNoteListener) activity;
            onViewNoteListener = (OnViewNoteListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}