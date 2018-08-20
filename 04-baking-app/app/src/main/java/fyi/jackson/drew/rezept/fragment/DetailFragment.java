package fyi.jackson.drew.rezept.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.drew.rezept.MainActivity;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Ingredient;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.model.Step;
import fyi.jackson.drew.rezept.ui.ExpandController;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private static final String EXTRA_RECIPE_ITEM = "EXTRA_RECIPE_ITEM";
    private static final String EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME";
    private ExpandController ingredientsExpander;
    private ExpandController stepsExpander;

    @BindView(R.id.card_header) CardView cardView;

    @BindView(R.id.iv_main_image) ImageView mainImage;
    @BindView(R.id.tv_name) TextView name;
    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.click_area_ingredients) View clickAreaIngredients;
    @BindView(R.id.click_area_steps) View clickAreaSteps;
    @BindView(R.id.iv_expand_ingredients) ImageView expandIngredients;
    @BindView(R.id.iv_expand_steps) ImageView expandSteps;
    @BindView(R.id.content_ingredients) LinearLayout contentIngredients;
    @BindView(R.id.content_steps) LinearLayout  contentSteps;
    private Unbinder unbinder;

    public DetailFragment() {}

    public static DetailFragment newInstance(Recipe recipe, String transitionName) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE_ITEM, recipe);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(
                    TransitionInflater.from(getContext())
                            .inflateTransition(android.R.transition.move));
        }
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

        Recipe recipe = getArguments().getParcelable(EXTRA_RECIPE_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);
        bindTo(recipe, transitionName);

        ingredientsExpander = new ExpandController(
                clickAreaIngredients, expandIngredients, contentIngredients);

        stepsExpander = new ExpandController(clickAreaSteps, expandSteps, contentSteps);
    }

    private void bindTo(final Recipe recipe, final String transitionName) {
        name.setText(recipe.getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardView.setTransitionName(transitionName);
        }

        Picasso.get()
                .load(recipe.getImage())
                .into(mainImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        startPostponedEnterTransition();
                    }
                });

        // Populate Ingredients
        for (Ingredient ingredient : recipe.getIngredients()) {
            AppCompatCheckBox ingredientCheckBox = (AppCompatCheckBox) getLayoutInflater()
                    .inflate(R.layout.layout_ingredient, contentIngredients, false);
            ingredientCheckBox.setText(ingredient.toString());
            contentIngredients.addView(ingredientCheckBox);
        }

        // Populate Steps
        for (Step step : recipe.getSteps()) {
            TextView stepTextView = (TextView) getLayoutInflater()
                    .inflate(R.layout.layout_step, contentSteps, false);
            stepTextView.setText(step.toShortString());
            contentSteps.addView(stepTextView);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCooking(recipe);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void startCooking(Recipe recipe) {
        String transitionName = "DetailToCookingTransitional";
        ViewCompat.setTransitionName(mainImage, transitionName);

        CookingFragment cookingFragment = CookingFragment.newInstance(recipe, transitionName);

        boolean isTablet = ((MainActivity) getActivity()).isTablet();
        int layoutId = isTablet ? R.id.detail : R.id.content;

        getFragmentManager().beginTransaction()
                .addSharedElement(mainImage, transitionName)
                .replace(layoutId, cookingFragment)
                .addToBackStack(TAG)
                .commit();
    }
}
