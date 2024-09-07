package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageButton menuButton, sleepbtn, drinkBtn, caloriesButton, bmiButton, quizButton, redeemButton, friendButton, recipeButton;
    private ViewPager2 viewPager;
    private int[] images = {R.mipmap.food_foreground,R.mipmap.food1_foreground};
    private ImageSliderAdapter adapter;
    private Handler handler = new Handler();
    private Runnable autoScrollRunnable;
    private int scrollInterval = 3000;
    private LineChart bmi_lineChart, sleepLineChart;
    private static final int BMI_REQUEST_CODE = 1;
    private static final int SLEEP_REQUEST_CODE=1;
    private List<Entry> bmiEntries;
    private List<Entry> sleepEntries;
    private WormDotsIndicator dotsIndicator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuButton = findViewById(R.id.menu_button);
        sleepbtn = findViewById(R.id.button);
        drinkBtn = findViewById(R.id.button2);
        caloriesButton = findViewById(R.id.calories_button);
        bmiButton = findViewById(R.id.bmi_button);
        quizButton = findViewById(R.id.quiz_button);
        redeemButton = findViewById(R.id.redeem_button);
        friendButton = findViewById(R.id.friend_button);
        recipeButton = findViewById(R.id.recipe_button);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        dotsIndicator = findViewById(R.id.dotsIndicator);



        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.food_foreground);
        images.add(R.mipmap.food1_foreground);

        List<String> urls = new ArrayList<>();
        urls.add("https://www.savorculinaryservices.com/nourishing-your-loved-ones-why-organic-foods-are-the-best-choice-for-your-family/");
        urls.add("https://betterme.world/articles/how-to-lose-15-pounds-in-2-weeks/");

        ImageSliderAdapter adapter = new ImageSliderAdapter(this,images,urls);
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if(nextItem >= adapter.getItemCount()){
                    nextItem = 0;
                }
                viewPager.setCurrentItem(nextItem,true);
            }
        };

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

        handler.postDelayed(runnable,3000);

        ///toolbar
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(MainActivity.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(MainActivity.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(MainActivity.this);
                    break;
                case 1:
                    toolbar.setting(MainActivity.this);
                    break;
            }
        }));




        sleepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, sleep.class);
                startActivityForResult(intent,SLEEP_REQUEST_CODE);
            }
        });

        drinkBtn.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, drinkWater.class);
            startActivity(intent);
        });

        caloriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, calories.class);
            startActivity(intent);
        });

        bmiButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, bmi.class);
            startActivityForResult(intent, BMI_REQUEST_CODE);
        });

        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuizPage.class);
                startActivity(intent);
            }
        });

        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Redeem.class);
                startActivity(intent);
            }
        });

        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainRecipe.class);
                startActivity(intent);
            }
        });

        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FriendList.class);
                startActivity(intent);
            }
        });


        bmi_lineChart = findViewById(R.id.bmi_graph);

        XAxis xAxis = bmi_lineChart.getXAxis();
        xAxis.setTextSize(11.5f);
        YAxis leftAxis = bmi_lineChart.getAxisLeft();
        leftAxis.setTextSize(15f);
        YAxis rightAxis = bmi_lineChart.getAxisRight();
        rightAxis.setTextSize(15f);
        bmi_lineChart.getLegend().setTextSize(15f);
        loadAndDisplayBMIData();

        sleepLineChart = findViewById(R.id.sleep_graph);

        XAxis xAxisS = sleepLineChart.getXAxis();
        xAxisS.setTextSize(11.5f);
        YAxis leftAxisS = bmi_lineChart.getAxisLeft();
        leftAxisS.setTextSize(15f);
        YAxis rightAxisS = bmi_lineChart.getAxisRight();
        rightAxisS.setTextSize(15f);
        sleepLineChart.getLegend().setTextSize(15f);
        loadAndDisplaySleepData();
    }



    ///function for bmi graph
    private void loadAndDisplayBMIData() {
        bmiEntries = new ArrayList<>();
        List<String> bmi_xValues = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("bmi_data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String datePart = parts[0].split(": ")[1].trim();
                String bmiPart = parts[1].split(": ")[1].trim();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date date = sdf.parse(datePart);
                float bmiValue = Float.parseFloat(bmiPart);

                // Adding entry to the chart
                bmiEntries.add(new Entry(index, bmiValue));
                bmi_xValues.add(new SimpleDateFormat("MM/dd", Locale.getDefault()).format(date));
                index++;
            }

            reader.close();

            // Creating a LineDataSet and setting it to the LineChart
            LineDataSet dataSet = new LineDataSet(bmiEntries, "BMI");
            dataSet.setColor(R.color.peach); // Customize line color
            dataSet.setValueTextColor(R.color.black); // Customize value text color
            dataSet.setValueTextSize(15f);
            dataSet.setLineWidth(8f);
            dataSet.setCircleRadius(8f);

            LineData lineData = new LineData(dataSet);
            bmi_lineChart.setData(lineData);

            // Customize X-axis labels with date
            bmi_lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return bmi_xValues.get((int) value);
                }
            });

            bmi_lineChart.invalidate(); // Refresh the chart

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAndDisplaySleepData() {
        sleepEntries = new ArrayList<>();
        List<String> sleep_xValues = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("sleepdata.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String datePart = parts[0].split(": ")[1].trim();
                String sleepTimePart = parts[1].split(": ")[1].trim();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date date = sdf.parse(datePart);
                long sleepValue = Long.parseLong(sleepTimePart);
                float sleepValueDouble=(float)sleepValue;
                float diffInHours = sleepValueDouble / (1000 * 60 * 60);
                String diffInHoursTD=String.format("%.2f",diffInHours);
                float diffInHoursTDFloat=Float.parseFloat(diffInHoursTD);

                // Adding entry to the chart
                sleepEntries.add(new Entry(index, diffInHoursTDFloat));
                sleep_xValues.add(new SimpleDateFormat("MM/dd", Locale.getDefault()).format(date));
                index++;
            }

            reader.close();

            // Creating a LineDataSet and setting it to the LineChart
            LineDataSet dataSet = new LineDataSet(sleepEntries, "Sleep Time");
            dataSet.setColor(R.color.blue); // Customize line color
            dataSet.setValueTextColor(R.color.black); // Customize value text color
            dataSet.setValueTextSize(15f);
            dataSet.setLineWidth(8f);
            dataSet.setCircleRadius(8f);

            LineData lineData = new LineData(dataSet);
            sleepLineChart.setData(lineData);

            // Customize X-axis labels with date
            sleepLineChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return sleep_xValues.get((int) value);
                }
            });

            sleepLineChart.invalidate(); // Refresh the chart

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void onResume() {
        super.onResume();
        loadAndDisplayBMIData();
        loadAndDisplaySleepData();
    }
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable);
    }
}
