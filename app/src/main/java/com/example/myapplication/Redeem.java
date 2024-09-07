package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Redeem extends AppCompatActivity {

    private TextView backTextView, pointTextView, streakCountTextView;
    private RecyclerView checkInRecyclerView, rewardRecyclerView;
    private Button viewButton;
    private int points = 0;
    private int checkInCount = 0; // Keeps track of the check-in days
    private CheckInAdapter checkInAdapter;
    private List<Boolean> checkInItems;
    private SharedPreferences sharedPreferences,voucherPreferences;
    private static final String PREFS_NAME = "CheckInPrefs";
    private static final String STREAK_COUNT = "streakCount";
    private static final String LAST_CHECKIN_DATE = "lastCheckInDate";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private List<Voucher> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        // Initialize views
        backTextView = findViewById(R.id.back);
        pointTextView = findViewById(R.id.point);
        checkInRecyclerView = findViewById(R.id.checkinRecyclerView);
        rewardRecyclerView = findViewById(R.id.recyclerView);
        rewardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        streakCountTextView = findViewById(R.id.streak_count);
        viewButton = findViewById(R.id.viewButton);

        // Initialize SharedPreferences
        voucherPreferences = getSharedPreferences("Vouchers",MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        checkInCount = sharedPreferences.getInt(STREAK_COUNT, 0);
        String lastCheckInDate = sharedPreferences.getString(LAST_CHECKIN_DATE, "");

        // Restore points from previous sessions if necessary
        points = sharedPreferences.getInt("points", 0);
        pointTextView.setText(String.valueOf(points));

        streakCountTextView.setText("Streak: " + checkInCount + " Days");

        // Handle back button click
        backTextView.setOnClickListener(v -> onBackPressed());

        // Setup RecyclerView with GridLayoutManager
        checkInRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        checkInItems = getCheckInItems(); // Initialize the check-in items
        checkInAdapter = new CheckInAdapter(checkInItems);
        checkInRecyclerView.setAdapter(checkInAdapter);

        // Handle check-in button click
        findViewById(R.id.checkin_button).setOnClickListener(v -> handleCheckIn(lastCheckInDate));

       loadVouchers();
       VoucherAdapter voucherAdapter = new VoucherAdapter(voucherList);
       rewardRecyclerView.setAdapter(voucherAdapter);
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(Redeem.this, VoucherDetailsActivity.class);
            intent.putParcelableArrayListExtra("vouchers", new ArrayList<>(voucherList));
            startActivity(intent);
        });
    }

    private void loadVouchers(){
        voucherList = new ArrayList<>();
        voucherList.add(new Voucher("Pizza Hut",5));
        voucherList.add(new Voucher("KFC",3));
    }

    // This method handles the check-in logic
    private void handleCheckIn(String lastCheckInDate) {
        String todayDate = dateFormat.format(new Date());

        if (!todayDate.equals(lastCheckInDate)) {
            if (isConsecutiveDay(lastCheckInDate, todayDate)) {
                checkInCount++;  // Increment streak
            } else {
                checkInCount = 1;  // Reset streak if not consecutive
            }

            // Award points for each check-in
            points += 100;

            // Save the new streak count and the current date
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(STREAK_COUNT, checkInCount);
            editor.putString(LAST_CHECKIN_DATE, todayDate);
            editor.putInt("points", points);
            editor.apply();

            // Update UI
            pointTextView.setText(String.valueOf(points));
            streakCountTextView.setText("Streak: " + checkInCount + " Days"); // Update streak count display
            checkInItems.set(checkInCount - 1, true); // Mark the current day as checked in
            checkInAdapter.notifyDataSetChanged();

            // Check if the user has completed a 7-day streak
            if (checkInCount == 7) {
                points += 100; // Extra 100 points for 7-day streak
                pointTextView.setText(String.valueOf(points));
                editor.putInt("points", points);
                editor.apply();
                Toast.makeText(Redeem.this, "7-Day Streak! You earned an extra 100 points!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Redeem.this, "Checked in! You earned 10 points.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Redeem.this, "You have already checked in today!", Toast.LENGTH_SHORT).show();
        }
    }

    // This method generates a list of check-in items for the RecyclerView
    private List<Boolean> getCheckInItems() {
        List<Boolean> checkInItems = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            checkInItems.add(i <= checkInCount); // Mark previous days as checked in
        }
        return checkInItems;
    }

    // Helper method to check if two dates are consecutive
    private boolean isConsecutiveDay(String lastCheckInDate, String todayDate) {
        try {
            Date lastDate = dateFormat.parse(lastCheckInDate);
            Date currentDate = dateFormat.parse(todayDate);

            if (lastDate != null && currentDate != null) {
                long diff = currentDate.getTime() - lastDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                return diffDays == 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>{
        private List<Voucher> vouchers;

        public VoucherAdapter(List<Voucher> vouchers){
            this.vouchers = vouchers;
        }

        @Override
        public VoucherViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item,parent,false);
            return new VoucherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(VoucherViewHolder holder, int position){
            Voucher voucher = vouchers.get(position);
            holder.voucherName.setText(voucher.getName());
            holder.quantity.setText(String.valueOf(voucher.getQuantity()));

            if(voucher.getQuantity() == 0){
                holder.redeemButton.setEnabled(false);
            }else{
                holder.redeemButton.setOnClickListener(v -> {
                    int newQuantity = voucher.getQuantity() - 1;
                    voucher.setQuantity(newQuantity);
                    holder.quantity.setText(String.valueOf(newQuantity));
                    saveVoucherData(voucher.getName(),newQuantity);
                    if(newQuantity == 0){
                        holder.redeemButton.setEnabled(false);
                    }
                });
            }
        }

        public int getItemCount(){
            return vouchers.size();
        }

        public class VoucherViewHolder extends RecyclerView.ViewHolder{
            TextView voucherName, quantity;
            Button redeemButton;

            public VoucherViewHolder(View itemView){
                super(itemView);
                voucherName = itemView.findViewById(R.id.voucherName);
                quantity = itemView.findViewById(R.id.quantity);
                redeemButton = itemView.findViewById(R.id.redeemButton);
            }
        }
    }

    private void saveVoucherData(String voucherName,int quantity){
        SharedPreferences.Editor editor = voucherPreferences.edit();
        editor.putInt(voucherName,quantity);
        editor.apply();
    }
}
