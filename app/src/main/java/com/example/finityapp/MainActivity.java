package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


   Button button;
   Button button2;
   Button scanbutton, webScrape;
   Button welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button2=findViewById(R.id.listButton);
        button=findViewById(R.id.transactionButton);
        scanbutton=findViewById(R.id.scanButton);
        welcome=findViewById(R.id.welcomePageButton);
        webScrape=findViewById(R.id.web_scrape);

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

            webScrape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent j=new Intent(MainActivity.this, WebScraping.class);
                    startActivity(j);
                }
            });

            welcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent f=new Intent(MainActivity.this, WelcomePage.class);
                    startActivity(f);
                }
            });

    }
}