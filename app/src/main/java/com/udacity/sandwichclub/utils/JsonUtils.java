package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject realJson = new JSONObject(json);

            JSONObject name = realJson.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray akaArray = name.getJSONArray("alsoKnownAs");

            List<String> alsoKnownAs = convertJSONArrayToList(akaArray);

            String placeOfOrigin = realJson.getString("placeOfOrigin");
            String description = realJson.getString("description");
            String image = realJson.getString("image");

            JSONArray ingredientsArray = realJson.getJSONArray("ingredients");
            List<String> ingredients = convertJSONArrayToList(ingredientsArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static List<String> convertJSONArrayToList(JSONArray jsonArray) throws JSONException {
        if (jsonArray.length() == 0) {
            return null;
        }

        String joinedValues = jsonArray.join(",");
        String[] valuesArray = joinedValues.split(",");
        return new ArrayList<>(Arrays.asList(valuesArray));
    }

}
