package com.example.myproject.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAddTaskListener;
import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.example.myproject.customclasses.Task;
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

                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, minute.intValue());
                calendar.set(Calendar.HOUR, hour.intValue());
                calendar.set(Calendar.DAY_OF_MONTH, forAlarmManagerDay);
                calendar.set(Calendar.MONTH, forAlarmManagerMonth);
                calendar.set(Calendar.YEAR, forAlarmManagerYear);

                Log.d("doc", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                Log.d("doc", String.valueOf(calendar.get(Calendar.MONTH)));
                Log.d("doc", String.valueOf(calendar.get(Calendar.YEAR)));
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