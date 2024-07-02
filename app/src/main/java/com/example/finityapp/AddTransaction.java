package com.example.finityapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTransaction extends AppCompatActivity {
        Button button;
        EditText amountfield;
        EditText categoryfield;
        EditText dateField;

        Transaction transaction;

        FirebaseDatabase database;
        DatabaseReference dbReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_transaction);

        amountfield=findViewById(R.id.amountField);
        categoryfield=findViewById(R.id.categoryField);
        dateField=findViewById(R.id.dateField);
        button=findViewById(R.id.addButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database=FirebaseDatabase.getInstance();
                dbReference= database.getReference("Transactions");

                String amount=amountfield.getText().toString();
                String date=dateField.getText().toString();
                String category=categoryfield.getText().toString();

                transaction=new Transaction(amount, date, category);

                dbReference.push().setValue(transaction)
                        .addOnSuccessListener(aVoid -> {
                            // Write was successful!
                            // You can show a success message or clear the input fields here
                            amountfield.setText("");
                            categoryfield.setText("");
                            dateField.setText("");
                        })
                        .addOnFailureListener(e -> {
                            // Write failed
                            // You can show an error message here
                        });
            }
        });
    }
}

