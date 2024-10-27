package com.example.bankingapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BalanceActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    String username;
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");

        tvBalance = findViewById(R.id.tvBalance);
        displayBalance();
    }

    private void displayBalance() {
        double balance = dbHelper.getBalance(username);
        tvBalance.setText("Your balance: ₹" + balance);
    }
}