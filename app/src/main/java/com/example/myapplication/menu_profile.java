package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class menu_profile extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, weightEditText, heightEditText;
    private TextView emailTextView;
    private Spinner genderSpinner;
    private Button saveButton;
    private ShapeableImageView profileImageView;

    private int[][] profileImages = {
            {R.drawable.avatar, R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6, R.drawable.default_person1, R.drawable.default_person3, R.drawable.account_avatar_profile_user_8_svgrepo_com, R.drawable.default_person4, R.drawable.default_person5, R.drawable.default_person6, R.drawable.alien, R.drawable.santa, R.drawable.jason, R.drawable.nun, R.drawable.einstein, R.drawable.president, R.drawable.geisha, R.drawable.actor, R.drawable.artist, R.drawable.joker, R.drawable.batman, R.drawable.anime, R.drawable.sheep, R.drawable.lazybones, R.drawable.avocado, R.drawable.cactus, R.drawable.default_person2},
            {R.drawable.default_person},
    };
    private int currentImageRow = 0;
    private int currentImageColumn = 0;
    private int tempImageRow = 0;    // Temporary variables for selected image
    private int tempImageColumn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_profile);

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView);
        nameEditText = findViewById(R.id.nameEditText);
        emailTextView = findViewById(R.id.emailTextView);
        phoneEditText = findViewById(R.id.phoneEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        saveButton = findViewById(R.id.saveButton);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);

        // Load user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        String gender = sharedPreferences.getString("gender", "");
        String weight = sharedPreferences.getString("weight", "");
        String height = sharedPreferences.getString("height", "");

        // Display user data
        nameEditText.setText(name);
        emailTextView.setText(email);
        phoneEditText.setText(phone);
        weightEditText.setText(weight);
        heightEditText.setText(height);

        // Load profile image
        currentImageRow = sharedPreferences.getInt("profile_image_row", 0);
        currentImageColumn = sharedPreferences.getInt("profile_image_column", 0);
        profileImageView.setImageResource(profileImages[currentImageRow][currentImageColumn]);

        // Set up gender Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Set the gender if previously saved
        if (!gender.isEmpty()) {
            int spinnerPosition = adapter.getPosition(gender);
            genderSpinner.setSelection(spinnerPosition);
        }

        // Handle profile image change
        profileImageView.setOnClickListener(v -> showImageSelectionDialog());

        // Handle the save button click
        saveButton.setOnClickListener(v -> saveUserProfile());
    }

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(menu_profile.this);
        builder.setTitle("Select Profile Image");

        // Create a GridView and set the adapter
        GridView gridView = new GridView(this);
        gridView.setNumColumns(5); // Set the number of columns to 5
        ImageAdapter adapter = new ImageAdapter(this, profileImages);
        gridView.setAdapter(adapter);

        builder.setView(gridView);

        // Create the AlertDialog instance
        AlertDialog dialog = builder.create();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            tempImageRow = position / profileImages[0].length;
            tempImageColumn = position % profileImages[0].length;
            profileImageView.setImageResource(profileImages[tempImageRow][tempImageColumn]);

            // Dismiss the dialog after selection
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    private void saveUserProfile() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String weight = weightEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || gender.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Save the updated profile information
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("name", name);
            editor.putString("phone", phone);
            editor.putString("gender", gender);
            editor.putString("weight", weight); // Save weight
            editor.putString("height", height); // Save height

            // Save the selected profile image indices
            editor.putInt("profile_image_row", tempImageRow);
            editor.putInt("profile_image_column", tempImageColumn);

            editor.apply();

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // Update current image selection only after saving
            currentImageRow = tempImageRow;
            currentImageColumn = tempImageColumn;
        }
    }
}
