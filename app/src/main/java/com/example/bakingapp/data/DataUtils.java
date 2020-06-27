package com.example.bakingapp.data;
import android.util.Log;

import com.example.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DataUtils {
    private Recipe[] recipes;

    public Recipe[] getRecipes() {
        Gson gson = new Gson();
        //recipes = gson.fromJson(String.valueOf(R.string.baking_json), Recipe[].class);
        Log.i("Recipes",recipes.toString());
        return recipes;
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }



}
