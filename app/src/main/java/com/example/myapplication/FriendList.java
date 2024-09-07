package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendList extends AppCompatActivity {

    private ImageView profileImageView;
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;
    private SharedPreferences sharedPreferences;
    private List<Friend> friendList;
    private TextView nameTextView;
    private Button addButton, changeProfileButton;
    private int[][] profileImages = {
            {R.drawable.avatar, R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6, R.drawable.default_person1, R.drawable.default_person3, R.drawable.account_avatar_profile_user_8_svgrepo_com, R.drawable.default_person4, R.drawable.default_person5, R.drawable.default_person6, R.drawable.alien, R.drawable.santa, R.drawable.jason, R.drawable.nun, R.drawable.einstein, R.drawable.president, R.drawable.geisha, R.drawable.actor, R.drawable.artist, R.drawable.joker, R.drawable.batman, R.drawable.anime, R.drawable.sheep, R.drawable.lazybones, R.drawable.avocado, R.drawable.cactus, R.drawable.default_person2},
            {R.drawable.default_person},
    };
    private int currentImageRow = 0;
    private int currentImageColumn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        recyclerView = findViewById(R.id.recyclerView);
        profileImageView = findViewById(R.id.profile);
        nameTextView = findViewById(R.id.name);
        changeProfileButton = findViewById(R.id.button);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "User");

        nameTextView.setText(username);

        currentImageRow = sharedPreferences.getInt("profile_image_row", 0);
        currentImageColumn = sharedPreferences.getInt("profile_image_column", 0);
        profileImageView.setImageResource(profileImages[currentImageRow][currentImageColumn]);

        friendList = new ArrayList<>();

        // Initialize FriendAdapter with context and friendList
        friendAdapter = new FriendAdapter(this, friendList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(friendAdapter);

        loadFriends();

        changeProfileButton.setOnClickListener(v -> showImageSelectionDialog());


        // Toolbar setup
        ShapeableImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> toolbar.navigateToMainActivity(FriendList.this));
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> toolbar.showOptionsDialog(FriendList.this, which -> {
            switch (which) {
                case 0:
                    toolbar.profile(FriendList.this);
                    break;
                case 1:
                    toolbar.setting(FriendList.this);
                    break;
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Friend newFriend = data.getParcelableExtra("friend");
            if (newFriend != null) {
                friendList.add(newFriend);
                friendAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendList.this);
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
            int row = position / profileImages[0].length;
            int column = position % profileImages[0].length;
            currentImageRow = row;
            currentImageColumn = column;
            profileImageView.setImageResource(profileImages[row][column]);

            // Save the selected image row and column to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("profile_image_row", currentImageRow);
            editor.putInt("profile_image_column", currentImageColumn);
            editor.apply();

            // Dismiss the dialog after selection
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    private void loadFriends(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs",MODE_PRIVATE);
        Set<String> friends = sharedPreferences.getStringSet("friends",new HashSet<>());

        if(friends !=null){
            for(String friendName : friends){
                Friend friend = new Friend("123",friendName,8,24,120);
                friendList.add(friend);
            }
        }
    }
}
