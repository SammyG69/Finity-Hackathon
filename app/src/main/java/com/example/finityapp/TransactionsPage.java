package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.databinding.ListTransactionsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionsPage extends AppCompatActivity {
    private ArrayList<Transaction> transactions;
    private RecyclerView recyclerView;
    private MyAdapter userAdapter;
    private ListTransactionsBinding binding;

    private BottomNavigationView bottomNavigationView;

    DatabaseReference databaseReference;
    FirebaseDatabase database;

    ImageButton button;

    private static final String TAG = "TransactionsPage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.list_transactions);

        // Initialize the transactions list before using it
        transactions = new ArrayList<>();

        // Set up Firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Transactions");

        // Set up RecyclerView
        recyclerView = binding.recyclerChicken;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up Adapter
        userAdapter = new MyAdapter(this, transactions);
        recyclerView.setAdapter(userAdapter);

        // Fetch data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the list before adding new data
                transactions.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Transaction transaction = dataSnapshot.getValue(Transaction.class);
                    transactions.add(transaction);
                }

                // Notify the adapter about data changes
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
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


        // Set up button click listener
        binding.scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Scan Receipt button clicked");

                Intent i = new Intent(TransactionsPage.this, Activity_scanner.class);
                startActivity(i);
                finish();
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Scan Receipt button clicked");

                Intent i = new Intent(TransactionsPage.this, AddTransaction.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(TransactionsPage.this, activityClass);
        startActivity(intent);
        // Optional: Add transition animations
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
