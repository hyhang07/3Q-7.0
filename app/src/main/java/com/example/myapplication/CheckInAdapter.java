package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.ViewHolder> {

    private final List<Boolean> checkInItems;

    public CheckInAdapter(List<Boolean> checkInItems) {
        this.checkInItems = checkInItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_in, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boolean isCheckedIn = checkInItems.get(position);
        holder.dayTextView.setText("Day " + (position + 1));
        holder.pointTextView.setText(isCheckedIn ? "100" : "0");

        // Set background color based on check-in status
        if (isCheckedIn) {
            holder.pointCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        } else {
            holder.pointCardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.peach));
        }
    }

    @Override
    public int getItemCount() {
        return checkInItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView;
        public TextView pointTextView;
        public CardView pointCardView;

        public ViewHolder(View view) {
            super(view);
            dayTextView = view.findViewById(R.id.day);
            pointTextView = view.findViewById(R.id.point);
            pointCardView = view.findViewById(R.id.point_card);
        }
    }
}
