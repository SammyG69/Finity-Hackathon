package com.example.finityapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions;
    private RecyclerView recyclerView;
    private MyAdapter userAdapter;
    private ActivityMainBinding binding;

    DatabaseReference databaseReference;
    FirebaseDatabase database;

   Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        binding= DataBindingUtil.setContentView(this, R.layout.item_card);


        database=FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Transactions");



        recyclerView=binding.recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //Fetch the data from firebase into recycler view
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for (DataSnapshot dataSnapshot: snapshot.getChildren() ){
                       Transaction transaction =dataSnapshot.getValue(Transaction.class);
                       transactions.add(transaction);
                   }

                    userAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        transactions=new ArrayList<>();
        userAdapter=new MyAdapter(this, transactions);
        recyclerView.setAdapter(userAdapter);

    }
}