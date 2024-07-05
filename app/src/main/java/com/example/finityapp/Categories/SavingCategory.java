package com.example.finityapp.Categories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SavingCategory {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private String name;
    private double goal;  // this is the amount the user has to save
    private double saved; // this is the amount the user has saved
    private String startDate;
    private String endDate;

    public SavingCategory(String categoryName, double goal, double saved, String startDate, String endDate) {
        this.databaseReference = database.getReference("SavingCategories").child(categoryName);
        this.name = categoryName;
        this.goal = goal;
        this.saved = saved;
        this.startDate = startDate;
        this.endDate = endDate;

        // Store values in the database
        saveData();
    }

    public SavingCategory() {
        // Default constructor required for calls to DataSnapshot.getValue(SavingCategory.class)
    }

    /* getters and setters */
    public double getSaved() {
        return saved;
    }

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
        this.endDate = endDate;
        saveData();
    }

    private void saveData() {
        databaseReference.child("goal").setValue(goal);
        databaseReference.child("saved").setValue(saved);
        databaseReference.child("startDate").setValue(startDate);
        databaseReference.child("endDate").setValue(endDate);
    }

    public void updateSaved(double saved) {
        this.saved = saved;
        databaseReference.child("saved").setValue(saved);
    }

    /* calculates the remaining days into a long format, the if build...
    is for having the appropriate build */
    public static String formatDaysBetween(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            return String.format("%d", daysBetween);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return "Invalid date format";
        }
    }

    /** returns the amount left to save as a string to two decimal places */
    public String amountRemaining() {
        double result = goal - saved;
        return String.format("%.2f", result);
    }

    @Override
    public String toString() {
        return "SavingCategory{" +
                "name='" + name + '\'' +
                ", goal=" + goal +
                ", saved=" + saved +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

