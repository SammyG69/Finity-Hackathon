package com.example.finityapp;

import java.util.Date;

public class Transaction {

    private double amount;

    private Date date;

    private String Category;

    public Transaction(double amount, Date date, String category) {
        this.amount = amount;
        this.date = date;
        Category = category;
    }

    public Transaction() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
