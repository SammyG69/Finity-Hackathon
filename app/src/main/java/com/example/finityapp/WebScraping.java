package com.example.finityapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraping extends AppCompatActivity {

    TextView txt, fullAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_scraping);
        txt = findViewById(R.id.textColes);
        fullAmount = findViewById(R.id.totalCost);

        Intent j = getIntent();
        String[] list = j.getStringArrayExtra("list");

        if (list != null && list.length > 0) {
            new FetchProductDetailsTask().execute(list);
        } else {
            txt.setText("No items to search.");
        }
    }

    private class FetchProductDetailsTask extends AsyncTask<String, Void, List<String>> {

        private double totalCost = 0.0;

        @Override
        protected List<String> doInBackground(String... items) {
            List<String> results = new ArrayList<>();
            for (String item : items) {
                String url = "https://salefinder.com.au/search/" + item.replace(" ", "%20");
                try {
                    // Connect to the website and get the HTML document
                    Document document = Jsoup.connect(url).get();

                    // Select the first product container (div with class 'item-landscape')
                    Element firstProductContainer = document.select("div.item-landscape").first();

                    if (firstProductContainer != null) {
                        // Extract specific details like title, price, and retailer name
                        String title = firstProductContainer.select("span.item-description").text();
                        String priceString = firstProductContainer.select("span.price").first().text();
                        double price = parsePrice(priceString);

                        Element retailerLogo = firstProductContainer.select("div.item-retailer-logo-top img").first();
                        String retailerName = retailerLogo != null ? retailerLogo.attr("alt") : "Unknown retailer";

                        results.add("Title: " + title + "\nPrice: " + priceString + "\nRetailer: " + retailerName);
                        totalCost += price;
                    } else {
                        results.add("No product container found for: " + item);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    results.add("Error fetching details for: " + item + "\nError: " + e.getMessage());
                }
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<String> results) {
            // Update the TextView with the fetched details
            StringBuilder resultText = new StringBuilder();
            for (String result : results) {
                resultText.append(result).append("\n\n");
            }
            txt.setText(resultText.toString());
            fullAmount.setText("Total Cost: $" + String.format("%.2f", totalCost));
        }

        private double parsePrice(String priceString) {
            // Remove any non-numeric characters except for the decimal point
            String price = priceString.replaceAll("[^\\d.]", "");
            try {
                return Double.parseDouble(price);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0.0;
            }
        }
    }
}
