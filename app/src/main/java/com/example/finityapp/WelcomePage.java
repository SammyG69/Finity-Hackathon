package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WelcomePage extends AppCompatActivity
{
    Button begin;
    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        db = FirebaseFirestore.getInstance();

        begin=findViewById(R.id.buttonBegin);

        getUserCount();

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j=new Intent(
                        WelcomePage.this, SavingsActivity.class
                );

                startActivity(j);
            }
        });

    }

    private void getUserCount() {
        final DocumentReference userCountRef = db.collection("appData").document("userCount");

        userCountRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                long count = documentSnapshot.getLong("count");
                // Display user count in your app
                TextView userCountTextView = findViewById(R.id.userCountTextView);
                userCountTextView.setText("User Count: " + count);
            }
        }).addOnFailureListener(e -> {
            // Handle failure
        });
    }
}
