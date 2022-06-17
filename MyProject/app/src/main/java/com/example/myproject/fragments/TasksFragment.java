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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;
import com.example.myproject.adapters.TasksListAdapter;
import com.example.myproject.callbackinterfaces.OnAddTaskListener;
import com.example.myproject.callbackinterfaces.OnDrawerListener;
import com.example.myproject.customclasses.Note;
import com.example.myproject.customclasses.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class TasksFragment extends Fragment {

    OnAddTaskListener onAddTaskListener;
    OnDrawerListener onDrawerListener;

    ArrayList<Task> tasks;
    ArrayList<String> tasksIds;

    TasksListAdapter adapter;

    ListView tasksList;
    TextView tasksNumber;

    public TasksFragment() {
        // Required empty public constructor
    }

    private interface FireBaseCallback {
        void onTasksFetchCompleted(ArrayList<Task> fetchedTasks, ArrayList<String> fetchedTasksIds);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasks = new ArrayList<>();
        tasksIds = new ArrayList<>();
        adapter = new TasksListAdapter(requireContext(), R.layout.tasks_list_single_task, tasks);

        readUserTasksFromDatabase(new FireBaseCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTasksFetchCompleted(ArrayList<Task> fetchedTasks, ArrayList<String> fetchedTasksIds) {
                tasks.addAll(fetchedTasks);
                tasksIds.addAll(fetchedTasksIds);

                tasksNumber.setText(tasks.size() + " tasks");

                adapter.notifyDataSetChanged();
            }
        });
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

        tasksList = view.findViewById(R.id.tasks_list);
        tasksList.setAdapter(adapter);

        tasksNumber = view.findViewById(R.id.tasks_number);
        tasksNumber.setText(tasks.size() + " tasks");

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
                tasks.get(position).setCompleted(!tasks.get(position).getCompleted());

                FirebaseFirestore.getInstance()
                        .collection("users/"
                                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                + "/tasks")
                        .document(tasksIds.get(position))
                        .set(tasks.get(position));
            }
        });

        TextView tasksNumber = view.findViewById(R.id.tasks_number);
        tasksNumber.setText(tasks.size() + " tasks");

        tasksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                AlertDialog.Builder communicate = new AlertDialog.Builder(getContext());
                communicate.setMessage("Do you really want to delete this task?");

                communicate.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore.getInstance()
                                .collection("users/"
                                        + FirebaseAuth.getInstance().getCurrentUser().getUid()
                                        + "/tasks")
                                .document(tasksIds.get(index))
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "task was successfully deleted!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "task wasn't deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        tasks.remove(index);
                        tasksIds.remove(index);
                        tasksNumber.setText(tasks.size() + " tasks");
                        adapter.notifyDataSetChanged();
                    }
                });

                communicate.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // closing alertdialog
                    }
                });

                AlertDialog alertDialog = communicate.create();
                alertDialog.show();

                return true;
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

    public void readUserTasksFromDatabase(FireBaseCallback fireBaseCallback) {
        ArrayList<Task> fetchedTasks = new ArrayList<>();
        ArrayList<String> fetchedTasksIds = new ArrayList<>();

        FirebaseFirestore.getInstance()
                .collection("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> userTask = doc.getData();

                                Boolean completed = (Boolean) userTask.get("completed");
                                Long hour = (Long) userTask.get("hour");
                                Long minute = (Long) userTask.get("minute");
                                String text = (String) userTask.get("text");
                                String date = (String) userTask.get("date");

                                Task fetchedTask = new Task(completed, text, date, hour, minute);

                                fetchedTasks.add(fetchedTask);
                                fetchedTasksIds.add(doc.getId());
                            }
                            fireBaseCallback.onTasksFetchCompleted(fetchedTasks, fetchedTasksIds);
                        }
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onDrawerListener = (OnDrawerListener) activity;
            onAddTaskListener = (OnAddTaskListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}