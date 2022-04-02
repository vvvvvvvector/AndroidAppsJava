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

public class UpdateFragment extends Fragment {

    private EditText id_update, name_update, email_update;
    private Button button_update;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        id_update = view.findViewById(R.id.contact_id_edit);
        name_update = view.findViewById(R.id.contact_name_edit);
        email_update = view.findViewById(R.id.contact_email_edit);

        button_update = view.findViewById(R.id.update_button);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });

        return view;
    }

    private void updateContact() {
        int id = Integer.parseInt(id_update.getText().toString());
        String name = name_update.getText().toString();
        String email = email_update.getText().toString();

        ContactDatabaseHelper contactDatabaseHelper = new ContactDatabaseHelper(getActivity());
        SQLiteDatabase database = contactDatabaseHelper.getWritableDatabase();

        contactDatabaseHelper.updateContact(id, name, email, database);
        contactDatabaseHelper.close();

        Toast.makeText(getActivity(), "Contact updated", Toast.LENGTH_LONG).show();

        id_update.setText("");
        name_update.setText("");
        email_update.setText("");
    }
}