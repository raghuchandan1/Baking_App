package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.data.Recipes;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        int recipePosition = RecipeDetailActivity.getRecipePosition();
        String ingredientsText = RecipeDetailActivity.getIngredientsText();
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        if(ingredientsText==null){
            recipePosition = 0;
            ingredientsText = RecipeDetailActivity.makeIngredientsText(Recipes.getRecipes()[0].getIngredients());
        }
        else if(ingredientsText.equals("")){
            recipePosition = 0;
            ingredientsText = RecipeDetailActivity.makeIngredientsText(Recipes.getRecipes()[0].getIngredients());
        }
        CharSequence widgetText = ingredientsText;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Class destinationClass=RecipeDetailActivity.class;
        Intent intentToStart=new Intent(context,destinationClass);
        intentToStart.putExtra("Recipe",Recipes.getRecipes()[recipePosition]);
        intentToStart.putExtra("RecipePosition",recipePosition);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToStart, 0);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

