package com.example.finityapp.Categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.Categories.SavingCategory;
import com.example.finityapp.R;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.ViewHolder> {

    private ArrayList<SavingCategory> savingCategories;

    public SavingsAdapter(ArrayList<SavingCategory> savingCategories) {
        this.savingCategories = savingCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savings_goal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavingCategory savingCategory = savingCategories.get(position);

        holder.textViewTitle.setText(savingCategory.getName());
        holder.textViewGoal.setText("Goal: $" + savingCategory.getGoal());
        holder.textViewSaved.setText("Saved: $" + savingCategory.getSaved());
        holder.textViewDates.setText("Dates: " + savingCategory.getStartDate() + " to " + savingCategory.getEndDate());
    }

    @Override
    public int getItemCount() {
        return savingCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSaved;
        TextView textViewTitle;
        TextView textViewGoal;
        TextView textViewDates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewGoal = itemView.findViewById(R.id.textViewGoal);
            textViewSaved = itemView.findViewById(R.id.textViewSaved);
            textViewDates = itemView.findViewById(R.id.textViewDates);
        }
    }
}
