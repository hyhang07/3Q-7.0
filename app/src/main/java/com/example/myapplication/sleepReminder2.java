package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class sleepReminder2 extends AppCompatActivity {

    Calendar calendar;
    MaterialTimePicker timePicker;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    TextView time;
    String timeStr;
    Button setTimeBtn,setReminderBtn,cancelReminderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_reminder2);
        createNotificationChannel();

        time=findViewById(R.id.tvselecttime);
        setTimeBtn=findViewById(R.id.btnsettime);
        setReminderBtn=findViewById(R.id.btnsetreminder);
        cancelReminderBtn=findViewById(R.id.btncancel);

        calendar=Calendar.getInstance();

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        setReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReminder();
            }
        });

        cancelReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReminder();
            }
        });


        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(sleepReminder2.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(sleepReminder2.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(sleepReminder2.this);
                    break;
                case 1:
                    toolbar.setting(sleepReminder2.this);
                    break;
            }
        }));
    }

    private void cancelReminder() {
        Intent intent=new Intent(this,ReminderReceiver.class);

        pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager==null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Reminder Cancelled",Toast.LENGTH_SHORT).show();
    }


    private void setReminder() {

        if (calendar == null) {
            Toast.makeText(this, "Please select a time first", Toast.LENGTH_SHORT).show();
            return;
        }

        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(this,ReminderReceiver.class);

        pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this,"Reminder set Successfully",Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0)
                .setMinute(0)
                .setTitleText("Select Reminder Time")
                .build();

        timePicker.show(getSupportFragmentManager(), "3qAndroid");

        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedTime = String.format("%02d", timePicker.getHour()) + " : " + String.format("%02d", timePicker.getMinute());
                time.setText(formattedTime);

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="3qAndroidReminderChannnel";
            String description="Channel for Sleep Reminder";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("3qAndroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}