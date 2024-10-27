package com.example.bankingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");

        Button btnBalance = findViewById(R.id.btnBalance);
        Button btnWithdraw = findViewById(R.id.btnWithdraw);
        Button btnDeposit = findViewById(R.id.btnDeposit);
        Button btnTransfer = findViewById(R.id.btnTransfer);

        btnBalance.setOnClickListener(v -> {
            Intent intent = new Intent(this, BalanceActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnWithdraw.setOnClickListener(v -> {
            Intent intent = new Intent(this, WithdrawActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnDeposit.setOnClickListener(v -> {
            Intent intent = new Intent(this, DepositActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        btnTransfer.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransferActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}