package com.example.moneymanager;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SeeExpensesFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    public SeeExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_see_expenses, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);
        readExpenses();

        return view;
    }

    public void readExpenses() {
        ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(getActivity());
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = helper.readExpenses(database);
        ArrayList<String> records = new ArrayList<>();

        while (cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.EXPENSE_TITLE)
            );
            String amount = Double.toString(cursor.getDouble(cursor
                    .getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.EXPENSE_AMOUNT))
            );


            records.add("Title: " + title + "\nAmount: " + amount + "$");
        }

        adapter = new ArrayAdapter<>(getActivity(), R.layout.expense_info, records);
        listView.setAdapter(adapter);
        helper.close();
    }
}