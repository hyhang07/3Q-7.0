package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int[][] images;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, int[][] images) {
        this.context = context;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        for(int[] row : images){
            count += row.length;
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        int row = position / images[0].length;
        int column = position % images[0].length;
        return images[row][column];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dialog_item_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);

        int row = position / images[0].length;
        int column = position % images[0].length;
        imageView.setImageResource(images[row][column]);

        return convertView;
    }
}
