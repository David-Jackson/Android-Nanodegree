package fyi.jackson.drew.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import fyi.jackson.drew.popularmovies.MainActivity;
import fyi.jackson.drew.popularmovies.R;
import fyi.jackson.drew.popularmovies.data.MovieDataCallback;
import fyi.jackson.drew.popularmovies.model.Movie;
import fyi.jackson.drew.popularmovies.recycler.MovieListAdapter;
import fyi.jackson.drew.popularmovies.ui.MovieDataHandler;
import fyi.jackson.drew.popularmovies.ui.MovieItemClickListener;

public class MovieListFragment extends Fragment implements
        MovieItemClickListener, MovieDataCallback {

    public static final String TAG = MovieListFragment.class.getSimpleName();


    RecyclerView recyclerView;
    MovieListAdapter adapter;

    MovieDataHandler dataHandler;
    List<Movie> movieList;

    public MovieListFragment() {}

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new MovieListAdapter(null, this);

        dataHandler = new MovieDataHandler(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        adapter.setMovieList(movieList);
        recyclerView = view.findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 6);
        gridLayoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (movieList == null) dataHandler.requestActiveData();

        MainActivity fragmentActivity = (MainActivity) getActivity();
        fragmentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fragmentActivity.toolbarLayout.setTitle(getString(R.string.title_popular_movies));
        fragmentActivity.appBarLayout.setExpanded(false);
        fragmentActivity.disableAppBar();
        fragmentActivity.checkNetworkConnection();
    }

    @Override
    public void onMovieClicked(int pos, Movie movie, ImageView sharedImageView) {
        String transitionName = ViewCompat.getTransitionName(sharedImageView);

        Fragment movieDetailFragment =
                MovieDetailFragment.newInstance(movie, transitionName);

        getFragmentManager()
                .beginTransaction()
                .addSharedElement(sharedImageView, transitionName)
                .addToBackStack(TAG)
                .replace(R.id.content, movieDetailFragment)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        boolean isMethodPopular =
                (dataHandler.activeDataMethod == MovieDataHandler.METHOD_API_POPULAR) ||
                (dataHandler.activeDataMethod == MovieDataHandler.METHOD_DB_POPULAR);
        int activeMenuItemIndex = isMethodPopular ? 0 : 1;
        menu.getItem(activeMenuItemIndex).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort_by_popularity:
                setTitle(R.string.title_popular_movies);
                dataHandler.setActiveDataMethod(MovieDataHandler.METHOD_API_POPULAR);
                break;
            case R.id.menu_sort_by_rating:
                setTitle(R.string.title_rated_movies);
                dataHandler.setActiveDataMethod(MovieDataHandler.METHOD_API_TOP);
                break;
        }
        recyclerView.smoothScrollToPosition(0);
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(CharSequence title) {
        ((MainActivity) getActivity()).toolbarLayout.setTitle(title);
    }

    public void setTitle(int resId) {
        CharSequence title = getString(resId);
        setTitle(title);
    }

    @Override
    public void onUpdate(List<Movie> movieList) {
        Log.d(TAG, "onUpdate: Updating RecyclerView");
        this.movieList = movieList;
        adapter.setMovieList(movieList);
        adapter.notifyDataSetChanged();
    }
}
