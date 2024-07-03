package com.example.finityapp.Categories;

import android.os.Build;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SavingCategoryRepository {
        private FirebaseDatabase database;
        private DatabaseReference databaseReference;
        private String name;
        private double goal;  // this is the amount the user has to spend
        private double saved; // this is the amount the user has saved
        private String startDate;
        private String endDate;

        public SavingCategoryRepository(String categoryName, double goal, String startDate, String endDate) {
            this.database = FirebaseDatabase.getInstance();
            this.databaseReference = database.getReference("SavingCategories").child(categoryName);
            this.name = categoryName;
            this.goal = goal;
            this.startDate = startDate;
            this.endDate = endDate;

            // Store values in the database
            saveData();
        }

        /* getters and setters */
        public String getName() {
            return name;
        }

        public double getGoal() {
            return goal;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            // need to add this to the db if changed
            this.endDate = endDate;
        }

    private void saveData() {
        // Using refrence to this category to set multiple values in a single call
        databaseReference.child("amount").setValue(goal);
        databaseReference.child("startDate").setValue(startDate);
        databaseReference.child("endDate").setValue(endDate);
    }

    /* calculates the remaning days into a long format, the if build...
    is for having the appropriate build */
    public static String formatDaysBetween(String startDateStr, String endDateStr) {
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            // Parse the dates from strings
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            // Calculate the number of days between the two dates
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

            // Format the result to no decimal places and return as a string
            return String.format("%d", daysBetween);
        } catch (Exception e) {
            // Handle any parsing or calculation exceptions here
            System.err.println("Error: " + e.getMessage());
            return "Invalid date format";
        }
    }

    /** returns the amount left to save as a string to two decimal places */
    public String amountRemaining() {
        // Subtract the two double values
        double result = goal - saved;

        // Format the result to 2 decimal places and return it as a String
        return String.format("%.2f", result);
    }
}
