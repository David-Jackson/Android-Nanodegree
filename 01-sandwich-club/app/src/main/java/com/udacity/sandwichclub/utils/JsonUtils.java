package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            Log.d(TAG, "parseSandwichJson: " + sandwichJson.toString());
            sandwich = new Sandwich(
                    sandwichJson.getJSONObject("name").getString("mainName"),
                    getAliases(sandwichJson),
                    sandwichJson.getString("placeOfOrigin"),
                    sandwichJson.getString("description"),
                    sandwichJson.getString("image"),
                    getIngredients(sandwichJson)
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> getAliases(JSONObject sandwichJson) throws JSONException {
        List<String> aliases = new ArrayList<>();
        JSONArray aliasesJson = sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
        for (int i = 0; i < aliasesJson.length(); i++) {
            aliases.add(aliasesJson.getString(i));
        }
        return aliases;
    }

    private static List<String> getIngredients(JSONObject sandwichJson) throws JSONException {
        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientsJson = sandwichJson.getJSONArray("ingredients");
        for (int i = 0; i < ingredientsJson.length(); i++) {
            ingredients.add(ingredientsJson.getString(i));
        }
        return ingredients;
    }
}
