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
    String specificProductName = "Golden Crumpets"; // Change this to the product you are looking for

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_scraping);

        theText = findViewById(R.id.textColes);

        // Construct the search URL
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

                    // Use Jsoup to get a connection
                    Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0");
                    Document document = connection.get();

                    Log.d(TAG, "Connection successful");

                    // Select the product containers
                    Elements productContainers = document.select("shared-product-tile.ng-star-inserted");

                    Log.d(TAG, "Found " + productContainers.size() + " product containers");

                    for (Element container : productContainers) {
                        Log.d(TAG, "Processing container: " + container.html());

                        Element nameElement = container.selectFirst("h3.shelfProductTile-descriptionLink");
                        Element priceElement = container.selectFirst("span.price");

                        if (nameElement != null) {
                            Log.d(TAG, "Product Name Element Found: " + nameElement.text());
                        } else {
                            Log.d(TAG, "Product Name Element Not Found");
                        }

                        if (priceElement != null) {
                            Log.d(TAG, "Product Price Element Found: " + priceElement.text());
                        } else {
                            Log.d(TAG, "Product Price Element Not Found");
                        }

                        if (nameElement != null && priceElement != null) {
                            String name = nameElement.text();
                            String price = priceElement.text();
                            result.append(name).append(": ").append(price).append("\n");
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

