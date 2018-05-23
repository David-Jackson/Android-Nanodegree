package fyi.jackson.drew.rezept.recycler.holder;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.ui.ItemClickListener;
import fyi.jackson.drew.rezept.util.RecipeUtils;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public View itemView;
    public TextView name;
    public ImageView image;

    public RecipeViewHolder(View v) {
        super(v);
        itemView = v;
        name = v.findViewById(R.id.tv_name);
        image = v.findViewById(R.id.iv_image);
    }

    public void bindTo(final Recipe recipe, final ItemClickListener clickListener) {
        name.setText(recipe.getName());

        ViewCompat.setTransitionName(image, recipe.getName() + "_image");
        ViewCompat.setTransitionName(name, recipe.getName() + "_name");

        Picasso.get()
                .load(recipe.getImage())
                .placeholder(RecipeUtils.getPlaceHolderImage())
                .into(image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(recipe, RecipeViewHolder.this);
            }
        });
    }
}
