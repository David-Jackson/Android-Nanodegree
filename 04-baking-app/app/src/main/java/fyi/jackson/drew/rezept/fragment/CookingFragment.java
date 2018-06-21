package fyi.jackson.drew.rezept.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.recycler.InstructionListAdapter;
import fyi.jackson.drew.rezept.recycler.LinePagerIndicatorDecoration;

public class CookingFragment extends Fragment {

    public static final String EXTRA_RECIPE_ITEM = "EXTRA_RECIPE_ITEM";
    public static final String EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME";

    @BindView(R.id.rv_instructions) RecyclerView instructionsRecyclerView;
    private Unbinder unbinder;

    public CookingFragment() {}

    public static CookingFragment newInstance(Recipe recipe, String transitionName) {
        CookingFragment cookingFragment = new CookingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE_ITEM, recipe);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        cookingFragment.setArguments(bundle);
        return cookingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cooking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        Recipe recipe = getArguments().getParcelable(EXTRA_RECIPE_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);
        bindTo(recipe, transitionName);
    }

    private void bindTo(Recipe recipe, String transitionName) {
        InstructionListAdapter adapter = new InstructionListAdapter(recipe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false);
        instructionsRecyclerView.setAdapter(adapter);
        instructionsRecyclerView.setLayoutManager(layoutManager);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(instructionsRecyclerView);

        instructionsRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
