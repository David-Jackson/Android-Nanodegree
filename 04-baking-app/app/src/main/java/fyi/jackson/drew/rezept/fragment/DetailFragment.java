package fyi.jackson.drew.rezept.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.ui.ExpandController;

public class DetailFragment extends Fragment {

    public static final String EXTRA_RECIPE_ITEM = "EXTRA_RECIPE_ITEM";
    public static final String EXTRA_IMAGE_TRANSITION_NAME = "EXTRA_IMAGE_TRANSITION_NAME";
    public static final String EXTRA_NAME_TRANSITION_NAME = "EXTRA_NAME_TRANSITION_NAME";
    ExpandController ingredientsExpander, stepsExpander;

    @BindView(R.id.iv_main_image) ImageView mainImage;
    @BindView(R.id.tv_name) TextView name;

    @BindView(R.id.click_area_ingredients) View clickAreaIngredients;
    @BindView(R.id.click_area_steps) View clickAreaSteps;
    @BindView(R.id.iv_expand_ingredients) ImageView expandIngredients;
    @BindView(R.id.iv_expand_steps) ImageView expandSteps;
    @BindView(R.id.content_ingredients) View contentIngredients;
    @BindView(R.id.content_steps) View contentSteps;
    private Unbinder unbinder;

    public DetailFragment() {}

    public static DetailFragment newInstance(
            Recipe recipe, String imageTransitionName, String nameTransitionName) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE_ITEM, recipe);
        bundle.putString(EXTRA_IMAGE_TRANSITION_NAME, imageTransitionName);
        bundle.putString(EXTRA_NAME_TRANSITION_NAME, nameTransitionName);
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
        String imageTransitionName = getArguments().getString(EXTRA_IMAGE_TRANSITION_NAME);
        String nameTransitionName = getArguments().getString(EXTRA_NAME_TRANSITION_NAME);
        bindTo(recipe, imageTransitionName, nameTransitionName);

        ingredientsExpander = new ExpandController(
                clickAreaIngredients, expandIngredients, contentIngredients);

        stepsExpander = new ExpandController(clickAreaSteps, expandSteps, contentSteps);
    }

    private void bindTo(Recipe recipe, String imageTransitionName, String nameTransitionName) {
        name.setText(recipe.getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainImage.setTransitionName(imageTransitionName);
            name.setTransitionName(nameTransitionName);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
