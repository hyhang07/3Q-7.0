package com.example.myapplication;

public class FoodRecipe {

    private String RecipeName;
    private String RecipeIngredients;
    private String RecipeMethodsTitle;
    private String Recipe;
    private int Thumbnail;

    public FoodRecipe(String name, String recipeIngredients, String recipeMethodsTitle, String recipe, int thumbnail) {

        RecipeName = name;
        RecipeIngredients = recipeIngredients;
        RecipeMethodsTitle = recipeMethodsTitle;
        Recipe = recipe;
        Thumbnail = thumbnail;
    }

    public String getRecipeName() {

        return RecipeName;
    }

    public String getRecipeIngredients() {

        return RecipeIngredients;
    }

    public String getRecipeMethodsTitle() {

        return RecipeMethodsTitle;
    }

    public String getRecipe() {

        return Recipe;
    }

    public int getThumbnail() {

        return Thumbnail;
    }
}
