package com.example.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;

public class RecipeDetailFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler{
    //TODO: Always handle in activity and based on width replace fragment or launch new activity
    //TODO: Add for sharing between fragments(Not needed implement the Adapter's onClickHandler in the activity. There also replace or add the fragment based on the clicked step

    OnStepClickListener mCallback;
    static Recipe recipe;

    public interface OnStepClickListener{
        void onStepSelected(Step step, int stepPosition);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement OnStepClickListener");
        }
    }

    public RecipeDetailFragment () {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){

        }
        RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();

        recipe = recipeDetailActivity.getRecipe();


        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        TextView ingredientsDetailView = (TextView) rootView.findViewById(R.id.ingredient_detail_view);
        RecyclerView stepsView = (RecyclerView) rootView.findViewById(R.id.step_recycler_view);
        //TODO: Implement clickHandler in Activity
        StepAdapter stepAdapter = new StepAdapter(this);
        stepAdapter.setSteps(recipe.getSteps());
        stepsView.setAdapter(stepAdapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        stepsView.setLayoutManager(layoutManager);

        ingredientsDetailView.setText("Ingredient 1");

        return rootView;
    }
    //TODO: For sharing implement this onClick in activity

    @Override
    public void onClick(Step step, int stepPosition) {
        //TODO: Handle on click of a Step
        //TODO: Start the Step Detail Fragment
        mCallback.onStepSelected(step,stepPosition);
    }

}
