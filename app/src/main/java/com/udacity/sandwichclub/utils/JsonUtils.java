package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_NAME = "name";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String FALLBACK_PLACE_OF_ORIGIN = "Not Known";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jo = new JSONObject(json);
            JSONObject joNames = jo.optJSONObject(KEY_NAME);
            JSONArray alsKnowAsArray = joNames.optJSONArray(KEY_ALSO_KNOW_AS);
            JSONArray ingredientsArray = jo.optJSONArray(KEY_INGREDIENTS);
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            for (int j = 0; j < alsKnowAsArray.length(); j++) {
                alsoKnownAs.add(alsKnowAsArray.optString(j));
            }
            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredients.add(ingredientsArray.optString(j));
            }
            String mainName = joNames.optString(KEY_MAIN_NAME);
            
            String placeOfOrigin = jo.optString(KEY_PLACE_OF_ORIGIN,FALLBACK_PLACE_OF_ORIGIN);
            String description = jo.optString(KEY_DESCRIPTION);
            String image = jo.optString(KEY_IMAGE);
            
            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
