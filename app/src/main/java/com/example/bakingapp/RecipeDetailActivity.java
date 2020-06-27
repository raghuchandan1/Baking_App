package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Recipes;
import com.example.bakingapp.data.Step;
import com.example.bakingapp.data.Ingredient;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener{
    private static boolean twoPane;
    private static Recipe recipe;
    public static int recipePosition;
    private static int stepPosition;
    private static Step step;

    public static String ingredientsText;

    public static String getIngredientsText() {
        return ingredientsText;
    }

    public static void setIngredientsText(String ingredientsText) {
        RecipeDetailActivity.ingredientsText = ingredientsText;
    }

    public static int getStepPosition() {
        return stepPosition;
    }

    public static void setStepPosition(int stepPosition) {
        RecipeDetailActivity.stepPosition = stepPosition;
    }

    public static Step getStep() {
        return step;
    }

    public static void setStep(Step step) {
        RecipeDetailActivity.step = step;
    }

    public static int getRecipePosition() {
        return recipePosition;
    }

    public static void setRecipePosition(int recipePosition) {
        RecipeDetailActivity.recipePosition = recipePosition;
    }

    public static boolean isTwoPane() {
        return twoPane;
    }

    public static void setTwoPane(boolean twoPane) {
        RecipeDetailActivity.twoPane = twoPane;
    }

    public static Recipe getRecipe() {
        return recipe;
    }

    public static void setRecipe(Recipe recipe) {
        RecipeDetailActivity.recipe = recipe;
    }
    //TODO: Add ingredients
    public static String makeIngredientsText(List<Ingredient> ingredients) {
        String text="";
        for (Ingredient ingredient : ingredients) {
            text+=ingredient.getQuantity()+" "+ingredient.getMeasure()+" "+ingredient.getIngredient()+System.getProperty("line.separator");
        }
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intent = getIntent();
        final Recipe recipe = intent.getParcelableExtra("Recipe");
        final int recipePosition = intent.getIntExtra("RecipePosition",0);
        assert recipe != null;
        setIngredientsText(makeIngredientsText(recipe.getIngredients()));
        Log.i("Ingredient in Fragment", recipe.getIngredients().get(0).getIngredient());
        setRecipe(recipe);
        setRecipePosition(recipePosition);
        setTwoPane(findViewById(R.id.step_detail_fragment_holder)!=null);
        Log.i("Two Pane",isTwoPane()+"");
        if(isTwoPane()){
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_holder, recipeDetailFragment).commit();
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(Recipes.getRecipes()[recipePosition].getSteps().get(0),0,recipePosition);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.step_detail_holder, stepDetailFragment);

        }
        else{
            FrameLayout recipeDetailHolder = (FrameLayout) findViewById(R.id.recipe_detail_holder);
            // Add Fragment in the StepDetailActivity
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_detail_holder, recipeDetailFragment).commit();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onStepSelected(Step step, int stepPosition) {
        if(isTwoPane()){
            setStepPosition(stepPosition);
            setStep(Recipes.getRecipes()[getRecipePosition()].getSteps().get(getStepPosition()));
            Log.i("StepPosition in TwoPane",stepPosition+"");
            StepDetailFragment stepDetailFragment1 = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.step_detail_holder, stepDetailFragment1);
            transaction.commit();
        }
        else{
            //  Add the player activity
            Context context=this;
            Class destinationClass=StepDetailActivity.class;
            Intent intentToStart=new Intent(context,destinationClass);
            intentToStart.putExtra("Step",step);
            intentToStart.putExtra("StepPosition",stepPosition);
            Log.i("StepPosition",stepPosition+"");
            intentToStart.putExtra("RecipePosition",getRecipePosition());
            startActivity(intentToStart);
            Toast.makeText(this, "This step is selected"+step.getShortDescription(),Toast.LENGTH_LONG).show();
        }
    }
}