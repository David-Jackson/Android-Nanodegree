package fyi.jackson.drew.rezept.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.ui.ExpandController;

public class DetailFragment extends Fragment {

    ExpandController ingredientsExpander, stepsExpander;

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

        ingredientsExpander = new ExpandController(
                view.findViewById(R.id.click_area_ingredients),
                (ImageView) view.findViewById(R.id.iv_expand_ingredients),
                view.findViewById(R.id.content_ingredients));

        stepsExpander = new ExpandController(
                view.findViewById(R.id.click_area_steps),
                (ImageView) view.findViewById(R.id.iv_expand_steps),
                view.findViewById(R.id.content_steps));
    }
}
