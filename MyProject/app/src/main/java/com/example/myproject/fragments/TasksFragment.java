package com.example.myproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myproject.R;
import com.example.myproject.adapters.TasksListAdapter;
import com.example.myproject.callbackinterfaces.OnAddTaskListener;
import com.example.myproject.callbackinterfaces.OnDrawerListener;
import com.example.myproject.customclasses.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    OnAddTaskListener onAddTaskListener;
    OnDrawerListener onDrawerListener;

    ArrayList<Task> tasks;
    TasksListAdapter adapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new TasksListAdapter(requireContext(), R.layout.tasks_list_single_task, tasks);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        ImageView menu = view.findViewById(R.id.menu_icon_tasks);

        LinearLayout drawerUserNotes = view.findViewById(R.id.drawer_user_notes);
        LinearLayout drawerUserTasks = view.findViewById(R.id.drawer_user_tasks);
        LinearLayout drawerSignOut = view.findViewById(R.id.drawer_sign_out);

        ListView tasksList = view.findViewById(R.id.tasks_list);

        ImageView createNewTaskIcon = view.findViewById(R.id.add_new_task_icon);

        createNewTaskIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTaskListener.tasksAddOperationPerformed("create task");
            }
        });

        tasksList.setAdapter(adapter);

        adapter.setCallback(new TasksListAdapter.TasksAdapterCallback() {
            @Override
            public void checkBoxChanged(int position) {
                Log.d("doc", "checkbox click " + tasks.get(position).getText());
            }
        });

        TextView tasksNumber = view.findViewById(R.id.tasks_number);
        tasksNumber.setText(tasks.size() + " tasks");

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Log.d("doc", "list click " + tasks.get(index).getText());
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerUserNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawerListener.onDrawerOperationPerformed("go to user notes");
            }
        });

        drawerUserTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        drawerSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder communicate = new AlertDialog.Builder(getContext());
                communicate.setMessage("Do you really want to sign out?");

                communicate.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        onDrawerListener.onDrawerOperationPerformed("sign out");
                    }
                });

                communicate.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                });

                AlertDialog alertDialog = communicate.create();
                alertDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        tasks = new ArrayList<>();

        Task task1 = new Task(false, "Text 1", "23/05/2022", 22L, 45L);
        Task task2 = new Task(true, "Text 2", "22/04/2022", 21L, 44L);
        Task task3 = new Task(false, "Text 3", "21/03/2022", 20L, 43L);
        Task task4 = new Task(true, "Text 4", "20/02/2022", 19L, 42L);
        Task task5 = new Task(false, "Text 5", "19/01/2022", 18L, 41L);
        Task task6 = new Task(false, "Text 6", "18/05/2022", 22L, 45L);
        Task task7 = new Task(true, "Text 7", "17/04/2022", 21L, 44L);
        Task task8 = new Task(false, "Text 8", "16/03/2022", 20L, 43L);
        Task task9 = new Task(true, "Text 9", "15/02/2022", 19L, 42L);
        Task task10 = new Task(false, "Text 10", "14/01/2022", 18L, 41L);
        Task task11 = new Task(false, "Text 11", "13/05/2022", 22L, 45L);
        Task task12 = new Task(true, "Text 12", "12/04/2022", 21L, 44L);
        Task task13 = new Task(false, "Text 13", "11/03/2022", 20L, 43L);
        Task task14 = new Task(true, "Text 14", "10/02/2022", 19L, 42L);
        Task task15 = new Task(false, "Text 15", "09/01/2022", 18L, 41L);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);
        tasks.add(task7);
        tasks.add(task8);
        tasks.add(task9);
        tasks.add(task10);
        tasks.add(task11);
        tasks.add(task12);
        tasks.add(task13);
        tasks.add(task14);
        tasks.add(task15);

        try {
            onDrawerListener = (OnDrawerListener) activity;
            onAddTaskListener = (OnAddTaskListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}