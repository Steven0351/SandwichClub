package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.error_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView originTv = findViewById(R.id.origin_tv);

        descriptionTv.setText(replaceStringIfEmpty(sandwich.getDescription()));

        String ingredients = concatenateAndRemoveQuotationMarksFromList(sandwich.getIngredients());
        ingredientsTv.setText(ingredients);

        String alsoKnownAs = concatenateAndRemoveQuotationMarksFromList(sandwich.getAlsoKnownAs());
        alsoKnownTv.setText(alsoKnownAs);

        originTv.setText(replaceStringIfEmpty(sandwich.getPlaceOfOrigin()));
    }

    // By default, quotes are left around the String elements in the List retrieved from JSON.
    // This method concatenates the string elements and removes quotation marks for presentation to
    // the user.
    private String concatenateAndRemoveQuotationMarksFromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "N/A";
        }

        String rawString = TextUtils.join(", ", list);
        return rawString.replaceAll("\"", "");
    }

    private String replaceStringIfEmpty(String string) {
        if (string == null || string.isEmpty()) {
            return "N/A";
        }
        return string;
    }
}
