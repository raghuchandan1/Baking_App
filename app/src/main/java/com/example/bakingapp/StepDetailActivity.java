package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Recipes;
import com.example.bakingapp.data.Step;

public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static Step step;
    static int stepPosition;
    static int recipePosition;
    Button previousButton;
    Button nextButton;


    public static int getStepPosition() {
        return stepPosition;
    }

    public static void setStepPosition(int stepPosition) {
        StepDetailActivity.stepPosition = stepPosition;
    }

    public static int getRecipePosition() {
        return recipePosition;
    }

    public static void setRecipePosition(int recipePosition) {
        StepDetailActivity.recipePosition = recipePosition;
    }

    public static Step getStep() {
        return step;
    }

    public static void setStep(Step step) {
        StepDetailActivity.step = step;
    }

    public static boolean landscape = false;

    public static boolean isLandscape() {
        return landscape;
    }

    public static void setLandscape(boolean landscape) {
        StepDetailActivity.landscape = landscape;
    }

    boolean twoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        landscape = (findViewById(R.id.button_holder)==null);
        setLandscape(landscape);
        Log.i("Landscape in Activity",isLandscape()+"");
        Intent intent = getIntent();
        final Step step = intent.getParcelableExtra("Step");
        final int stepPosition = intent.getIntExtra("StepPosition",0);
        setStepPosition(stepPosition);
        setRecipePosition(recipePosition);
        final int recipePosition = intent.getIntExtra("RecipePosition",0);
        assert step != null;
        Log.i("Ingredient in Fragment", step.getVideoURL());
        setStep(step);
        //TODO: Add Fragment in the StepDetailActivity
        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.step_detail_holder, stepDetailFragment);
        transaction.commit();
        if(!landscape) {
            previousButton = (Button) findViewById(R.id.button_previous);
            previousButton.setText("Previous");
            Log.i("StepPositionPrevious", stepPosition + "");
            if (stepPosition == 0) {
                previousButton.setVisibility(View.INVISIBLE);
            }
            previousButton.setOnClickListener((View.OnClickListener) this);
            nextButton = (Button) findViewById(R.id.button_next);
            nextButton.setText("Next");
            if (stepPosition == Recipes.getRecipes()[recipePosition].getSteps().size() - 1) {
                nextButton.setVisibility(View.INVISIBLE);
            }
            nextButton.setOnClickListener((View.OnClickListener) this);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_previous:{
                nextButton.setVisibility(View.VISIBLE);
                Log.i("Previous","Pressed");
                if(stepPosition!=1){
                    setStepPosition(stepPosition-1);
                    setStep(Recipes.getRecipes()[getRecipePosition()].getSteps().get(getStepPosition()));
                    Log.i("StepPositionPrevious",stepPosition+"");
                    StepDetailFragment stepDetailFragment1 = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.step_detail_holder, stepDetailFragment1);
                    transaction.commit();

                }
                else{
                    setStepPosition(stepPosition-1);
                    setStep(Recipes.getRecipes()[getRecipePosition()].getSteps().get(getStepPosition()));
                    Log.i("StepPositionPrevious",stepPosition+"");
                    StepDetailFragment stepDetailFragment1 = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.step_detail_holder, stepDetailFragment1);
                    transaction.commit();
                    previousButton.setVisibility(View.INVISIBLE);
                }

            }
            break;
            case R.id.button_next:{
                previousButton.setVisibility(View.VISIBLE);
                Log.i("Next","Pressed");
                if(stepPosition!=(Recipes.getRecipes()[getRecipePosition()].getSteps().size()-2)){
                    setStepPosition(stepPosition+1);
                    setStep(Recipes.getRecipes()[getRecipePosition()].getSteps().get(getStepPosition()));
                    StepDetailFragment stepDetailFragment1 = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.step_detail_holder, stepDetailFragment1);
                    transaction.commit();

                }
                else{
                    setStepPosition(stepPosition+1);
                    setStep(Recipes.getRecipes()[getRecipePosition()].getSteps().get(getStepPosition()));
                    StepDetailFragment stepDetailFragment1 = StepDetailFragment.newInstance(step,stepPosition,recipePosition);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.step_detail_holder, stepDetailFragment1);
                    transaction.commit();
                    nextButton.setVisibility(View.INVISIBLE);
                }
                break;
            }
        }
    }
}