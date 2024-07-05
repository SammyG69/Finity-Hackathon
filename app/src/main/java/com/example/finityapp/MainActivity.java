package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.finityapp.R;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


   Button button;
   Button button2;
   Button scanbutton;
   Button welcome;
   private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button2=findViewById(R.id.listButton);
        button=findViewById(R.id.transactionButton);
        scanbutton=findViewById(R.id.scanButton);
        welcome=findViewById(R.id.welcomePageButton);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f=new Intent(MainActivity.this, Activity_scanner.class);
                startActivity(f);
            }
        });

        Log.d(TAG, "onCreate: MainActivity started");


        Log.d(TAG, "We are back yo");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "onClick: Transaction button clicked");
                    Intent i = new Intent(

                            getApplicationContext(), AddTransaction.class
                    );

                    startActivity(i);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent t=new Intent(
                            getApplicationContext(), TransactionsPage.class
                    );
                    startActivity(t);
                }
            });

            welcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent f=new Intent(MainActivity.this, WelcomePage.class);
                    startActivity(f);
                }
            });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.savingNav) {
                openActivity(SavingActivity.class);
                return true;
            } else if (id == R.id.spendingNav) {
                openActivity(SavingsActivity.class);
                return true;
            } else if (id == R.id.transactionsNav) {
                openActivity(TransactionsPage.class);
                return true;
            } else if (id == R.id.scanningNav) {
                openActivity(Activity_scanner.class);
                return true;
            }
            return false;
        });

    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
        // Optional: Add transition animations
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}