package com.example.bakingapp.data;
import android.util.Log;

import com.google.gson.Gson;

public class DataUtils {
    private Recipe[] recipes;

// --Commented out by Inspection START (6/29/2020 12:34 AM):
//    public Recipe[] getRecipes() {
//        Gson gson = new Gson();
//        //recipes = gson.fromJson(String.valueOf(R.string.baking_json), Recipe[].class);
//        Log.i("Recipes",recipes.toString());
//        return recipes;
//    }
// --Commented out by Inspection STOP (6/29/2020 12:34 AM)

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }



}
