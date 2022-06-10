package com.example.myproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myproject.R;
import com.example.myproject.customclasses.Task;

import java.util.ArrayList;

public class TasksListAdapter extends ArrayAdapter<Task> {

    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;

    TasksAdapterCallback callback;

    public interface TasksAdapterCallback {
        void checkBoxChanged(int position);
    }

    public void setCallback(TasksAdapterCallback callback) {
        this.callback = callback;
    }

    static class ViewHolder {
        CheckBox isCompleted;
        TextView text;
        TextView date;
        TextView time;
    }

    public TasksListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get task information
        Boolean isCompleted = getItem(position).getCompleted();

        String taskText = getItem(position).getText();
        String taskDate = getItem(position).getText();

        Long taskHour = getItem(position).getHour();
        Long taskMinute = getItem(position).getMinute();

        // create the view result for showing the animation
        final View result;

        // ViewHolder object
        TasksListAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.isCompleted = convertView.findViewById(R.id.task_checkbox);
            holder.text = convertView.findViewById(R.id.task_text);
            holder.date = convertView.findViewById(R.id.task_date);
            holder.time = convertView.findViewById(R.id.task_time);

            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (TasksListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);

        result.startAnimation(animation);
        lastPosition = position;

        holder.isCompleted.setChecked(isCompleted);

        holder.text.setText(taskText);
        holder.date.setText("Date: " + taskDate);

        holder.time.setText("Time: " + (taskHour < 10 ? "0" + taskHour : taskHour) + ":" + (taskMinute < 10 ? "0" + taskMinute : taskMinute));

        holder.isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.checkBoxChanged(position);
            }
        });

        return convertView;
    }
}
