package fyi.jackson.drew.rezept.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.ui.ExpandController;

public class DetailFragment extends Fragment {

    ExpandController ingredientsExpander, stepsExpander;

    @BindView(R.id.click_area_ingredients) View clickAreaIngredients;
    @BindView(R.id.click_area_steps) View clickAreaSteps;
    @BindView(R.id.iv_expand_ingredients) ImageView expandIngredients;
    @BindView(R.id.iv_expand_steps) ImageView expandSteps;
    @BindView(R.id.content_ingredients) View contentIngredients;
    @BindView(R.id.content_steps) View contentSteps;
    private Unbinder unbinder;

    public DetailFragment() {}

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        ingredientsExpander = new ExpandController(
                clickAreaIngredients, expandIngredients, contentIngredients);

        stepsExpander = new ExpandController(clickAreaSteps, expandSteps, contentSteps);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
