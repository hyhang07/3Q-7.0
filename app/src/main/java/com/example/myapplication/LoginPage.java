package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView loginButton;
    private TextView registerTextView;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerTextView = findViewById(R.id.textView2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegisterPage();
            }
        });

    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String registeredEmail = sharedPreferences.getString("email", null);
        String registeredPassword = sharedPreferences.getString("password", null);

        if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
            // Successful login
            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Unsuccessful login
            Toast.makeText(LoginPage.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToRegisterPage() {
        Intent intent = new Intent(LoginPage.this, RegisterPage.class);
        startActivity(intent);
    }
}