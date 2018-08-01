package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jo = new JSONObject(json);
            JSONObject joNames = new JSONObject(jo.getString("name"));
            JSONArray alsKnowAsArray = joNames.getJSONArray("alsoKnownAs");
            JSONArray ingredientsArray = jo.getJSONArray("ingredients");
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            for (int j = 0; j < alsKnowAsArray.length(); j++) {
                alsoKnownAs.add(alsKnowAsArray.getString(j));
            }
            for (int j = 0; j < ingredientsArray.length(); j++) {
                ingredients.add(ingredientsArray.getString(j));
            }
            String mainName = joNames.getString("mainName");
            
            String placeOfOrigin = jo.getString("placeOfOrigin");
            String description = jo.getString("description");
            String image = jo.getString("image");
            
            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
