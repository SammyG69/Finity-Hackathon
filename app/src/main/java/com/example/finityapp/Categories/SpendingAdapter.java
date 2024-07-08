package com.example.finityapp.Categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finityapp.R;

import java.util.ArrayList;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder>{
    private ArrayList<SpendingCategory> spendingCategories;

    public SpendingAdapter(ArrayList<SpendingCategory> spendingCategories) {
        this.spendingCategories = spendingCategories;
    }

    @NonNull
    @Override
    public SpendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spending_card, parent, false);
        return new SpendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingAdapter.ViewHolder holder, int position) {
        SpendingCategory spendingCategory = spendingCategories.get(position);

        holder.textViewTitle.setText(spendingCategory.getName());
        holder.textViewLimit.setText("Limit: $" + spendingCategory.getLimit());
        holder.textViewSpent.setText("Spent: $" + spendingCategory.getAmount());
        holder.textViewDate.setText("Date Started: " + spendingCategory.getDate());
    }

    @Override
    public int getItemCount() {
        return spendingCategories.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLimit;
        TextView textViewTitle;
        TextView textViewSpent;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewLimit = itemView.findViewById(R.id.textViewLimit);
            textViewSpent = itemView.findViewById(R.id.textViewSpent);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
