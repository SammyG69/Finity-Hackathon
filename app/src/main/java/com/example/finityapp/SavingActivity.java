package com.example.finityapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.Categories.SavingCategory;
import com.example.finityapp.Categories.SavingsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SavingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<SavingCategory> savingCategories;
    private SavingsAdapter adapter;

    private DatabaseReference databaseReference; // Firebase Database reference


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);
        
        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerViewSavings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize saving categories data (example)
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SavingCategories");

        // Initialize saving categories list
        savingCategories = new ArrayList<>();
        loadSavingCategories();

    }

    /** Loads all the Saving Categories from the firebase and initlaises the recycler view*/
    private void loadSavingCategories() {
        // Attach a listener to read the data at SavingCategories reference
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String categoryName = snapshot.getKey();
                    double goal = snapshot.child("goal").getValue(Double.class);
                    double saved = snapshot.child("saved").getValue(Double.class);
                    String startDate = snapshot.child("startDate").getValue(String.class);
                    String endDate = snapshot.child("endDate").getValue(String.class);

                    // Create SavingCategory object and add to list
                    SavingCategory savingCategory = new SavingCategory(categoryName, goal, saved, startDate, endDate);
                    savingCategories.add(savingCategory);
                    System.out.println(savingCategory);
                }

                // Initialize adapter with loaded data
                adapter = new SavingsAdapter(savingCategories);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}