package fyi.jackson.drew.rezept.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.recycler.holder.RecipeViewHolder;
import fyi.jackson.drew.rezept.ui.ItemClickListener;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<Recipe> recipes;
    private final ItemClickListener clickListener;

    public RecipeListAdapter(List<Recipe> recipes, ItemClickListener clickListener) {
        this.recipes = recipes;
        this.clickListener = clickListener;
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
        holder.bindTo(recipes.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return (recipes == null ? 0 : recipes.size());
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
