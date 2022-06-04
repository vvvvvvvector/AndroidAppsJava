package com.example.myproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myproject.R;
import com.example.myproject.customclasses.Note;

import java.util.ArrayList;

public class NotesListAdapter extends ArrayAdapter<Note> {

    private final Context mContext;
    private final int mResourse;

    public NotesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String noteTitle = getItem(position).getTitle();
        String noteText = getItem(position).getText();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourse, parent, false);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.note_title);
        TextView textTextView = (TextView) convertView.findViewById(R.id.note_text);

        titleTextView.setText(noteTitle);
        textTextView.setText(noteText);

        return convertView;
    }
}
