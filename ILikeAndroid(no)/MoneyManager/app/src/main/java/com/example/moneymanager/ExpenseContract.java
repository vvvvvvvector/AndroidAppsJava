package com.example.moneymanager;

public class ExpenseContract {

    private ExpenseContract() {
    }

    public static class ExpenseEntry {
        public static final String TABLE_NAME = "expenses_info";
        public static final String EXPENSE_TITLE = "expense_title";
        public static final String EXPENSE_AMOUNT = "expense_amount";
    }
}
