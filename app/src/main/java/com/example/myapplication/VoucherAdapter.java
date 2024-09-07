package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private List<Voucher> vouchers;
    private OnRedeemClickListener onRedeemClickListener;

    public VoucherAdapter(List<Voucher> vouchers, OnRedeemClickListener onRedeemClickListener) {
        this.vouchers = vouchers;
        this.onRedeemClickListener = onRedeemClickListener;
    }

    @Override
    public VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VoucherViewHolder holder, int position) {
        Voucher voucher = vouchers.get(position);
        holder.voucherName.setText(voucher.getName());
        holder.quantity.setText(String.valueOf(voucher.getQuantity()));

        holder.redeemButton.setEnabled(voucher.getQuantity() > 0);
        holder.redeemButton.setOnClickListener(v -> {
            if (voucher.getQuantity() > 0) {
                voucher.setQuantity(voucher.getQuantity() - 1);
                holder.quantity.setText(String.valueOf(voucher.getQuantity()));
                holder.redeemButton.setEnabled(voucher.getQuantity() > 0);
                onRedeemClickListener.onRedeemClick(voucher);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherName, quantity;
        Button redeemButton;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            voucherName = itemView.findViewById(R.id.voucherName);
            quantity = itemView.findViewById(R.id.quantity);
            redeemButton = itemView.findViewById(R.id.redeemButton);
        }
    }

    public interface OnRedeemClickListener {
        void onRedeemClick(Voucher voucher);
    }
}
