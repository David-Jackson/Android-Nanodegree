package fyi.jackson.drew.rezept.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.recycler.InstructionListAdapter;
import fyi.jackson.drew.rezept.recycler.LinePagerIndicatorDecoration;

public class CookingFragment extends Fragment {

    public static final String TAG = CookingFragment.class.getSimpleName();
    public static final String EXTRA_RECIPE_ITEM = "EXTRA_RECIPE_ITEM";
    public static final String EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME";

    @BindView(R.id.rv_instructions) RecyclerView instructionsRecyclerView;
    @BindView(R.id.media) PlayerView playerView;
    @BindView(R.id.iv_media) ImageView mainImage;
    private Unbinder unbinder;


    private SimpleExoPlayer exoPlayer;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            playerView.setTransitionName(transitionName);
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
