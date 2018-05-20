package fyi.jackson.drew.rezept.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fyi.jackson.drew.rezept.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView name;

    public RecipeViewHolder(View v) {
        super(v);
        itemView = v;
        name = v.findViewById(R.id.tv_name);
    }

    public void bindTo() {

    }
}
