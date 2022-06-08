package com.example.myproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myproject.R;
import com.example.myproject.callbackinterfaces.OnAddNoteListener;
import com.example.myproject.callbackinterfaces.OnDrawerListener;
import com.example.myproject.callbackinterfaces.OnViewNoteListener;
import com.google.firebase.auth.FirebaseAuth;

public class TasksFragment extends Fragment {

    OnDrawerListener onDrawerListener;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        ImageView menu = view.findViewById(R.id.menu_icon_tasks);

        LinearLayout drawerUserNotes = view.findViewById(R.id.drawer_user_notes);
        LinearLayout drawerUserTasks = view.findViewById(R.id.drawer_user_tasks);
        LinearLayout drawerSignOut = view.findViewById(R.id.drawer_sign_out);

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

        try {
            onDrawerListener = (OnDrawerListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity + " you must implement interface!");
        }
    }
}