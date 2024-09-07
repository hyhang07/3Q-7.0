package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RedeemedVoucherAdapter extends RecyclerView.Adapter<RedeemedVoucherAdapter.RedeemedVoucherViewHolder> {

    private List<Voucher> vouchers;
    private OnUseNowClickListener onUseNowClickListener;

    public RedeemedVoucherAdapter(List<Voucher> vouchers, OnUseNowClickListener onUseNowClickListener) {
        this.vouchers = vouchers;
        this.onUseNowClickListener = onUseNowClickListener;
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
            if (voucher.getQuantity() > 0) {
                voucher.setQuantity(voucher.getQuantity() - 1);
                holder.quantity.setText(String.valueOf(voucher.getQuantity()));
                if (voucher.getQuantity() == 0) {
                    vouchers.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, vouchers.size());
                    onUseNowClickListener.onVoucherUsed();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public static class RedeemedVoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherName, quantity;
        Button useNowButton;

        public RedeemedVoucherViewHolder(View itemView) {
            super(itemView);
            voucherName = itemView.findViewById(R.id.voucherName);
            quantity = itemView.findViewById(R.id.quantity);
            useNowButton = itemView.findViewById(R.id.useNowButton);
        }
    }

    public interface OnUseNowClickListener {
        void onVoucherUsed();
    }
}

