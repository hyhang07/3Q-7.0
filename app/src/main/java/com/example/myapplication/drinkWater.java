package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class drinkWater extends AppCompatActivity {
    Button drinkBtn,waterReminderBtn;
    TextView drunkWatertv,waterTimestv;
    String drunkWaterStr,waterTimesStr;
    EditText drinkWaterMLet;
    int totalWaterInt,waterTimesInt,day;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_water);

        SharedPreferences sharedPreferences= getSharedPreferences("Water",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        drinkBtn=findViewById(R.id.btndrink);
        drunkWatertv=findViewById(R.id.tvdrunkwater);
        drinkWaterMLet=findViewById(R.id.etdrinkwaterml);
        waterTimestv=findViewById(R.id.tvwatertimes);
        waterReminderBtn=findViewById(R.id.btnwaterreminder);



        drinkWaterMLet.addTextChangedListener(watcher);

        int waterVolumesp=sharedPreferences.getInt("Volume",0);
        int timessp=sharedPreferences.getInt("Times",0);
        int daysp=sharedPreferences.getInt("Day",0);
        waterTimesInt=timessp;
        totalWaterInt=waterVolumesp;
        day=daysp;
        calendar=Calendar.getInstance();
        int currentDay=calendar.get(Calendar.DAY_OF_MONTH);

        if (day!=currentDay)
        {
            editor.putInt("Volume",0);
            editor.putInt("Times",0);
            editor.putInt("Day",currentDay);
            editor.commit();
            waterVolumesp=sharedPreferences.getInt("Volume",0);
            timessp=sharedPreferences.getInt("Times",0);
            daysp=sharedPreferences.getInt("Day",0);
            waterTimesInt=timessp;
            totalWaterInt=waterVolumesp;
        }
        if (waterTimesInt==1)
            waterTimesStr=waterTimesInt+" Time";
        else
            waterTimesStr=waterTimesInt+" Times";
        waterTimestv.setText(waterTimesStr);

        drunkWaterStr=totalWaterInt+"ml";
        drunkWatertv.setText(drunkWaterStr);




        drinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (day!=currentDay)
                {
                    editor.putInt("Volume",0);
                    editor.putInt("Times",0);
                    editor.putInt("Day",currentDay);
                    editor.commit();
                }

                waterTimesInt++;
                if (waterTimesInt==1)
                    waterTimesStr=waterTimesInt+" Time";
                else
                    waterTimesStr=waterTimesInt+" Times";
                waterTimestv.setText(waterTimesStr);
                editor.putInt("Times",waterTimesInt);

                String waterStr=drinkWaterMLet.getText().toString();
                int waterInt=Integer.parseInt(waterStr);
                totalWaterInt=totalWaterInt+waterInt;
                drunkWaterStr=totalWaterInt+"ml";
                drunkWatertv.setText(drunkWaterStr);
                editor.putInt("Volume",totalWaterInt);
                editor.putInt("Day",currentDay);
                editor.commit();
            }
        });

        waterReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(drinkWater.this, drinkWaterReminder.class);
                startActivity(intent);
            }
        });

    }
    TextWatcher watcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String waterML=drinkWaterMLet.getText().toString().trim();

            drinkBtn.setEnabled(!waterML.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}