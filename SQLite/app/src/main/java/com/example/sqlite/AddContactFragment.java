package com.example.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddContactFragment extends Fragment {

    private Button addButton;
    private EditText id_editText, name_editText, email_editText;

    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        addButton = view.findViewById(R.id.add_button);
        id_editText = view.findViewById(R.id.contact_id_add);
        name_editText = view.findViewById(R.id.contact_name_add);
        email_editText = view.findViewById(R.id.contact_email_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id_editText.getText().toString();
                String name = name_editText.getText().toString();
                String email = email_editText.getText().toString();

                ContactDatabaseHelper contactDatabaseHelper = new ContactDatabaseHelper(getActivity());
                SQLiteDatabase database = contactDatabaseHelper.getWritableDatabase();

                // 41
            }
        });

        return view;
    }
}