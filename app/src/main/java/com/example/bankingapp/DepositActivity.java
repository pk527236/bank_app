package com.example.bankingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    String username;
    EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");
        etAmount = findViewById(R.id.etAmount);

        Button btnDeposit = findViewById(R.id.btnDeposit);
        btnDeposit.setOnClickListener(v -> deposit());
    }

    private void deposit() {
        double amount = Double.parseDouble(etAmount.getText().toString());
        if (dbHelper.deposit(username, amount)) {
            Toast.makeText(this, "Deposit successful", Toast.LENGTH_SHORT).show();
        }
    }
}