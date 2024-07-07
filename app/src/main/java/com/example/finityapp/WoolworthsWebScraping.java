package com.example.finityapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WoolworthsWebScraping extends AppCompatActivity {

    private static final String TAG = "WebScraping";
    TextView theText;
    String specificProductName = "Chicken Breast Lilydale"; // Change this to the product you are looking for

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_scraping);

        theText = findViewById(R.id.textColes);

        // Construct the search URL for Woolworths
        String searchUrl = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            searchUrl = "https://www.woolworths.com.au/shop/search/products?searchTerm=" + URLEncoder.encode(specificProductName, StandardCharsets.UTF_8);
        }
        Log.d(TAG, "Search URL: " + searchUrl);

        // Start the AsyncTask to perform the network operation
        new FetchDataTask().execute(searchUrl);
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();

            for (String url : urls) {
                try {
                    Log.d(TAG, "Connecting to URL: " + url);

                    // Use JSoup to get the connection
                    Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0");
                    Document document = connection.get();

                    Log.d(TAG, "Connection successful");

                    Elements productContainers = document.select("div.product-grid-v2--tile");

                    for (Element container : productContainers) {
                        Element nameElement = container.selectFirst("h4.product-title");
                        Element priceElement = container.selectFirst("span.price");

                        if (nameElement != null && priceElement != null) {
                            String name = nameElement.text();
                            String price = priceElement.text();
                            result.append(name).append(": ").append(price).append("\n");
                        } else {
                            Log.d(TAG, "Name or price element not found in container");
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IOException occurred", e);
                } catch (Exception e) {
                    Log.e(TAG, "An unexpected error occurred", e);
                }
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.isEmpty()) {
                theText.setText(result);
            } else {
                theText.setText("Error fetching data");
                Log.d(TAG, "Result is null or empty");
            }
        }
    }
}
