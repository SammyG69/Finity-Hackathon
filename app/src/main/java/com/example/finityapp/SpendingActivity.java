package com.example.finityapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.Categories.SavingCategory;
import com.example.finityapp.Categories.SpendingAdapter;
import com.example.finityapp.Categories.SpendingCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpendingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SpendingAdapter adapter;
    private ArrayList<SpendingCategory> spendingCategories;
    private DatabaseReference databaseReference; // Firebase Database reference

    private BottomNavigationView bottomNavigationView;

    private Button btnNew;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);


        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recyclerViewSpending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize saving categories data (example)
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SpendingCategories");

        // Initialize saving categories list
        spendingCategories = new ArrayList<>();
        loadSpendingCategories();

        btnNew=findViewById(R.id.addCategorySpending);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f=new Intent(
                        SpendingActivity.this, AddCategory.class
                );
                startActivity(f);
            }
        });


        /* nav bar */
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.savingNav) {
                openActivity(SavingsActivity.class);
                return true;
            } else if (id == R.id.spendingNav) {
                openActivity(SpendingActivity.class);
                return true;
            } else if (id == R.id.transactionsNav) {
                openActivity(TransactionsPage.class);
                return true;
            }
            return false;
        });
    }

    /** Loads all the Saving Categories from the firebase and initlaises the recycler view*/
    private void loadSpendingCategories() {
        // Attach a listener to read the data at SavingCategories reference
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String categoryName = snapshot.getKey();
                    double amount = snapshot.child("amount").getValue(Double.class);
                    double limit = snapshot.child("limit").getValue(Double.class);
                    String date = snapshot.child("date").getValue(String.class);

                    // Create SavingCategory object and add to list
                    SpendingCategory spendingCategory = new SpendingCategory(categoryName, limit, amount, date);
                    spendingCategories.add(spendingCategory);
                }

                // Initialize adapter with loaded data
                adapter = new SpendingAdapter(spendingCategories);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(SpendingActivity.this, activityClass);
        startActivity(intent);
        // Optional: Add transition animations
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
