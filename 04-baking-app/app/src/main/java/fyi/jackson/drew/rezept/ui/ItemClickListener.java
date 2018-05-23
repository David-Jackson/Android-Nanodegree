package fyi.jackson.drew.rezept.ui;

import android.widget.ImageView;

import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.recycler.holder.RecipeViewHolder;

public interface ItemClickListener {
    void onClick(Recipe recipe, RecipeViewHolder holder);
}
