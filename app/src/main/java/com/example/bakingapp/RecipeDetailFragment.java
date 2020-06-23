package com.example.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailFragment extends Fragment {
    public RecipeDetailFragment () {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        TextView ingredientsDetailView = (TextView) rootView.findViewById(R.id.ingredient_detail_view);
        RecyclerView stepsView = (RecyclerView) rootView.findViewById(R.id.step_recycler_view);

        return rootView;
    }
}
