package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WelcomePage extends AppCompatActivity
{
    Button begin;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        begin=findViewById(R.id.buttonBegin);

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
}
