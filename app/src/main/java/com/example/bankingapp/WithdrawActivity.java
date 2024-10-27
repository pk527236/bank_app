package com.example.bankingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    String username;
    EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");
        etAmount = findViewById(R.id.etAmount);

        Button btnWithdraw = findViewById(R.id.btnWithdraw);
        btnWithdraw.setOnClickListener(v -> withdraw());
    }

    private void withdraw() {
        double amount = Double.parseDouble(etAmount.getText().toString());
        if (dbHelper.withdraw(username, amount)) {
            Toast.makeText(this, "Withdrawal successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insufficient funds", Toast.LENGTH_SHORT).show();
        }
    }
}