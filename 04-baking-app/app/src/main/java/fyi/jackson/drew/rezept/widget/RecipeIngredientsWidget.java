package fyi.jackson.drew.rezept.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import java.util.HashSet;
import java.util.Set;
import java.util.zip.CheckedInputStream;

import fyi.jackson.drew.rezept.MainActivity;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Ingredient;
import fyi.jackson.drew.rezept.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    public static final String TAG = RecipeIngredientsWidget.class.getSimpleName();

    public static final String ACTION_UPDATE_WIDGET_RECIPE = "ACTION_UPDATE_WIDGET_RECIPE";
    public static final String ACTION_CLEAR_WIDGET_RECIPE = "ACTION_CLEAR_WIDGET_RECIPE";
    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    public static final String KEY_INGREGIENT_SET = "KEY_INGREGIENT_SET";
    public static final String KEY_RECIPE_NAME = "KEY_RECIPE_NAME";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
                                Set<String> ingredients, String name) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);

        if (name == null || ingredients == null) {
            // show placeholder
            views.setViewVisibility(R.id.layout_placeholder, View.VISIBLE);
            views.setViewVisibility(R.id.layout_content, View.GONE);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.layout_placeholder, pendingIntent);
        } else {
            Log.d(TAG, "updateRemoteViews: " + name + " | " + ingredients.toString());
            // show content
            views.setViewVisibility(R.id.layout_content, View.VISIBLE);
            views.setViewVisibility(R.id.layout_placeholder, View.GONE);

            String[] ingredientsArray = new String[ingredients.size()];
            int i = 0;
            for (String s : ingredients) {
                ingredientsArray[i++] = s;
            }

            Intent intent = new Intent(context, IngredientsListService.class);
            intent.putExtra(IngredientsListService.EXTRA_INGREDIENTS_LIST, ingredientsArray);

            views.setTextViewText(R.id.appwidget_text, name);
            views.setRemoteAdapter(R.id.ingredients_list, intent);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredients_list);

            Intent clearIntent = new Intent(context, RecipeIngredientsWidget.class);
            clearIntent.setAction(RecipeIngredientsWidget.ACTION_CLEAR_WIDGET_RECIPE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clearIntent, 0);

            views.setOnClickPendingIntent(R.id.iv_close, pendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> ingredients = preferences.getStringSet(KEY_INGREGIENT_SET, null);
        String name = preferences.getString(KEY_RECIPE_NAME, null);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients, name);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: " + intent.getAction());
        switch (intent.getAction()) {
            case ACTION_UPDATE_WIDGET_RECIPE:
                Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
                storeRecipeIngredients(context, recipe);
                requestUpdate(context, recipe);
                break;
            case ACTION_CLEAR_WIDGET_RECIPE:
                clearRecipeIngredients(context);
                requestUpdate(context, null);
                break;
        }
    }

    private void storeRecipeIngredients(Context context, Recipe recipe) {
        Set<String> ingredients = new HashSet<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredients.add(ingredient.toString());
        }

        Log.d(TAG, "storeRecipeIngredients: " + recipe.getName() + " | " + ingredients.toString());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_RECIPE_NAME, recipe.getName());
        editor.putStringSet(KEY_INGREGIENT_SET, ingredients);
        editor.apply();
    }

    private void clearRecipeIngredients(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_RECIPE_NAME);
        editor.remove(KEY_INGREGIENT_SET);
        editor.apply();
    }

    private void requestUpdate(Context context, Recipe recipe) {

        Set<String> ingredients = null;
        String name = null;
        if (recipe != null) {
            ingredients = new HashSet<>();
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredients.add(ingredient.toString());
            }
            name = recipe.getName();
        }

        // This time we dont have widgetId. Reaching our widget with that way.
        ComponentName appWidget = new ComponentName(context, RecipeIngredientsWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(appWidget);

        onUpdateWithData(context, appWidgetManager, appWidgetIds, ingredients, name);
    }

    public void onUpdateWithData(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, Set<String> ingredients, String name) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients, name);
        }
    }
}

