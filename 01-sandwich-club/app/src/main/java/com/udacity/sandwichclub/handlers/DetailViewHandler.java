package com.udacity.sandwichclub.handlers;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

// This class handles all of the UI for the sandwich detail view
public class DetailViewHandler {

    Activity activity;

    private ImageView sandwichImageView;
    private TextView title, aliases;
    private SandwichAttributeHandler desctiption, origin, ingredients;


    public DetailViewHandler(Activity activity) {
        sandwichImageView = activity.findViewById(R.id.iv_image);
        title = activity.findViewById(R.id.tv_sandwich_title);
        aliases = activity.findViewById(R.id.tv_aliases);
        desctiption = new SandwichAttributeHandler(
                (TextView) activity.findViewById(R.id.tv_description),
                (ImageView) activity.findViewById(R.id.iv_description_title),
                activity.findViewById(R.id.div_description)
        );
        origin = new SandwichAttributeHandler(
                (TextView) activity.findViewById(R.id.tv_origin),
                (ImageView) activity.findViewById(R.id.iv_origin_title),
                activity.findViewById(R.id.div_origin)
        );
        ingredients = new SandwichAttributeHandler(
                (TextView) activity.findViewById(R.id.tv_ingredients),
                (ImageView) activity.findViewById(R.id.iv_ingredients_title),
                activity.findViewById(R.id.div_ingredients)
        );

        this.activity = activity;
    }

    // Method to load a sandwich for a given position,
    // returns true if sandwich loads correctly,
    // false if a sandwich at that position does not exist
    public boolean loadSandwich(int position) {
        String[] sandwiches = activity.getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) return false;

        populateUI(sandwich);

        return true;
    }

    public void populateUI(Sandwich sandwich) {
        Picasso.with(activity)
                .load(sandwich.getImage())
                .into(sandwichImageView);
        title.setText(sandwich.getMainName());

        String aliasesString = sandwich.getAlsoKnownAsString();
        desctiption.setVisibility(aliasesString.isEmpty() ? View.GONE : View.VISIBLE);
        desctiption.setText(aliasesString);

        String descriptionString = sandwich.getDescription();
        desctiption.setVisibility(descriptionString.isEmpty() ? View.GONE : View.VISIBLE);
        desctiption.setText(descriptionString);

        String originString = sandwich.getPlaceOfOrigin();
        origin.setVisibility(originString.isEmpty() ? View.GONE : View.VISIBLE);
        origin.setText(originString);

        String ingredientsString = sandwich.getIngredientsString();
        ingredients.setVisibility(ingredientsString.isEmpty() ? View.GONE : View.VISIBLE);
        ingredients.setText(ingredientsString);
    }
}
