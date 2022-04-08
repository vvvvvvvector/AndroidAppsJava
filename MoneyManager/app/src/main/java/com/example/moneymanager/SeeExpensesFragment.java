package com.example.moneymanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SeeExpensesFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private final String[] test = {
            "test 1",
            "test 2",
            "test 3",
            "test 4",
            "test 5"
    };

    public SeeExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_expenses, container, false);

        listView =  (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(view.getContext(), R.layout.expense_info, test);
        
        listView.setAdapter(adapter);

        return view;
    }
}