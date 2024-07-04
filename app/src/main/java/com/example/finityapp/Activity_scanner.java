package com.example.finityapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;

public class Activity_scanner extends AppCompatActivity {
    Button captureBtn, button_copy;
    TextView resultIV, dateTV, amountTV;
    private static final int REQUEST_CAMERA_CODE = 100;
    Uri imageUri;

    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        captureBtn = findViewById(R.id.idButtonSnap);
        dateTV = findViewById(R.id.idDateText);
        amountTV = findViewById(R.id.idAmountText);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        if (ContextCompat.checkSelfPermission(Activity_scanner.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_scanner.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Activity_scanner.this)
                        .crop()                    // Crop image(Optional), Check Customization for more option
                        .compress(1024)            // Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    // Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            if (data != null) {
                imageUri = data.getData();
                if (imageUri != null) {
                    Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
                    try {
                        recognizeText();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void recognizeText() throws IOException {
        if (imageUri != null) {
            InputImage inputImage = InputImage.fromFilePath(Activity_scanner.this, imageUri);
            textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            String recognizeTxt = text.getText();
                            extractComponents(recognizeTxt);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_scanner.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void extractComponents(String text) {
        // Define regex patterns
        String datePattern = "\\b\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}\\b"; // Example pattern for dates
        String amountPattern = "\\b\\d+(?:\\.\\d{1,2})?\\b"; // Example pattern for amounts
        String totalPattern = "(?i)total\\s*[:$]?\\s*(\\d+(?:\\.\\d{1,2})?)"; // Pattern to find total amounts

        // Find date
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String date = matcher.group();
            dateTV.setText(date);
        }

        // Find total amount
        pattern = Pattern.compile(totalPattern);
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String totalAmount = matcher.group(1); // Get the capturing group that contains the amount
            amountTV.setText(totalAmount);
        } else {
            // If no total keyword is found, show a message (optional)
            amountTV.setText("Total not found");
        }
    }

}
