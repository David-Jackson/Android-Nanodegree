package fyi.jackson.drew.rezept.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.drew.rezept.MainActivity;
import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.network.DataHandler;
import fyi.jackson.drew.rezept.recycler.RecipeListAdapter;
import fyi.jackson.drew.rezept.recycler.holder.RecipeViewHolder;
import fyi.jackson.drew.rezept.ui.ItemClickListener;

public class ListFragment extends Fragment implements
        ItemClickListener, DataHandler.DataCallback, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = ListFragment.class.getSimpleName();

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    RecipeListAdapter adapter;

    Snackbar errorSnackbar;

    DataHandler dataHandler;

    public ListFragment() {}

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RecipeListAdapter(null, this);
        dataHandler = new DataHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = view.findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.rv_recipe_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        dataHandler.requestData();
    }

    @Override
    public void onUpdate(List<Recipe> recipes) {
        Log.d(TAG, "onUpdate: Updating...");
        if (recipes.size() == 0) {
            showErrorSnackbar();
        } else {
            hideErrorSnackbar();
        }
        adapter.setRecipes(recipes);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(Recipe recipe, final RecipeViewHolder holder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "onClick: " + holder.itemView.getTranslationZ());
            int mult = holder.itemView.getTranslationZ() > 10 ? -1 : 1;
            holder.itemView.animate()
                    .translationZBy(mult * 30f)
                    .setDuration(300)
                    .start();
        }


        String transitionName = ViewCompat.getTransitionName(holder.itemView);

        DetailFragment detailFragment = DetailFragment.newInstance(recipe, transitionName);

        boolean isTablet = ((MainActivity) getActivity()).isTablet();
        int layoutId = isTablet ? R.id.detail : R.id.content;

        FragmentTransaction transaction = getFragmentManager().beginTransaction()
                .addSharedElement(holder.itemView, transitionName)
                .replace(layoutId, detailFragment);

        if (!isTablet) {
            transaction.addToBackStack(TAG);
        }

        transaction.commit();
    }

    @Override
    public void onRefresh() {
        dataHandler.forceRequest();
    }

    private void showErrorSnackbar() {
        errorSnackbar = Snackbar.make(recyclerView, R.string.error_text, Snackbar.LENGTH_INDEFINITE);
        errorSnackbar.show();
    }

    private void hideErrorSnackbar() {
        if (errorSnackbar != null) {
            errorSnackbar.dismiss();
        }
    }
}
