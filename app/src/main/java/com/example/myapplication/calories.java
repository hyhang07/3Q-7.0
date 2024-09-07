package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class calories extends AppCompatActivity {

    private EditText weightInput, heightInput, ageInput, manualFoodInput, manualCalorieInput;
    private Spinner activityLevelSpinner, foodSpinner;
    private TextView resultText;
    private Button goToDataButton;

    private double totalCaloriesConsumed = 0;
    private TableLayout foodLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        ageInput = findViewById(R.id.ageInput);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);
        foodSpinner = findViewById(R.id.foodSpinner);
        manualCalorieInput = findViewById(R.id.manualCalorieInput);
        manualFoodInput = findViewById(R.id.manualFoodInput); // Initialize manualFoodInput

        resultText = findViewById(R.id.resultText);
        Button calculateButton = findViewById(R.id.calculateButton);
        Button addFoodButton = findViewById(R.id.addFoodButton);
        goToDataButton = findViewById(R.id.goToDataButton);

        foodLayout = findViewById(R.id.foodLayout);

        goToDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(calories.this, calories_data.class);
            startActivity(intent);
        });
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadFoodItems(todayDate);


        // Set up the custom spinner adapter
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item, // Custom layout with white text color
                getResources().getStringArray(R.array.activity_levels)
        );
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityAdapter);

        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFood = foodSpinner.getSelectedItem().toString();

                if (position == 0) {
                    manualCalorieInput.setVisibility(View.GONE);
                    manualFoodInput.setVisibility(View.GONE);
                }

                if (selectedFood.equals("Key-In Food Manually")) {
                    manualCalorieInput.setVisibility(View.VISIBLE);
                    manualFoodInput.setVisibility(View.VISIBLE);
                } else {
                    manualCalorieInput.setVisibility(View.GONE);
                    manualFoodInput.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        setupFoodSpinner();

        addFoodButton.setOnClickListener(v -> addFood());

        calculateButton.setOnClickListener(v -> calculateBMRAndAMR());

        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(calories.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(calories.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(calories.this);
                    break;
                case 1:
                    toolbar.setting(calories.this);
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

    }


    //calculate BMR and AMR
    private void calculateBMRAndAMR() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();
        String ageStr = ageInput.getText().toString();

        if (weightStr.isEmpty() || heightStr.isEmpty() || ageStr.isEmpty()) {
            resultText.setText("Please fill out all fields");
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);
        int age = Integer.parseInt(ageStr);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String gender = sharedPreferences.getString("gender", "");

        if (gender.isEmpty()) {
            resultText.setText("Please set your gender in profile");
            return;
        }

        double bmr;
        if (gender.equals("Male")) {
            bmr = 66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age);
        } else {
            bmr = 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);
        }

        int selectedPosition = activityLevelSpinner.getSelectedItemPosition();
        if (selectedPosition == 0) {
            resultText.setText("Please select your activity level");
            return;
        }

        String activityLevel = activityLevelSpinner.getSelectedItem().toString();
        double amr = calculateAMR(bmr, activityLevel);

        String result = String.format("Your BMR: %.2f\nYour AMR: %.2f", bmr, amr);
        resultText.setText(result);
    }

    private double calculateAMR(double bmr, String activityLevel) {
        switch (activityLevel) {
            case "Sedentary (little or no exercise)":
                return bmr * 1.2;
            case "Lightly active (exercise 1–3 days/week)":
                return bmr * 1.375;
            case "Moderately active (exercise 3–5 days/week)":
                return bmr * 1.55;
            case "Active (exercise 6–7 days/week)":
                return bmr * 1.725;
            case "Extremely active (hard exercise 6–7 days/week)":
                return bmr * 1.9;
            default:
                return bmr;
        }
    }











