package com.example.moneymanager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenseFragment extends Fragment {

    private Button addButton;
    private EditText title_text, amount_text;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        addButton = view.findViewById(R.id.add_button_fragment);
        title_text = view.findViewById(R.id.expense_title);
        amount_text = view.findViewById(R.id.amount_fragment);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_text.getText().toString();
                String amount = amount_text.getText().toString();

                ExpenseDatabaseHelper helper = new ExpenseDatabaseHelper(getActivity());
                SQLiteDatabase database = helper.getWritableDatabase();
                double amount_double = Double.parseDouble(amount);
                helper.addExpense(title, amount_double, database);
                helper.close();

                title_text.setText("");
                amount_text.setText("");

                Toast.makeText(getActivity(), "added", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}