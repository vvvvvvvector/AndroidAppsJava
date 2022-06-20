package com.example.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteFragment extends Fragment {

    private EditText id_delete;
    private Button button_delete;

    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        id_delete = view.findViewById(R.id.contact_id_delete);
        button_delete = view.findViewById(R.id.delete_button);

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });

        return view;
    }

    public void deleteContact() {
        ContactDatabaseHelper contactDatabaseHelper = new ContactDatabaseHelper(getActivity());
        SQLiteDatabase database = contactDatabaseHelper.getWritableDatabase();

        int id = Integer.parseInt(id_delete.getText().toString());

        contactDatabaseHelper.deleteContact(id, database);
        contactDatabaseHelper.close();

        id_delete.setText("");

        Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_SHORT).show();
    }
}