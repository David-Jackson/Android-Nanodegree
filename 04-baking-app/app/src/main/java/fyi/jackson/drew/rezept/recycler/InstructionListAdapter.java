package fyi.jackson.drew.rezept.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.model.Step;
import fyi.jackson.drew.rezept.recycler.holder.IngredientsViewHolder;
import fyi.jackson.drew.rezept.recycler.holder.StepViewHolder;

public class InstructionListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_INGREDIENTS = 96;
    private static final int TYPE_STEP = 316;

    private final Recipe recipe;

    public InstructionListAdapter(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 ? TYPE_INGREDIENTS : TYPE_STEP);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_INGREDIENTS:
                View v1 = inflater.inflate(R.layout.view_holder_ingredients, parent, false);
                viewHolder = new IngredientsViewHolder(v1);
                break;
            default: // TYPE_STEP
                View v2 = inflater.inflate(R.layout.view_holder_step, parent, false);
                viewHolder = new StepViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_INGREDIENTS:
                ((IngredientsViewHolder) holder).bindTo(recipe.getIngredients());
                break;
            default: // TYPE_STEP
                Step step = recipe.getSteps().get(position - 1);
                ((StepViewHolder) holder).bindTo(step);
                break;
        }
    }

    @Override
    public int getItemCount() {
        // ingredients card + number of steps
        return 1 + recipe.getSteps().size();
    }
}
