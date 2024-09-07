package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class sleepTimeNSuggestion extends AppCompatActivity {
    TextView sleepDurationtv,sleepResulttv,suggestiontv;
    String sleepDurationStr,sleepResultStr,suggestionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_time_nsuggestion);

        sleepDurationtv=findViewById(R.id.tvsleepduration);
        sleepResulttv=findViewById(R.id.tvsleephealth);
        suggestiontv=findViewById(R.id.tvsuggestion);

        sleepDurationStr=getIntent().getStringExtra("keySleepDuration");
        sleepResultStr=getIntent().getStringExtra("keyResult");
        suggestionStr=getIntent().getStringExtra("keySuggestion");

        sleepDurationtv.setText(sleepDurationStr);
        sleepResulttv.setText(sleepResultStr);
        suggestiontv.setText(suggestionStr);

        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(sleepTimeNSuggestion.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(sleepTimeNSuggestion.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(sleepTimeNSuggestion.this);
                    break;
                case 1:
                    toolbar.setting(sleepTimeNSuggestion.this);
                    break;
            }
        }));
    }
}