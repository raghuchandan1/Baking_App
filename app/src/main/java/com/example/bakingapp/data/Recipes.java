package com.example.bakingapp.data;

public class Recipes {
    public static Recipe[] recipes;

    public static Recipe[] getRecipes() {
        return recipes;
    }

    public static void setRecipes(Recipe[] recipes) {
        Recipes.recipes = recipes;
    }
}
