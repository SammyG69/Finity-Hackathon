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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            FirebaseDatabase database= FirebaseDatabase.getInstance();

            button=findViewById(R.id.transactionButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Scanner myObj1 = new Scanner(System.in);
                    System.out.println("Please enter your transaction date: ");
                    String date= myObj1.nextLine();
                    Scanner myObj2= new Scanner(System.in);
                    System.out.println("Please enter your amount and category that the purchase goes in: ");
                    String category= myObj2.nextLine();
                    double amount= myObj2.nextDouble();


                }
            });

            DatabaseReference myref= database.getReference("Transactions");

            myref.setValue("hello from our course");

            myref.addValueEventListener(new ValueEventListener() {
                TextView textview=findViewById(R.id.textView);
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String value =snapshot.getValue(String.class);
                    textview.setText(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            return insets;
        });
    }
}