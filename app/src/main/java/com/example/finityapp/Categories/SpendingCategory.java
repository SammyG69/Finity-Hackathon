package com.example.finityapp.Categories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpendingCategory {
    private static final FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private String name;
    private double amount;  // this is the amount the user has to spend
    private String date;
    private double limit;  // this is the limit the user has spent

    public SpendingCategory(String categoryName, double limit, double amount, String date) {
        this.databaseReference = database.getReference("SpendingCategories").child(categoryName);
        this.name = categoryName;
        this.limit = limit;
        this.amount = amount;
        this.date = date;

        // Store values in the database
        saveData();
    }



    public SpendingCategory() {
    }

    private void saveData() {
        // Using refrence to this category to set multiple values in a single call
        databaseReference.child("amount").setValue(amount);
        databaseReference.child("limit").setValue(limit);
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

    public double getLimit() {
        return limit;
    }
}