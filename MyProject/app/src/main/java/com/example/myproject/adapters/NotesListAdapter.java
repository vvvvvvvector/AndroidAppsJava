package com.example.myproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myproject.R;
import com.example.myproject.customclasses.Note;

import java.util.ArrayList;

public class NotesListAdapter extends ArrayAdapter<Note> {

    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView title;
        TextView text;
    }

    public NotesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the note information
        String noteTitle = getItem(position).getTitle();
        String noteText = getItem(position).getText();

        // create the view result for showing the animation
        final View result;

        // ViewHolder object
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.note_title);
            holder.text = convertView.findViewById(R.id.note_text);

            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);

        result.startAnimation(animation);
        lastPosition = position;

        holder.title.setText(noteTitle);
        holder.text.setText(noteText);

        return convertView;
    }
}
