package com.example.myapplication;

import static com.example.myapplication.bmi.bmi_forsetting.BMI_FILENAMEFORSETTING;
import static com.example.myapplication.sleep.sleep_forsetting.SLEEP_FILENAMEFORSETTING;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class menu_setting extends AppCompatActivity {
    private Button about_us, sign_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_setting);



        Button deleteBMIButton = findViewById(R.id.delete_BMI_data);
        Button deleteSleepButton = findViewById(R.id.delete_sleep_data);
        Button deleteCalorieData = findViewById(R.id.delete_Calories_data);
        about_us = findViewById(R.id.about_us_button);
        sign_out = findViewById(R.id.sign_out_button);

        deleteBMIButton.setOnClickListener(v -> {
            deleteBMIData();
            Toast.makeText(menu_setting.this, "BMI data deleted", Toast.LENGTH_SHORT).show();
        });

        deleteSleepButton.setOnClickListener(v -> {
            // Logic to delete Sleep data
            deleteSleepData();

            // Show a Toast message to the user
            Toast.makeText(menu_setting.this, "Sleep data deleted", Toast.LENGTH_SHORT).show();

        });

        deleteCalorieData.setOnClickListener(v -> {
            deleteCalorieData();
            Toast.makeText(menu_setting.this, "Calories data deleted", Toast.LENGTH_SHORT).show();
        });

        about_us.setOnClickListener(v -> {
            Intent intent = new Intent(menu_setting.this, about_us.class);
            startActivity(intent);
        });

        sign_out.setOnClickListener(v -> {
            Intent intent = new Intent(menu_setting.this, LoginPage.class);
            startActivity(intent);
            finish();
        });
    }

    private void deleteBMIData() {
        new Thread(() -> {
            try {
                boolean deleted = deleteFile(BMI_FILENAMEFORSETTING);

                runOnUiThread(() -> {
                    if (deleted) {
                        Toast.makeText(menu_setting.this, "BMI data deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(menu_setting.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(menu_setting.this, "Error deleting BMI data", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void deleteCalorieData() {
        new Thread(() -> {
            try {
                File path = getExternalFilesDir(null);
                File file = new File(path, "food_data.txt");
                boolean deleted = file.delete();

                runOnUiThread(() -> {
                    if (deleted) {
                        Toast.makeText(menu_setting.this, "Calories data deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(menu_setting.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(menu_setting.this, "Error deleting calorie data", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    private void deleteSleepData() {
        new Thread(() -> {
            try {
                boolean deleted = deleteFile(SLEEP_FILENAMEFORSETTING);

                runOnUiThread(() -> {
                    if (deleted) {
                        Toast.makeText(menu_setting.this, "Sleep data deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(menu_setting.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(menu_setting.this, "Error deleting Sleep data", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


}
