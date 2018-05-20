package fyi.jackson.drew.rezept.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.recycler.holder.RecipeViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    public RecipeListAdapter() {

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_holder_recipe, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 40;
    }
}
