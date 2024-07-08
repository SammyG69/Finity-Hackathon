package com.example.finityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finityapp.Categories.SavingCategory;
import com.example.finityapp.Categories.SpendingCategory;

public class AddCategory extends AppCompatActivity {
     EditText name,limit,amount, date;
     Button backWeGo, addToData;
     String name1, limit1, amount1, date1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_category);

        name=findViewById(R.id.nameCategoryField);
        limit=findViewById(R.id.limitCategoryField);
        amount=findViewById(R.id.spentCategoryField);
        date=findViewById(R.id.dateBeginCategory);

        backWeGo=findViewById(R.id.backHomePls);
        addToData=findViewById(R.id.addCategorytoDatabase);

        addToData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name1= String.valueOf(name.getText());
                limit1=String.valueOf(limit.getText());
                amount1=String.valueOf(amount.getText());
                date1=String.valueOf(date.getText());


                if (!name1.isEmpty() && !limit1.isEmpty() && !date1.isEmpty() && !amount1.isEmpty()) {
                    double goalValue = Double.parseDouble(limit1);
                    double currentValue = Double.parseDouble(amount1);

                    SpendingCategory savingCategory = new SpendingCategory(name1, goalValue, currentValue, date1);
                    Toast.makeText(AddCategory.this, "Category Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCategory.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();}


            }
        });

        backWeGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), SavingsActivity.class);
                startActivity(j);
            }
        });

    }
}