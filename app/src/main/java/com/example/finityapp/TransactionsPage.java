package com.example.finityapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.databinding.ActivityMainBinding;
import com.example.finityapp.databinding.ListTransactionsBinding;
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

    DatabaseReference databaseReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_transactions);

        // Initialize the transactions list before using it
        transactions = new ArrayList<>();

        // Set up Firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Transactions");

        // Set up Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.list_transactions);

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
    }
}