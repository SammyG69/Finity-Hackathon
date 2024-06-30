package com.example.finityapp;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            FirebaseDatabase database= FirebaseDatabase.getInstance();

            DatabaseReference myref= database.getReference("messages");

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