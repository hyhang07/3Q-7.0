package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class about_us extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(about_us.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(about_us.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(about_us.this);
                    break;
                case 1:
                    toolbar.setting(about_us.this);
                    break;
            }
        }));
    }
}
