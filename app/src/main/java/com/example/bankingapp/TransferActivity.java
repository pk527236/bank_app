package com.example.bankingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TransferActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    String username;
    EditText etRecipient, etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");
        etRecipient = findViewById(R.id.etRecipient);
        etAmount = findViewById(R.id.etAmount);

        Button btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(v -> transfer());
    }

    private void transfer() {
        String recipient = etRecipient.getText().toString();
        double amount = Double.parseDouble(etAmount.getText().toString());
        if (dbHelper.transfer(username, recipient, amount)) {
            Toast.makeText(this, "Transfer successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insufficient funds or invalid recipient", Toast.LENGTH_SHORT).show();
        }
    }
}