package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calories_data extends AppCompatActivity {

    private CaloriesDataHelper caloriesDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories_data);


        Button datePickerButton = findViewById(R.id.datePickerButton);

        // Initialize the CaloriesDataHelper class
        caloriesDataHelper = new CaloriesDataHelper(this);

        // Set up the date picker button
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(calories_data.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(calories_data.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(calories_data.this);
                    break;
                case 1:
                    toolbar.setting(calories_data.this);
                    break;
            }
        }));
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date
                    String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);

                    // Refresh the data for the new selected date
                    caloriesDataHelper.displayDataForDate(selectedDate, findViewById(R.id.dateDataTextView));
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Helper class to manage calorie data
    private static class CaloriesDataHelper {
        private Context context;

        public CaloriesDataHelper(Context context) {
            this.context = context;
        }

        public void displayDataForDate(String selectedDate, TextView dateDataTextView) {
            List<String> dataForDate = new ArrayList<>();
            double totalCaloriesForDate = 0;
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "food_data.txt");

            if (!file.exists()) {
                dateDataTextView.setText("No data file found.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(selectedDate)) {
                        // Extract time from the line (assuming time is in the format HH:mm:ss after the date)
                        String time = line.substring(11, 19); // Assumes date is in the format yyyy-MM-dd and time starts at index 11
                        String entryWithTime = time + line.substring(19); // Append the rest of the line after time

                        dataForDate.add(entryWithTime);

                        // Extract calories from the line and add to totalCaloriesForDate
                        String[] parts = line.split("\\|");
                        if (parts.length > 2) {
                            String caloriesPart = parts[2].trim().replace("calories", "").trim();
                            try {
                                double calories = Double.parseDouble(caloriesPart);
                                totalCaloriesForDate += calories;
                            } catch (NumberFormatException e) {
                                e.printStackTrace(); // Handle the case where parsing fails
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check if any data was found for the selected date
            if (dataForDate.isEmpty()) {
                dateDataTextView.setText("No record found for this date.");
            } else {
                // Display data for the selected date with time only
                StringBuilder result = new StringBuilder();
                for (String entry : dataForDate) {
                    result.append(entry).append("\n");
                }
                result.append("\nTotal food calories: ").append(totalCaloriesForDate);
                dateDataTextView.setText(result.toString());
            }
        }
    }
}

