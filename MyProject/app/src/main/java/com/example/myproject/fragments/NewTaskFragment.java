package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAddTaskListener;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.example.myproject.customclasses.Task;
import com.example.myproject.receiver.AlarmReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class NewTaskFragment extends Fragment {

    OnBackButtonListener onBackButtonListener;
    OnAddTaskListener onAddTaskListener;

    String date = "";

    int forAlarmManagerDay;
    int forAlarmManagerMonth;
    int forAlarmManagerYear;

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        month++;

        forAlarmManagerDay = day;
        forAlarmManagerMonth = month;
        forAlarmManagerYear = year;

        date = ((day < 10) ? "0" + day : day) + "/" + ((month < 10) ? "0" + month : month) + "/" + year;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        LinearLayout backButton = view.findViewById(R.id.go_back_new_task);

        EditText taskEditText = view.findViewById(R.id.new_task_text);

        Button addTaskButton = view.findViewById(R.id.add_new_task_button);

        TimePicker timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        Button datePickerButton = view.findViewById(R.id.date_picker);
        datePickerButton.setText(date);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog;

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month++;

                        forAlarmManagerDay = day;
                        forAlarmManagerMonth = month;
                        forAlarmManagerYear = year;

                        date = ((day < 10) ? "0" + day : day) + "/" + ((month < 10) ? "0" + month : month) + "/" + year;
                        datePickerButton.setText(date);
                    }
                };

                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

                datePickerDialog.show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonListener.onBackButtonClickListener();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = taskEditText.getText().toString();
                Long hour = (long) timePicker.getHour();
                Long minute = (long) timePicker.getMinute();

                Task newTask = new Task(false, text, date, hour, minute);

                // -------------------Alarm manager-------------------
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.YEAR, forAlarmManagerYear);
                calendar.set(Calendar.MONTH, forAlarmManagerMonth - 1);
                calendar.set(Calendar.DAY_OF_MONTH, forAlarmManagerDay);
                calendar.set(Calendar.HOUR_OF_DAY, hour.intValue());
                calendar.set(Calendar.MINUTE, minute.intValue());
                calendar.set(Calendar.SECOND, 0);

                setAlarm(calendar, text);
                // -------------------Alarm manager-------------------

                FirebaseFirestore.getInstance()
                        .collection("users/"
                                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                + "/tasks").document()
                        .set(newTask);

                onAddTaskListener.tasksAddOperationPerformed("new task added");
            }
        });

        return view;
    }

    private void setAlarm(Calendar calendar, String taskText) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("taskText", taskText);

        int id = (int) System.currentTimeMillis();
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onBackButtonListener = (OnBackButtonListener) activity;
            onAddTaskListener = (OnAddTaskListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}