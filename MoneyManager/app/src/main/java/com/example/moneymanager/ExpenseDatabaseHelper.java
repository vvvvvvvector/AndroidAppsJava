package com.example.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expenses_database";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "
            + ExpenseContract.ExpenseEntry.TABLE_NAME + "("
            + ExpenseContract.ExpenseEntry.EXPENSE_TITLE + " text,"
            + ExpenseContract.ExpenseEntry.EXPENSE_AMOUNT + " real);";

    public static final String DROP_TABLE = "drop table if exists "
            + ExpenseContract.ExpenseEntry.TABLE_NAME;

    public ExpenseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void deleteExpense(String title, SQLiteDatabase database) {
        database.delete(ExpenseContract.ExpenseEntry.TABLE_NAME
                , "expense_title=?"
                , new String[]{title});
    }

    public void addExpense(String title, double amount, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ExpenseContract.ExpenseEntry.EXPENSE_TITLE, title);
        contentValues.put(ExpenseContract.ExpenseEntry.EXPENSE_AMOUNT, amount);

        database.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, contentValues);
    }

    public Cursor readExpenses(SQLiteDatabase database) {
        String[] projections = {
                ExpenseContract.ExpenseEntry.EXPENSE_TITLE,
                ExpenseContract.ExpenseEntry.EXPENSE_AMOUNT
        };

        Cursor cursor = database.query(
                ExpenseContract.ExpenseEntry.TABLE_NAME
                , projections
                , null
                , null
                , null
                , null
                , null);

        return cursor;
    }
}
