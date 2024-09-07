package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoucherDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RedeemedVoucherAdapter redeemedVoucherAdapter;
    private List<Voucher> redeemedVoucherList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details);

        sharedPreferences = getSharedPreferences("Vouchers", MODE_PRIVATE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRedeemedVouchers();
        redeemedVoucherAdapter = new RedeemedVoucherAdapter(redeemedVoucherList);
        recyclerView.setAdapter(redeemedVoucherAdapter);
    }

    private void loadRedeemedVouchers() {
        redeemedVoucherList = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            int quantity = (Integer) entry.getValue();
            if (quantity > 0) {
                redeemedVoucherList.add(new Voucher(entry.getKey(), quantity));
            }
        }

        if (redeemedVoucherList.isEmpty()) {
            // Display "No voucher available" if list is empty
            findViewById(R.id.noVoucherText).setVisibility(View.VISIBLE);
        }
    }

    private class RedeemedVoucherAdapter extends RecyclerView.Adapter<RedeemedVoucherAdapter.RedeemedVoucherViewHolder> {

        private List<Voucher> vouchers;

        public RedeemedVoucherAdapter(List<Voucher> vouchers) {
            this.vouchers = vouchers;
        }

        @Override
        public RedeemedVoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.redeemed_voucher_item, parent, false);
            return new RedeemedVoucherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RedeemedVoucherViewHolder holder, int position) {
            Voucher voucher = vouchers.get(position);
            holder.voucherName.setText(voucher.getName());
            holder.quantity.setText(String.valueOf(voucher.getQuantity()));

            holder.useNowButton.setOnClickListener(v -> {
                int newQuantity = voucher.getQuantity() - 1;
                voucher.setQuantity(newQuantity);
                holder.quantity.setText(String.valueOf(newQuantity));
                if (newQuantity == 0) {
                    vouchers.remove(position);
                    notifyDataSetChanged();
                }
                saveVoucherData();
                if (vouchers.isEmpty()) {
                    findViewById(R.id.noVoucherText).setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return vouchers.size();
        }

        public class RedeemedVoucherViewHolder extends RecyclerView.ViewHolder {
            TextView voucherName, quantity;
            Button useNowButton;

            public RedeemedVoucherViewHolder(View itemView) {
                super(itemView);
                voucherName = itemView.findViewById(R.id.voucherName);
                quantity = itemView.findViewById(R.id.quantity);
                useNowButton = itemView.findViewById(R.id.useNowButton);
            }
        }
    }

    private void saveVoucherData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Voucher voucher : redeemedVoucherList) {
            editor.putInt(voucher.getName(), voucher.getQuantity());
        }
        editor.apply();
    }
}