/////add food
    private void setupFoodSpinner() {
        ArrayAdapter<String> foodAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item, // Custom layout with white text color
                getResources().getStringArray(R.array.food_items)
        );
        foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodSpinner.setAdapter(foodAdapter);
    }

    private void addFood() {
        int selectedPosition = foodSpinner.getSelectedItemPosition();
        String selectedFood = foodSpinner.getSelectedItem().toString();
        String manualCaloriesStr = manualCalorieInput.getText().toString();
        double calories = 0;

        if (selectedPosition == 0) {
            // The first item is the hint, show a message to select a valid food item
            Toast.makeText(this, "Please select a food item.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFood.equals("Key-In Food Manually")) {
            selectedFood = manualFoodInput.getText().toString().toUpperCase();
            if (selectedFood.isEmpty()) {
                Toast.makeText(this, "Please enter the food name.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!manualCaloriesStr.isEmpty()) {
                calories = Double.parseDouble(manualCaloriesStr);
                Toast.makeText(this, "Added successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter calories for the food.", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            calories = getCaloriesForFood(selectedFood);
            Toast.makeText(this, "Added successfully.", Toast.LENGTH_SHORT).show();
        }

        totalCaloriesConsumed += calories;

        // Store data in a text file with the current timestamp
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String entry = timeStamp + " | " + selectedFood + " | " + calories + " calories\n";
        saveDataToFile(entry);

        // Clear manual input after adding
        manualCalorieInput.setText("");
        manualFoodInput.setText("");

        recreate();
    }


    private void saveDataToFile(String data) {
        File path = getExternalFilesDir(null);
        File file = new File(path, "food_data.txt");

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getCaloriesForFood(String food) {
        switch (food) {
            case "Hamburger":
                return 250; // Example calorie value
            case "Salad":
                return 150;
            case "Pizza":
                return 300;
            default:
                return 0;
        }
    }


    public class FoodItem {
        private String name;
        private double calories;

        public FoodItem(String name, double calories) {
            this.name = name;
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public double getCalories() {
            return calories;
        }
    }

    private void loadFoodItems(String date) {
        File path = getExternalFilesDir(null);
        File file = new File(path, "food_data.txt");

        if (file.exists()) {
            List<FoodItem> foodItems = readFoodDataForDate(date);
            populateFoodTable(foodItems);
        } else {
            // Handle the case where the file doesn't exist
            populateFoodTable(Collections.emptyList()); // Pass an empty list to trigger "No record found"
        }
    }

    private List<FoodItem> readFoodDataForDate(String date) {
        Map<String, Double> foodMap = new HashMap<>();  // Map to aggregate food items
        File path = getExternalFilesDir(null);
        File file = new File(path, "food_data.txt");

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.contains(date)) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 3) {
                            String name = parts[1].trim();
                            double calories = Double.parseDouble(parts[2].replace("calories", "").trim());

                            // Aggregate calories for the same food item
                            foodMap.put(name, foodMap.getOrDefault(name, 0.0) + calories);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Convert map to list of FoodItem
        List<FoodItem> foodItems = new ArrayList<>();
        for (Map.Entry<String, Double> entry : foodMap.entrySet()) {
            foodItems.add(new FoodItem(entry.getKey(), entry.getValue()));
        }

        return foodItems;
    }



    private void populateFoodTable(List<FoodItem> foodItems) {
        foodLayout.removeAllViews(); // Clear existing rows

        if (foodItems.isEmpty()) {
            // Show no data available message
            TextView noDataView = new TextView(this);
            noDataView.setText("No record found for this date.");
            noDataView.setPadding(8, 8, 8, 8);
            noDataView.setTextSize(20);
            noDataView.setGravity(Gravity.CENTER);
            noDataView.setTextColor(Color.RED); // You can choose a different color
            foodLayout.addView(noDataView);
        } else {
            double dailyTotalCalories = 0; // Reset daily total calories

            // Populate table rows with food data
            for (FoodItem item : foodItems) {
                TableRow row = new TableRow(this);

                TextView foodName = new TextView(this);
                foodName.setText(item.getName());
                foodName.setPadding(8, 8, 8, 8);
                foodName.setGravity(Gravity.CENTER);
                foodName.setTextSize(18);
                row.addView(foodName);

                TextView caloriesView = new TextView(this);
                caloriesView.setText(String.valueOf(item.getCalories()));
                caloriesView.setPadding(8, 8, 8, 8);
                caloriesView.setGravity(Gravity.CENTER);
                caloriesView.setTextSize(18);
                row.addView(caloriesView);

                dailyTotalCalories += item.getCalories();
                TextView totalCaloriesView = new TextView(this);
                totalCaloriesView.setText(String.valueOf(dailyTotalCalories));
                totalCaloriesView.setPadding(8, 8, 45, 8);
                totalCaloriesView.setGravity(Gravity.CENTER);
                totalCaloriesView.setTextSize(18);
                row.addView(totalCaloriesView);

                foodLayout.addView(row);
            }
        }
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
