package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_INGREDIENTES = "ingredients";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichJson = new JSONObject(json);
            Log.d(TAG, "parseSandwichJson: " + sandwichJson.toString());
            sandwich = new Sandwich(
                    sandwichJson.getJSONObject(KEY_NAME).getString(KEY_MAIN_NAME),
                    getAliases(sandwichJson),
                    sandwichJson.optString(KEY_PLACE_OF_ORIGIN),
                    sandwichJson.optString(KEY_DESCRIPTION),
                    sandwichJson.optString(KEY_IMAGE),
                    getIngredients(sandwichJson)
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> getAliases(JSONObject sandwichJson) throws JSONException {
        JSONArray aliasesJson = sandwichJson.getJSONObject(KEY_NAME).getJSONArray(KEY_ALSO_KNOW_AS);
        return jsonArrayToList(aliasesJson);
    }

    private static List<String> getIngredients(JSONObject sandwichJson) throws JSONException {
        JSONArray ingredientsJson = sandwichJson.getJSONArray(KEY_INGREDIENTES);
        return jsonArrayToList(ingredientsJson);
    }

    private static List<String> jsonArrayToList(JSONArray array) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            res.add(array.optString(i));
        }
        return res;
    }
}
