package com.example.finityapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTransaction extends AppCompatActivity {
    Button button;
    EditText amountfield;
    ArrayList<String> categories;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    EditText dateField;

    Transaction transaction;

    FirebaseDatabase database;
    DatabaseReference dbReference;

    Button button2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_transaction);
        categories = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("SpendingCategories");

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categories.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String categoryName = dataSnapshot.getKey(); // Get the name of the child node
                    categories.add(categoryName);
                }
                adapterItem.notifyDataSetChanged(); // Notify adapter to update the dropdown list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItem = new ArrayAdapter<>(this, R.layout.option_list_category, categories);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AddTransaction.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        amountfield = findViewById(R.id.amountField);
        dateField = findViewById(R.id.dateField);
        button = findViewById(R.id.addButton);
        button2 = findViewById(R.id.home1Button);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), TransactionsPage.class);
                startActivity(j);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = amountfield.getText().toString();
                String date = dateField.getText().toString();
                String category = autoCompleteTextView.getText().toString();

                if (amount.isEmpty() || date.isEmpty() || category.isEmpty()) {
                    Toast.makeText(AddTransaction.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                database = FirebaseDatabase.getInstance();
                dbReference = database.getReference("Transactions");

                transaction = new Transaction(amount, date, category);

                dbReference.push().setValue(transaction)
                        .addOnSuccessListener(aVoid -> {
                            amountfield.setText("");
                            autoCompleteTextView.setText("");
                            dateField.setText("");

                            // Update the category amount directly here
                            DatabaseReference categoryRef = database.getReference("SpendingCategories").child(category).child("amount");
                            categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    double currentAmount = 0;
                                    if (snapshot.exists()) {
                                        currentAmount = snapshot.getValue(Double.class);
                                    }
                                    double updatedAmount = currentAmount + Double.parseDouble(amount);
                                    categoryRef.setValue(updatedAmount)
                                            .addOnSuccessListener(aVoid1 -> {
                                                Toast.makeText(AddTransaction.this, "Transaction added successfully", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(AddTransaction.this, "Failed to update category amount", Toast.LENGTH_SHORT).show();
                                            });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(AddTransaction.this, "Failed to read category amount", Toast.LENGTH_SHORT).show();
                                }
                            });

                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddTransaction.this, "Failed to add transaction", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}

