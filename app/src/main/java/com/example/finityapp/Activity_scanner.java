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
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class Activity_scanner extends AppCompatActivity {
    Button captureBtn, saveBtn;
    TextView itemsTV;
    private static final int REQUEST_CAMERA_CODE = 100;
    Uri imageUri;

    TextRecognizer textRecognizer;

    // String array to store the items
    String[] itemsArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        captureBtn = findViewById(R.id.idButtonSnap);
        itemsTV = findViewById(R.id.idDateText);
        saveBtn=findViewById(R.id.saveNow);



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
                            String recognizedText = text.getText();
                            if (recognizedText != null && !recognizedText.isEmpty()) {
                                // Split the text by line breaks
                                String[] lines = recognizedText.split("\\r?\\n");
                                itemsArray = lines;

                                saveBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent i=new Intent(
                                                Activity_scanner.this, WebScraping.class
                                        );

                                        i.putExtra("list", itemsArray);

                                        startActivity(i);

                                    }
                                });


                                // Optionally, display the lines in the TextView
                                StringBuilder displayText = new StringBuilder();
                                for (String line : lines) {
                                    displayText.append(line).append("\n");
                                }
                                itemsTV.setText(displayText.toString());
                            } else {
                                Toast.makeText(Activity_scanner.this, "No text recognized", Toast.LENGTH_SHORT).show();
                            }
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
}

