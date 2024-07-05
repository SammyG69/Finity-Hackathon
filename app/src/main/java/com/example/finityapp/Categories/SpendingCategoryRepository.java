package com.example.finityapp.Categories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpendingCategoryRepository {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String name;
    private double amount;  // this is the amount the user has to spend
    private String date;    //

    public SpendingCategoryRepository(String categoryName, double amount, String date) {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference("SpendingCategories").child(categoryName);
        this.name = categoryName;
        this.amount = amount;
        this.date = date;

        // Store values in the database
        saveData();
    }

    private void saveData() {
        // Using refrence to this category to set multiple values in a single call
        databaseReference.child("amount").setValue(amount);
        databaseReference.child("date").setValue(date);
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}