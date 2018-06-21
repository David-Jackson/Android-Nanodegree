package fyi.jackson.drew.rezept.recycler.holder;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Ingredient;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    LinearLayout ingredientsContainer;

    public IngredientsViewHolder(View v) {
        super(v);
        ingredientsContainer = v.findViewById(R.id.ingredients_container);
    }

    public void bindTo(final List<Ingredient> ingredients) {
        // Populate Ingredients
        for (Ingredient ingredient : ingredients) {
            AppCompatCheckBox ingredientCheckBox = new AppCompatCheckBox(itemView.getContext());
            ingredientCheckBox.setText(ingredient.toString());
            ingredientsContainer.addView(ingredientCheckBox);
        }
    }
}
