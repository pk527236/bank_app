package com.example.bankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bank.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE accounts (username TEXT PRIMARY KEY, balance REAL DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS accounts");
        onCreate(db);
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", hashPassword(password));

        long result = db.insert("users", null, values);
        if (result == -1) return false;

        ContentValues accountValues = new ContentValues();
        accountValues.put("username", username);
        accountValues.put("balance", 0.0);
        db.insert("accounts", null, accountValues);

        return true;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{username, hashPassword(password)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public double getBalance(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT balance FROM accounts WHERE username = ?",
                new String[]{username});
        if (cursor.moveToFirst()) {
            double balance = cursor.getDouble(0);
            cursor.close();
            return balance;
        }
        cursor.close();
        return 0;
    }

    public boolean updateBalance(String username, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("balance", amount);
        int result = db.update("accounts", values, "username = ?", new String[]{username});
        return result > 0;
    }

    public boolean deposit(String username, double amount) {
        double currentBalance = getBalance(username);
        double newBalance = currentBalance + amount;
        return updateBalance(username, newBalance);
    }

    public boolean withdraw(String username, double amount) {
        double currentBalance = getBalance(username);
        if (amount > currentBalance) {
            return false; // Insufficient funds
        }
        double newBalance = currentBalance - amount;
        return updateBalance(username, newBalance);
    }

    public boolean transfer(String senderUsername, String recipientUsername, double amount) {
        double senderBalance = getBalance(senderUsername);
        if (amount > senderBalance) {
            return false; // Insufficient funds
        }

        double newSenderBalance = senderBalance - amount;
        double recipientBalance = getBalance(recipientUsername);
        double newRecipientBalance = recipientBalance + amount;

        boolean senderUpdated = updateBalance(senderUsername, newSenderBalance);
        boolean recipientUpdated = updateBalance(recipientUsername, newRecipientBalance);

        return senderUpdated && recipientUpdated;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}