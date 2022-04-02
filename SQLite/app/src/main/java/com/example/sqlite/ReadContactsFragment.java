package com.example.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReadContactsFragment extends Fragment {

    private TextView record;

    public ReadContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_contacts, container, false);

        record = view.findViewById(R.id.display_text);
        readContacts();

        return view;
    }

    private void readContacts() {
        ContactDatabaseHelper contactDatabaseHelper = new ContactDatabaseHelper(getActivity());
        SQLiteDatabase database = contactDatabaseHelper.getReadableDatabase();

        Cursor cursor = contactDatabaseHelper.readContacts(database);
        String info = "";

        while (cursor.moveToNext()) {
            String id = Integer.toString(cursor.getInt(cursor
                    .getColumnIndexOrThrow(ContactContract.ContactEntry.CONTACT_ID)));

            String name = cursor.getString(cursor
                    .getColumnIndexOrThrow(ContactContract.ContactEntry.NAME));

            String email = cursor.getString(cursor
                    .getColumnIndexOrThrow(ContactContract.ContactEntry.EMAIL));

            info += "\n\n" + "id: " + id + "\nname: " + name + "\nemail: " + email;
        }

        record.setText(info);
        contactDatabaseHelper.close();
    }
}