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

public class AddSavingsGoal extends AppCompatActivity {

    EditText name,goal,current,dateStart,endStart;
    Button addGoal, backBtn;
    String name1, goal1, current1, dateStart1, endStart1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_savings_goal);

        name=findViewById(R.id.nameGoalField);
        goal=findViewById(R.id.amountGoalField);
        current=findViewById(R.id.amountSaveField);
        dateStart=findViewById(R.id.dateStartField);
        endStart=findViewById(R.id.dateEndField);
        addGoal=findViewById(R.id.addSavingGoaltoDatabase);
        backBtn=findViewById(R.id.backBtn);

        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name1= String.valueOf(name.getText());
                goal1=String.valueOf(goal.getText());
                current1=String.valueOf(current.getText());
                dateStart1=String.valueOf(dateStart.getText());
                endStart1=String.valueOf(endStart.getText());

                if (!name1.isEmpty() && !goal1.isEmpty() && !current1.isEmpty() && !dateStart1.isEmpty() && !endStart1.isEmpty()) {
                    double goalValue = Double.parseDouble(goal1);
                    double currentValue = Double.parseDouble(current1);

                    SavingCategory savingCategory = new SavingCategory(name1, goalValue, currentValue, dateStart1, endStart1);
                    Toast.makeText(AddSavingsGoal.this, "Savings Goal Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddSavingsGoal.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();}


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), SavingsActivity.class);
                startActivity(j);
            }
        });


    }
}
