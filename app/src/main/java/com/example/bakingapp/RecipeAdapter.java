package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Recipes;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {
    private final RecipeAdapterOnClickHandler rClickHandler;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler){
        rClickHandler=clickHandler;
    }
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe,int recipePosition);
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();

        int recipeLayout=R.layout.recipe_name;
        LayoutInflater inflater= LayoutInflater.from(context);
        View recipeView=inflater.inflate(recipeLayout, parent,false);

        return new RecipeViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        holder.recipeTextView.setText(Recipes.getRecipes()[position].getName());
    }

    @Override
    public int getItemCount() {
        if(Recipes.getRecipes()!=null)
            return Recipes.getRecipes().length;
        else
            return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTextView = itemView.findViewById(R.id.recipe_name_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            Recipe recipeDetails=Recipes.getRecipes()[adapterPosition];
            rClickHandler.onClick(recipeDetails,adapterPosition);
        }
    }
}