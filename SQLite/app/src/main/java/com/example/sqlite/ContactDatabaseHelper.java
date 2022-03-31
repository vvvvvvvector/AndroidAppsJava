package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact_db";

    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "
            + ContactContract.ContactEntry.TABLE_NAME + "("
            + ContactContract.ContactEntry.CONTACT_ID + " number,"
            + ContactContract.ContactEntry.NAME + " text,"
            + ContactContract.ContactEntry.EMAIL + " text);";

    public static final String DROP_TABLE = "drop table if exists "
            + ContactContract.ContactEntry.TABLE_NAME;

    public ContactDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(DROP_TABLE);
        onCreate(database);
    }

    public void addContact(int id, String name, String email, SQLiteDatabase database) {
        ContentValues contactValues = new ContentValues();

        contactValues.put(ContactContract.ContactEntry.CONTACT_ID, id);
        contactValues.put(ContactContract.ContactEntry.NAME, name);
        contactValues.put(ContactContract.ContactEntry.EMAIL, email);

        database
                .insert(ContactContract.ContactEntry.TABLE_NAME, null, contactValues);
    }
}
