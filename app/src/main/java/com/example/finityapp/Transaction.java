package com.example.finityapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.Date;

public class Transaction extends BaseObservable {

    private String amount;

    private String date;

    private String Category;

    public Transaction(String amount, String date, String category) {
        this.amount = amount;
        this.date = String.valueOf(date);
        Category = category;
    }

    public Transaction() {
    }

    @Bindable
    public String getAmount() {
        return amount;

    }

    public void setAmount(String amount) {
        this.amount = amount;
        notifyPropertyChanged(BR.amount);
    }

    @Bindable
    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = String.valueOf(date);
        notifyPropertyChanged(BR.date);
    }

    @Bindable
    public String getCategory() {
        return Category;

    }

    public void setCategory(String category) {
        Category = category;
        notifyPropertyChanged(BR.category);
    }
}
