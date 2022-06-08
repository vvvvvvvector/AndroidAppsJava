package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.myproject.callbackinterfaces.OnBackButtonListener;
import com.example.myproject.callbackinterfaces.OnDrawerListener;
import com.example.myproject.callbackinterfaces.OnEditNoteListener;
import com.example.myproject.callbackinterfaces.OnViewNoteListener;
import com.example.myproject.fragments.EditNoteFragment;
import com.example.myproject.fragments.SignInFragment;
import com.example.myproject.fragments.NewNoteFragment;
import com.example.myproject.fragments.NotesFragment;
import com.example.myproject.fragments.SignUpFragment;
import com.example.myproject.callbackinterfaces.OnAddNoteListener;
import com.example.myproject.callbackinterfaces.OnAuthenticationListener;
import com.example.myproject.fragments.TasksFragment;

public class MainActivity extends AppCompatActivity implements
        OnAuthenticationListener,
        OnAddNoteListener,
        OnViewNoteListener,
        OnBackButtonListener,
        OnEditNoteListener,
        OnDrawerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        SignInFragment signInFragment = new SignInFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, signInFragment)
                .commit();
    }

    @Override
    public void authenticationOperationPerformed(String operation) {
        switch (operation) {
            case "Sign in button":
                Log.d("doc", "sign in logic");
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case "Sign up text":
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case "Sign up button":
                Log.d("doc", "sign up logic");
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
        }
    }

    @Override
    public void notesAddOperationPerformed(String operation) {
        switch (operation) {
            case "create note":
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NewNoteFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                break;
            case "new note added":
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
        }
    }

    @Override
    public void onViewOperationPerformed(Bundle bundle) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        editNoteFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, editNoteFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackButtonClickListener() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSaveOperationPerformed() {
        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NotesFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onDrawerOperationPerformed(String operation) {
        switch (operation) {
            case "sign out":
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SignInFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case "go to user tasks":
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TasksFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case "go to user notes":
                getSupportFragmentManager().popBackStack();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NotesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
        }
    }
}