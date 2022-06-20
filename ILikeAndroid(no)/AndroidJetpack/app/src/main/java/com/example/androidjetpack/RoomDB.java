package com.example.androidjetpack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MainData.class}, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {
    // database object
    private static RoomDB database;

    private static final String DATABASE_NAME = "myDatabase";

    public synchronized static RoomDB getInstance(Context context) {
        // init database if null
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDao mainDao();
}
