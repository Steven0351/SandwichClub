package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    private static final int KEY_OFFSET = 4;

    public static Sandwich parseSandwichJson(String json) {
        String mainName = retrieveStringValueFromJson("mainName", json);
        List<String> alsoKnownAs = retrieveListValueFromJson("alsoKnownAs", json);
        String placeOfOrigin = retrieveStringValueFromJson("placeOfOrigin", json);
        String description = retrieveStringValueFromJson("description", json);
        String image = retrieveStringValueFromJson("image", json);
        List<String> ingredients = retrieveListValueFromJson("ingredients", json);
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static String retrieveStringValueFromJson(String key, String json) {
        int keyEndIndex = json.indexOf("\"" + key + "\"" + ":\"") + key.length() + KEY_OFFSET;
        int valueEndIndex = json.indexOf("\",", keyEndIndex);

        String escape = "\\\\";
        String rawString = json.substring(keyEndIndex, valueEndIndex);

        if (rawString.isEmpty()) {
            return "N/A"; 
        }

        return rawString.replaceAll(escape, "");
    }

    private static List<String> retrieveListValueFromJson(String key, String json) {
        int keyEndIndex = json.indexOf("\"" + key + "\"") + key.length() + KEY_OFFSET;
        int valueEndIndex = json.indexOf("]", keyEndIndex);

        if (valueEndIndex - keyEndIndex <= 1) {
            return null;
        }

        String commaSeparatedValues = json.substring(keyEndIndex, valueEndIndex);
        String[] values = commaSeparatedValues.split(",");

        return new ArrayList<>(Arrays.asList(values));
    }


}
