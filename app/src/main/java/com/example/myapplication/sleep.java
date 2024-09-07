package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class sleep extends AppCompatActivity {
    private static final String sleepData = "sleepdata.txt";

    TextView startTime,endTime;
    Button calculateSleepTimeBtn, setSleepReminderBtn;
    String startTimeStr, endTimeStr, sleepTimeStr, suggestion, sleepResult;
    int sleepHoursInt;

    long sleepTimelong;

    public class sleep_forsetting {
        public static final String SLEEP_FILENAMEFORSETTING = "sleepdata.txt";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        startTime=findViewById(R.id.tvstarttime);
        endTime=findViewById(R.id.tvendtime);
        calculateSleepTimeBtn=findViewById(R.id.btncalculate);
        setSleepReminderBtn=findViewById(R.id.btnreminder);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int minutes=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(sleep.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hoursOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hoursOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm");
                        startTimeStr = format.format(c.getTime());
                        startTime.setText(startTimeStr);
                        startTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                },hours,minutes,true);
                timePickerDialog.show();
            }

        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int minutes=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(sleep.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hoursOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hoursOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm");
                        endTimeStr = format.format(c.getTime());
                        endTime.setText(endTimeStr);
                        endTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                },hours,minutes,true);
                timePickerDialog.show();
            }
        });

        calculateSleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calculate sleep time
                try {
                    SimpleDateFormat format = new SimpleDateFormat("k:mm");

                    // Parse the start and end times from the TextViews
                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.setTime(format.parse(startTimeStr));

                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(format.parse(endTimeStr));

                    // adjust for cases where end time is on the next day
                    if (endCalendar.before(startCalendar)) {
                        endCalendar.add(Calendar.DATE, 1);
                    }

                    // calculate the difference in milliseconds
                    long diffInMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
                    sleepTimelong=diffInMillis;

                    // Convert milliseconds to hours and minutes
                    long diffInHours = diffInMillis / (1000 * 60 * 60);
                    long diffInMinutes = (diffInMillis / (1000 * 60)) % 60;

                    // save the sleep duration
                    sleepHoursInt= (int) diffInHours;
                    sleepTimeStr = diffInHours + " hours and " + diffInMinutes + " minutes";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sleepHoursInt<4){
                    sleepResult="Severe Sleep Deprivation";
                    suggestion="Immediate Rest: You are severely sleep-deprived. If possible, take a nap as soon as you can.\n\n" +
                            "Caution: Avoid activities that require high concentration, such as driving.\n\n" +
                            "Long-Term: Consistently getting less than 4 hours of sleep can lead to serious health issues. Consider revising your schedule to prioritize sleep.";
                } else if (sleepHoursInt==4) {
                    sleepResult="Sleep Deprivation";
                    suggestion="Short Nap: Try to take a short nap (20-30 minutes) during the day to boost alertness.\n\n" +
                            "Hydration and Nutrition: Stay hydrated and eat small, balanced meals to maintain energy levels.\n\n" +
                            "Schedule Adjustment: Aim to gradually increase your sleep duration by going to bed earlier.";
                } else if (sleepHoursInt==5) {
                    sleepResult="Insufficient Sleep";
                    suggestion="Nap Opportunity: If you feel tired, a brief nap might help improve your focus.\n\n" +
                            "Relaxation Techniques: Practice relaxation techniques before bed, like deep breathing or meditation, to improve sleep quality.\n\n" +
                            "Consistency: Try to go to bed at the same time every night to improve sleep duration.";
                } else if (sleepHoursInt==6) {
                    sleepResult="Below Recommended Amount";
                    suggestion="Mindfulness: Ensure that your sleep is restful. If you still feel tired, consider improving your sleep environment (e.g., dark, cool room).\n\n" +
                            "Physical Activity: Engage in regular physical activity, but avoid vigorous exercise close to bedtime.\n\n" +
                            "Sleep Hygiene: Follow good sleep hygiene practices, like avoiding screens before bed.";
                }else if(sleepHoursInt==7){
                    sleepResult="Adequate Sleep (Ideal Range)";
                    suggestion="Consistency: Maintain a consistent sleep schedule to keep your body’s internal clock in sync.\n\n" +
                            "Quality Check: If you wake up feeling refreshed, you’re on the right track. If not, consider assessing factors like stress or diet.\n\n" +
                            "Healthy Habits: Continue engaging in healthy habits like regular exercise and a balanced diet to support good sleep.";
                } else if (sleepHoursInt==8) {
                    sleepResult="Optimal Sleep";
                    suggestion="Routine: Keep up with your current routine, as this sleep duration is generally optimal for most adults.\n\n" +
                            "Awareness: Be aware of oversleeping. If you still feel tired after 8-9 hours, it might indicate an underlying issue like poor sleep quality or a sleep disorder.\n\n" +
                            "Mental Wellness: Focus on mental wellness activities like mindfulness or light yoga to enhance overall well-being.";
                } else {
                    sleepResult="Possible Oversleeping";
                    suggestion="Health Check: While occasional longer sleep durations are normal, consistently sleeping more than 9 hours could indicate underlying health issues like depression or sleep disorders.\n\n" +
                            "Energy Levels: If you feel sluggish despite longer sleep, it might be worth consulting a healthcare professional.\n\n" +
                            "Balanced Lifestyle: Ensure that you balance sleep with physical activity and mental stimulation during the day.";
                }

                saveSleepData(sleepTimelong);

                Intent intent=new Intent(sleep.this, sleepTimeNSuggestion.class);
                intent.putExtra("keySleepDuration",sleepTimeStr);
                intent.putExtra("keyResult",sleepResult);
                intent.putExtra("keySuggestion",suggestion);
                startActivity(intent);
            }

        });

        setSleepReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(sleep.this, sleepReminder2.class);
                startActivity(intent);
            }
        });


        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(sleep.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(sleep.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(sleep.this);
                    break;
                case 1:
                    toolbar.setting(sleep.this);
                    break;
            }
        }));
    }

    private void saveSleepData(long sleepTimelong2 ) {
        new Thread(() -> {


            FileOutputStream fos;
            OutputStreamWriter osw;
            try {
                // Get the current date and time
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                // Prepare the data to be saved
                String data = String.format(Locale.getDefault(), "Date: %s, SleepTime: %s\n", currentTime, sleepTimelong2);

                // Open the file in append mode and write the data
                fos = openFileOutput(sleepData, Context.MODE_APPEND);
                osw = new OutputStreamWriter(fos);
                osw.write(data);
                osw.flush();



            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(sleep.this, "Error saving sleep data", Toast.LENGTH_SHORT).show());

            }
        }).start();
    }
}