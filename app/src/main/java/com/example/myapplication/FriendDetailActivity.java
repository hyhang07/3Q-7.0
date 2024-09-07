package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FriendDetailActivity extends AppCompatActivity {

    private TextView nameTextView, sleepTextView, bmiTextView, caloriesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        nameTextView = findViewById(R.id.nameTextView);
        sleepTextView = findViewById(R.id.sleepTextView);
        bmiTextView = findViewById(R.id.bmiTextView);
        caloriesTextView = findViewById(R.id.caloriesTextView);

        Intent intent = getIntent();
        Friend friend = intent.getParcelableExtra("friend");

        if (friend != null) {
            nameTextView.setText(friend.getName());
            sleepTextView.setText("Sleep: " + friend.getSleepHours() + " hours");
            bmiTextView.setText("BMI: " + friend.getBmi());
            caloriesTextView.setText("Calories: " + friend.getCalorieConsumption());
        }
    }
}
