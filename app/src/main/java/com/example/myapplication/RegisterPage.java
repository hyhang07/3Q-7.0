package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class RegisterPage extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        ImageView registerButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(v -> registerUser());

        // Navigate to Login page when clicking "Already have an account? Sign in here"
        TextView signInTextView = findViewById(R.id.textView2);
        signInTextView.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Store user data in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("password", password);

            Set<String> registeredUsers = sharedPreferences.getStringSet("registered_users", new HashSet<>());
            registeredUsers.add(name);
            editor.putStringSet("registered_users", registeredUsers);

            editor.apply();

            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            // Navigate to Login page after successful registration
            Intent intent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(intent);
            finish();
        }
    }
}
