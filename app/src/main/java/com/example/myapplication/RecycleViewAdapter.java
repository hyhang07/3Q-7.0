package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter <RecycleViewAdapter.MyHolder>{

    private Context mcontext;
    private List<FoodRecipe> mData;

    public RecycleViewAdapter (Context mcontext, List<FoodRecipe> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        view = mInflater.inflate(R.layout.activity_food_recipe, viewGroup, false);
        return new MyHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyHolder myHolder, int position) {
        myHolder.recipeTitle.setText(mData.get(position).getRecipeName());

        myHolder.img_recipe_thumbnail.setImageResource(mData.get(position).getThumbnail());

        myHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, RecipeActivity.class);

                intent.putExtra("RecipeName", mData.get(position).getRecipeName());
                intent.putExtra("RecipeIngredients", mData.get(position).getRecipeIngredients());
                intent.putExtra("RecipeMethodTitle", mData.get(position).getRecipeMethodsTitle());
                intent.putExtra("Recipe", mData.get(position).getRecipe());

                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        CardView cardView;
        ImageView img_recipe_thumbnail;
        public MyHolder(View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.text_recipe);
            img_recipe_thumbnail = itemView.findViewById(R.id.recipe_img_id);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }
}
