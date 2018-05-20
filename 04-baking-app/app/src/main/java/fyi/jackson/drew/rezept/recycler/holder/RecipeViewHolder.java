package fyi.jackson.drew.rezept.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.util.RecipeUtils;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView name;
    ImageView image;

    public RecipeViewHolder(View v) {
        super(v);
        itemView = v;
        name = v.findViewById(R.id.tv_name);
        image = v.findViewById(R.id.iv_image);
    }

    public void bindTo(Recipe recipe) {
        name.setText(recipe.getName());

        Picasso.get()
                .load(recipe.getImage())
                .placeholder(RecipeUtils.getPlaceHolderImage())
                .into(image);
    }
}
