package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class bmi extends AppCompatActivity {

    private static final String BMI_FILENAME = "bmi_data.txt";
    public static class bmi_forsetting {
        public static final String BMI_FILENAMEFORSETTING = "bmi_data.txt";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);

        EditText weightInput = findViewById(R.id.weightInput);
        EditText heightInput = findViewById(R.id.heightInput);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView bmiResult = findViewById(R.id.bmiResult);
        TextView bmiCategory = findViewById(R.id.bmiCategory);

        bmiResult.setText("");
        bmiCategory.setText("");

        // Toolbar setup (assuming toolbar is a class you have set up)
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(bmi.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(bmi.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(bmi.this);
                    break;
                case 1:
                    toolbar.setting(bmi.this);
                    break;
            }
        }));

        // Retrieve weight and height from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String weightStr = sharedPreferences.getString("weight", "");
        String heightStr = sharedPreferences.getString("height", "");

        // Set weight and height in EditTexts
        weightInput.setText(weightStr);
        heightInput.setText(heightStr);

        // Handle calculate button click
        calculateButton.setOnClickListener(v -> calculateAndDisplayBMI(weightInput, heightInput, bmiResult, bmiCategory));

           }

    private void calculateAndDisplayBMI(EditText weightInput, EditText heightInput, TextView bmiResult, TextView bmiCategory) {
        String weightStr = weightInput.getText().toString().trim();
        String heightStr = heightInput.getText().toString().trim();

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            bmiResult.setText(R.string.please_fill_fields);
            bmiCategory.setText("");
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            if (height <= 0) {
                bmiResult.setText(R.string.height_greater_zero);
                bmiCategory.setText("");
                return;
            }

            height = height / 100; // Convert height from cm to meters
            double bmi = calculateBMI(weight, height);
            String category = getBMICategory(bmi);

            bmiResult.setText(String.format(Locale.getDefault(), "BMI: %.2f", bmi));
            bmiCategory.setText(String.format(Locale.getDefault(), "Category: %s", category));

            // Save BMI data to file
            saveBMIDataToFile(bmi, category);

        } catch (NumberFormatException e) {
            bmiResult.setText(R.string.invalid_input);
            bmiCategory.setText("");
        } catch (Exception e) {
            bmiResult.setText(R.string.error_processing_bmi);
            bmiCategory.setText("");
            e.printStackTrace();
        }
    }

    private double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Normal weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    private void saveBMIDataToFile(double bmi, String category) {
        new Thread(() -> {
            FileOutputStream fos;
            OutputStreamWriter osw;
            try {
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                String data = String.format(Locale.getDefault(), "Date: %s, BMI: %.2f, Category: %s\n", currentTime, bmi, category);

                fos = openFileOutput(BMI_FILENAME, Context.MODE_APPEND);
                osw = new OutputStreamWriter(fos);
                osw.write(data);
                osw.flush();
                osw.close(); // Close the OutputStreamWriter to release resources

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(bmi.this, "Error saving BMI data", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    protected void onResume() {
        super.onResume();

        // Retrieve updated weight and height from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String weightStr = sharedPreferences.getString("weight", "");
        String heightStr = sharedPreferences.getString("height", "");

        // Set weight and height in EditTexts
        EditText weightInput = findViewById(R.id.weightInput);
        EditText heightInput = findViewById(R.id.heightInput);
        weightInput.setText(weightStr);
        heightInput.setText(heightStr);
    }



}
