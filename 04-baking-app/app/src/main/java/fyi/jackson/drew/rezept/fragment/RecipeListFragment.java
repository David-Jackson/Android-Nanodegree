package fyi.jackson.drew.rezept.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fyi.jackson.drew.rezept.R;
import fyi.jackson.drew.rezept.model.Recipe;
import fyi.jackson.drew.rezept.network.DataHandler;
import fyi.jackson.drew.rezept.recycler.RecipeListAdapter;

public class RecipeListFragment extends Fragment implements DataHandler.DataCallback {

    public static final String TAG = RecipeListFragment.class.getSimpleName();

    RecyclerView recyclerView;
    RecipeListAdapter adapter;

    DataHandler dataHandler;

    public RecipeListFragment() {}

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RecipeListAdapter(null);
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

        recyclerView = view.findViewById(R.id.rv_recipe_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        dataHandler.requestData();
    }

    @Override
    public void onUpdate(List<Recipe> recipes) {
        Log.d(TAG, "onUpdate: Updating...");
        adapter.setRecipes(recipes);
    }
}
